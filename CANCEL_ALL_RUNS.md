# Actions 실행 일괄 취소 방법 (gh CLI)

```bash
# 사전 준비
gh auth login

REPO=asderio-spec/linkeep-memo

# 진행중/대기중 실행 모두 취소
for STATUS in in_progress queued; do
  gh run list --repo "$REPO" --status $STATUS --limit 50 --json databaseId -q '.[].databaseId' \
    | xargs -I{} gh run cancel {} --repo "$REPO"
done

# 단일 수동 실행(워크플로우 디스패치)
gh workflow run build-android-apk.yml --repo "$REPO" --ref main
```
