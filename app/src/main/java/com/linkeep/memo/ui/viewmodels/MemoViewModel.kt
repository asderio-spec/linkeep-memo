package com.linkeep.memo.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.linkeep.memo.data.model.Memo
import com.linkeep.memo.data.repository.MemoRepository
import com.linkeep.memo.data.service.LLMService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MemoViewModel @Inject constructor(
    private val repository: MemoRepository,
    private val llmService: LLMService
) : ViewModel() {

    private val _memos = MutableStateFlow<List<Memo>>(emptyList())
    val memos: StateFlow<List<Memo>> = _memos.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    // 카테고리 상태 (간단히 메모에서 수집)
    private val _categories = MutableStateFlow<List<String>>(emptyList())
    val categories: StateFlow<List<String>> = _categories.asStateFlow()

    private val _selectedCategory = MutableStateFlow<String?>(null)
    val selectedCategory: StateFlow<String?> = _selectedCategory.asStateFlow()

    val filteredMemos: StateFlow<List<Memo>> = combine(
        _memos,
        _searchQuery,
        _selectedCategory
    ) { memos, query, selectedCat ->
        val byQuery = if (query.isBlank()) memos else memos.filter { memo ->
            memo.title.contains(query, ignoreCase = true) ||
            memo.content.contains(query, ignoreCase = true) ||
            memo.category.contains(query, ignoreCase = true)
        }
        val byCategory = selectedCat?.let { cat -> byQuery.filter { it.category == cat } } ?: byQuery
        byCategory
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    init {
        var seeded = false
        viewModelScope.launch {
            repository.getAllMemos().collect { memoList ->
                _memos.value = memoList
                // 카테고리 목록 업데이트 (정렬 및 중복 제거)
                _categories.value = memoList.map { it.category }
                    .distinct()
                    .sorted()

                if (memoList.isEmpty() && !seeded) {
                    seeded = true
                    // 데모용 시드 데이터 삽입
                    repository.insertMemo(
                        Memo(
                            title = "원클릭 저장 안내",
                            content = "공유 메뉴에서 Linkeep을 선택하면 자동 요약과 카테고리가 생성됩니다.",
                            link = "https://example.com/intro",
                            category = "가이드"
                        )
                    )
                    repository.insertMemo(
                        Memo(
                            title = "디자인 시스템 적용",
                            content = "G마켓 그린(#00C73C)을 테마로 적용했습니다.",
                            category = "디자인"
                        )
                    )
                    repository.insertMemo(
                        Memo(
                            title = "검색과 필터",
                            content = "상단 검색과 카테고리 칩으로 빠르게 찾아보세요.",
                            category = "기능"
                        )
                    )
                }
            }
        }
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun setSelectedCategory(category: String?) {
        _selectedCategory.value = category
    }

    fun addMemo(title: String, content: String, link: String?, category: String) {
        viewModelScope.launch {
            val memo = Memo(
                title = title,
                content = content,
                link = link,
                category = category
            )
            repository.insertMemo(memo)
        }
    }

    fun generateMemoContent(
        title: String? = null,
        content: String? = null,
        link: String? = null,
        category: String? = null
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val (generatedTitle, generatedContent, generatedCategory) = 
                    llmService.generateMemoContent(title, content, link, category)
                
                addMemo(
                    title = generatedTitle,
                    content = generatedContent,
                    link = link,
                    category = generatedCategory
                )
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun deleteMemo(memo: Memo) {
        viewModelScope.launch {
            repository.deleteMemo(memo)
        }
    }

    fun updateMemo(memo: Memo) {
        viewModelScope.launch {
            repository.updateMemo(memo)
        }
    }
} 