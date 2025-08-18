# G마켓 디자인 시스템 코드 확인 가이드

## 📋 코드 검토 결과

### ✅ 검토 완료 항목
- **React 구조**: 정상적인 React 함수형 컴포넌트 구조
- **Import 문**: Lucide React 아이콘 라이브러리 정상 import
- **Hook 사용**: useState, useEffect, useRef 정상 사용
- **Tailwind CSS**: 모든 클래스명 유효
- **컴포넌트 구조**: 30개 이상의 컴포넌트 정상 구현
- **문법 오류**: 없음 (JSX 문법 정상)

### ⚠️ 주의 사항
- 현재 코드는 **Claude.ai Artifact 환경**에 최적화되어 있음
- 외부 환경에서 실행 시 추가 설정 필요

---

## 🚀 실행 방법

### 방법 1: Claude.ai에서 직접 확인 (가장 간단) ✨
1. 위의 G마켓 디자인 시스템 Artifact가 이미 렌더링되어 있습니다
2. 상단의 **Overview**, **Components**, **Examples** 버튼을 클릭하여 탐색
3. 모든 컴포넌트가 실시간으로 작동합니다

### 방법 2: CodeSandbox에서 실행
1. [CodeSandbox](https://codesandbox.io) 접속
2. "Create Sandbox" → "React" 템플릿 선택
3. `App.js` 파일에 위 코드 전체 복사/붙여넣기
4. Dependencies 추가:
   ```json
   "dependencies": {
     "react": "^18.2.0",
     "react-dom": "^18.2.0",
     "lucide-react": "^0.263.1"
   }
   ```
5. Tailwind CSS CDN을 `public/index.html`에 추가:
   ```html
   <script src="https://cdn.tailwindcss.com"></script>
   ```

### 방법 3: 로컬 환경에서 실행

#### 1단계: 프로젝트 생성
```bash
# React 프로젝트 생성
npx create-react-app gmarket-design-system
cd gmarket-design-system

# 필요한 패키지 설치
npm install lucide-react
npm install -D tailwindcss postcss autoprefixer
npx tailwindcss init -p
```

#### 2단계: Tailwind CSS 설정
`tailwind.config.js` 파일 수정:
```javascript
module.exports = {
  content: [
    "./src/**/*.{js,jsx,ts,tsx}",
  ],
  theme: {
    extend: {},
  },
  plugins: [],
}
```

`src/index.css` 파일에 추가:
```css
@tailwind base;
@tailwind components;
@tailwind utilities;
```

#### 3단계: 코드 적용
1. `src/App.js` 파일을 열고 기존 내용 삭제
2. 위의 G마켓 디자인 시스템 코드 전체 복사/붙여넣기
3. 파일 저장

#### 4단계: 실행
```bash
npm start
```
브라우저에서 `http://localhost:3000` 자동 열림

### 방법 4: StackBlitz에서 실행
1. [StackBlitz React](https://stackblitz.com/fork/react) 접속
2. `App.jsx` 파일에 코드 붙여넣기
3. Terminal에서 `npm install lucide-react` 실행
4. `index.html`에 Tailwind CDN 추가:
   ```html
   <script src="https://cdn.tailwindcss.com"></script>
   ```

### 방법 5: Vite로 빠르게 실행
```bash
# Vite React 프로젝트 생성
npm create vite@latest gmarket-design -- --template react
cd gmarket-design

# 패키지 설치
npm install
npm install lucide-react
npm install -D tailwindcss postcss autoprefixer
npx tailwindcss init -p

# Tailwind 설정 (위와 동일)
# src/App.jsx에 코드 붙여넣기

npm run dev
```

---

## 🎨 주요 기능 테스트 체크리스트

### Overview 섹션
- [ ] 메인 네비게이션 바 표시 확인
- [ ] G마켓 로고 및 검색창 작동
- [ ] 컬러 팔레트 표시

### Components 섹션
- [ ] **Buttons**: Primary, Secondary, Danger, Ghost 버튼 클릭
- [ ] **Form Controls**: 
  - [ ] TextField 입력
  - [ ] Select 드롭다운 열기/선택
  - [ ] Checkbox 체크/해제
  - [ ] Radio 버튼 선택
  - [ ] Switch 토글
- [ ] **Dialog**: "Open Dialog" 버튼 클릭 → 모달 열기/닫기
- [ ] **Toast**: "Show Toast" 버튼 클릭 → 알림 표시
- [ ] **Accordion**: 각 항목 클릭하여 펼치기/접기
- [ ] **Tabs**: 탭 전환

### Examples 섹션
- [ ] 상품 카드 그리드 표시
- [ ] 장바구니 UI 확인
- [ ] 수량 조절 버튼 (+/-) 클릭
- [ ] 필터 칩 선택/해제

---

## 🔧 문제 해결

### 아이콘이 표시되지 않는 경우
```bash
npm install lucide-react@0.263.1
```

### Tailwind 스타일이 적용되지 않는 경우
1. Tailwind CDN 방식 사용:
   ```html
   <!-- index.html의 <head>에 추가 -->
   <script src="https://cdn.tailwindcss.com"></script>
   ```

2. 또는 Tailwind CLI 설정 확인:
   - `tailwind.config.js`의 content 경로 확인
   - `index.css`에 @tailwind 지시문 확인

### React 버전 충돌 시
```bash
npm install react@18 react-dom@18
```

---

## 📱 반응형 테스트

### 데스크톱 (1920px)
- 4열 상품 그리드
- 전체 네비게이션 메뉴 표시

### 태블릿 (768px)
- 2열 상품 그리드
- 검색창 표시

### 모바일 (375px)
- 1열 상품 그리드
- 햄버거 메뉴
- 모바일 검색창

---

## ✨ 추가 커스터마이징

### 색상 변경
```javascript
// colors 객체에서 primary.main 값 수정
const colors = {
  primary: {
    main: '#00C73C', // G마켓 그린
    // 원하는 색상으로 변경 가능
  }
}
```

### 새 컴포넌트 추가
```javascript
// 새로운 컴포넌트를 추가하려면
const MyNewComponent = () => {
  return (
    <Card className="p-4">
      <h3>새로운 컴포넌트</h3>
    </Card>
  );
};
```

---

## 📞 지원

문제가 발생하거나 추가 도움이 필요하신 경우, 다음 내용과 함께 문의해주세요:
- 사용 중인 실행 방법 (방법 1~5 중 선택)
- 브라우저 콘솔 에러 메시지
- Node.js 및 npm 버전 (`node -v`, `npm -v`)

---

## 🎉 성공적인 실행 화면

정상적으로 실행되면 다음과 같은 화면이 표시됩니다:
1. 상단에 초록색 G마켓 네비게이션 바
2. Overview, Components, Examples 3개의 섹션 버튼
3. 각 섹션별 인터랙티브한 UI 컴포넌트들
4. 하단 푸터

모든 컴포넌트는 클릭, 입력, 선택 등의 상호작용이 가능합니다!