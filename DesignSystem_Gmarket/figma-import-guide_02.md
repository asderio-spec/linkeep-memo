# 피그마 임포트 가이드 - G마켓 디자인 시스템

## 📌 중요 사항
**React 코드를 Figma로 직접 임포트하는 것은 불가능합니다.**
- Figma는 디자인 툴, React는 코드
- 방향: Figma → Code ✅ / Code → Figma ❌

하지만 여러 대체 방법들이 있습니다!

---

## 🎨 방법 1: Figma에서 디자인 시스템 재구축 (권장)

### 1단계: 컬러 스타일 생성
```
Primary Colors:
- Primary: #00C73C
- Primary Light: #4FD66E
- Primary Lighter: #B7E5C4
- Primary Dark: #009E2E
- Primary Darker: #007723

Secondary Colors:
- Secondary: #FF3D32
- Secondary Light: #FF6B63
- Secondary Dark: #CC312A

Gray Scale:
- Gray 50: #F8F9FA
- Gray 100: #F1F3F5
- Gray 200: #E9ECEF
- Gray 300: #DEE2E6
- Gray 400: #CED4DA
- Gray 500: #ADB5BD
- Gray 600: #868E96
- Gray 700: #495057
- Gray 800: #343A40
- Gray 900: #212529

Semantic Colors:
- Info: #3182F6
- Success: #00C73C
- Warning: #FF9500
- Error: #FF3D32
```

### 2단계: 텍스트 스타일 생성
```
Display:
- Display 1: 60px, Bold
- Display 2: 48px, Bold

Heading:
- H1: 36px, Bold
- H2: 30px, Bold
- H3: 24px, Bold
- H4: 20px, Bold
- H5: 18px, Bold
- H6: 16px, Bold

Body:
- Body 1: 16px, Regular
- Body 2: 14px, Regular
- Body 3: 12px, Regular

Caption: 12px, Regular, Gray 600
```

### 3단계: 컴포넌트 생성
Figma에서 각 컴포넌트를 수동으로 생성:
- Buttons (Primary, Secondary, Danger, Ghost)
- Cards
- Input Fields
- Checkboxes
- Radio Buttons
- Badges
- Chips

---

## 🚀 방법 2: Figma Plugin 활용

### Builder.io - Figma to Code Plugin
1. Figma Plugin 검색창에서 "Builder.io" 설치
2. 디자인 생성 후 플러그인 실행
3. React 코드 생성 (역방향)

### Figma Tokens Plugin
1. "Figma Tokens" 플러그인 설치
2. 디자인 토큰 JSON 생성:

```json
{
  "global": {
    "colors": {
      "primary": {
        "main": { "value": "#00C73C" },
        "light": { "value": "#4FD66E" },
        "dark": { "value": "#009E2E" }
      },
      "gray": {
        "50": { "value": "#F8F9FA" },
        "100": { "value": "#F1F3F5" },
        "200": { "value": "#E9ECEF" }
      }
    },
    "spacing": {
      "xs": { "value": "4px" },
      "sm": { "value": "8px" },
      "md": { "value": "16px" },
      "lg": { "value": "24px" },
      "xl": { "value": "32px" },
      "xxl": { "value": "48px" }
    }
  }
}
```

---

## 🔄 방법 3: Storybook + Figma 연동

### 1단계: Storybook 설치
```bash
npx storybook@latest init
npm run storybook
```

### 2단계: 컴포넌트 스토리 생성
```javascript
// Button.stories.js
export default {
  title: 'Gmarket/Button',
  component: Button,
};

export const Primary = {
  args: {
    variant: 'primary',
    children: 'Primary Button',
  },
};

export const Secondary = {
  args: {
    variant: 'secondary',
    children: 'Secondary Button',
  },
};
```

### 3단계: Figma에 Storybook 임베드
1. Figma에서 "Storybook Connect" 플러그인 설치
2. Storybook URL 연결
3. 컴포넌트 문서화

---

## 🎯 방법 4: 디자인 토큰 JSON 생성

### React 코드에서 디자인 토큰 추출
아래 코드를 실행하여 디자인 토큰 JSON 파일 생성:

```javascript
// design-tokens-generator.js
const designTokens = {
  "Gmarket Design System": {
    "Colors": {
      "Primary": {
        "main": { "value": "#00C73C", "type": "color" },
        "light": { "value": "#4FD66E", "type": "color" },
        "lighter": { "value": "#B7E5C4", "type": "color" },
        "dark": { "value": "#009E2E", "type": "color" },
        "darker": { "value": "#007723", "type": "color" }
      },
      "Secondary": {
        "main": { "value": "#FF3D32", "type": "color" },
        "light": { "value": "#FF6B63", "type": "color" },
        "dark": { "value": "#CC312A", "type": "color" }
      },
      "Gray": {
        "50": { "value": "#F8F9FA", "type": "color" },
        "100": { "value": "#F1F3F5", "type": "color" },
        "200": { "value": "#E9ECEF", "type": "color" },
        "300": { "value": "#DEE2E6", "type": "color" },
        "400": { "value": "#CED4DA", "type": "color" },
        "500": { "value": "#ADB5BD", "type": "color" },
        "600": { "value": "#868E96", "type": "color" },
        "700": { "value": "#495057", "type": "color" },
        "800": { "value": "#343A40", "type": "color" },
        "900": { "value": "#212529", "type": "color" }
      }
    },
    "Typography": {
      "fontSize": {
        "xs": { "value": "12", "type": "fontSizes" },
        "sm": { "value": "14", "type": "fontSizes" },
        "base": { "value": "16", "type": "fontSizes" },
        "lg": { "value": "18", "type": "fontSizes" },
        "xl": { "value": "20", "type": "fontSizes" },
        "2xl": { "value": "24", "type": "fontSizes" },
        "3xl": { "value": "30", "type": "fontSizes" },
        "4xl": { "value": "36", "type": "fontSizes" },
        "5xl": { "value": "48", "type": "fontSizes" },
        "6xl": { "value": "60", "type": "fontSizes" }
      },
      "fontWeight": {
        "normal": { "value": "400", "type": "fontWeights" },
        "medium": { "value": "500", "type": "fontWeights" },
        "bold": { "value": "700", "type": "fontWeights" }
      }
    },
    "Spacing": {
      "xs": { "value": "4", "type": "spacing" },
      "sm": { "value": "8", "type": "spacing" },
      "md": { "value": "16", "type": "spacing" },
      "lg": { "value": "24", "type": "spacing" },
      "xl": { "value": "32", "type": "spacing" },
      "xxl": { "value": "48", "type": "spacing" }
    },
    "BorderRadius": {
      "sm": { "value": "4", "type": "borderRadius" },
      "md": { "value": "6", "type": "borderRadius" },
      "lg": { "value": "8", "type": "borderRadius" },
      "full": { "value": "9999", "type": "borderRadius" }
    }
  }
};

// JSON 파일로 저장
console.log(JSON.stringify(designTokens, null, 2));
```

이 JSON을 Figma Tokens 플러그인에서 임포트하면 자동으로 스타일 생성됩니다.

---

## 🛠 방법 5: HTML to Figma Plugin 활용

### 1단계: HTML to Figma 플러그인 설치
1. Figma에서 "HTML to Figma" 플러그인 검색 및 설치
2. Chrome Extension "HTML to Figma" 설치

### 2단계: React 앱 실행 및 캡처
1. React 앱을 로컬에서 실행
2. Chrome Extension 클릭
3. 컴포넌트 선택 및 Figma로 전송
4. Figma에서 자동으로 레이어 생성

**장점**: 실제 렌더링된 UI를 Figma로 가져옴
**단점**: 코드가 아닌 시각적 요소만 임포트

---

## 📐 방법 6: 수동 컴포넌트 라이브러리 구축

### Figma 컴포넌트 구조
```
📁 Gmarket Design System
  📁 Colors
    ├── Primary
    ├── Secondary
    └── Gray Scale
  📁 Components
    📁 Buttons
      ├── Primary / Default
      ├── Primary / Hover
      ├── Primary / Disabled
      ├── Secondary / Default
      └── ...
    📁 Cards
      ├── Product Card
      ├── Basic Card
      └── ...
    📁 Form Elements
      ├── Text Field
      ├── Checkbox
      ├── Radio
      └── Select
    📁 Navigation
      ├── Header
      ├── Tabs
      └── Pagination
```

### Auto Layout 설정
```
Button Component:
- Auto Layout: Horizontal
- Padding: 16px 24px (lg), 8px 16px (md), 6px 12px (sm)
- Gap: 8px
- Border Radius: 6px
- Fill: #00C73C (Primary)
```

---

## 🎭 방법 7: Figma API를 통한 자동화

### Figma API로 컴포넌트 생성 스크립트
```javascript
// figma-api-creator.js
const axios = require('axios');

const FIGMA_TOKEN = 'YOUR_FIGMA_TOKEN';
const FILE_KEY = 'YOUR_FILE_KEY';

async function createGmarketComponents() {
  const components = {
    "Button": {
      type: "RECTANGLE",
      fills: [{ type: "SOLID", color: { r: 0, g: 0.78, b: 0.235 } }],
      cornerRadius: 6,
      paddingLeft: 24,
      paddingRight: 24,
      paddingTop: 8,
      paddingBottom: 8
    },
    "Card": {
      type: "FRAME",
      fills: [{ type: "SOLID", color: { r: 1, g: 1, b: 1 } }],
      strokes: [{ type: "SOLID", color: { r: 0.87, g: 0.87, b: 0.87 } }],
      strokeWeight: 1,
      cornerRadius: 8
    }
  };

  // Figma API 호출
  const response = await axios.post(
    `https://api.figma.com/v1/files/${FILE_KEY}/nodes`,
    components,
    {
      headers: {
        'X-Figma-Token': FIGMA_TOKEN,
        'Content-Type': 'application/json'
      }
    }
  );
  
  return response.data;
}
```

---

## ✨ 권장 워크플로우

### 최적의 디자인 시스템 구축 프로세스

1. **디자인 토큰 정의** (JSON)
   - 색상, 타이포그래피, 스페이싱 정의
   - 위의 design-tokens.json 사용

2. **Figma에서 기초 스타일 생성**
   - Figma Tokens 플러그인으로 JSON 임포트
   - Color Styles, Text Styles 자동 생성

3. **컴포넌트 라이브러리 구축**
   - 수동으로 주요 컴포넌트 생성
   - Auto Layout 활용
   - Variants 설정

4. **Storybook 연동**
   - React 컴포넌트와 Figma 디자인 동기화
   - 문서화 및 테스트

---

## 🚀 빠른 시작을 위한 Figma 템플릿 구조

### Figma 파일 생성 스크립트
아래 내용을 복사하여 Figma에서 수동으로 생성하거나, Figma Plugin API로 자동화:

```javascript
// gmarket-figma-template.js
const figmaTemplate = {
  name: "Gmarket Design System",
  pages: [
    {
      name: "🎨 Foundation",
      sections: [
        "Colors",
        "Typography", 
        "Spacing",
        "Shadows"
      ]
    },
    {
      name: "📦 Components",
      sections: [
        "Buttons",
        "Cards",
        "Forms",
        "Navigation",
        "Feedback"
      ]
    },
    {
      name: "📱 Templates",
      sections: [
        "Product List",
        "Product Detail",
        "Shopping Cart",
        "Checkout"
      ]
    }
  ],
  styles: {
    colors: [
      { name: "Primary/Main", value: "#00C73C" },
      { name: "Primary/Light", value: "#4FD66E" },
      { name: "Primary/Dark", value: "#009E2E" },
      { name: "Secondary/Main", value: "#FF3D32" },
      { name: "Gray/50", value: "#F8F9FA" },
      { name: "Gray/100", value: "#F1F3F5" },
      { name: "Gray/900", value: "#212529" }
    ],
    typography: [
      { name: "Display/1", size: 60, weight: 700 },
      { name: "Heading/1", size: 36, weight: 700 },
      { name: "Body/1", size: 16, weight: 400 },
      { name: "Caption", size: 12, weight: 400 }
    ]
  }
};
```

---

## 📝 체크리스트

### Figma 디자인 시스템 구축 체크리스트
- [ ] **Foundation 설정**
  - [ ] Color Styles 생성
  - [ ] Text Styles 생성
  - [ ] Effect Styles (그림자) 생성
  - [ ] Grid & Layout 설정

- [ ] **컴포넌트 생성**
  - [ ] Button (4 variants × 3 sizes)
  - [ ] Input Field
  - [ ] Select Dropdown
  - [ ] Checkbox & Radio
  - [ ] Card Component
  - [ ] Badge & Chip
  - [ ] Navigation Bar
  - [ ] Product Card

- [ ] **템플릿 페이지**
  - [ ] 홈페이지
  - [ ] 상품 목록
  - [ ] 상품 상세
  - [ ] 장바구니

---

## 💡 Pro Tips

### 1. Figma Variables 활용 (2024)
```
Color Variables:
├── Primitive
│   ├── green-500: #00C73C
│   └── gray-100: #F1F3F5
└── Semantic
    ├── button-primary: {green-500}
    └── border-default: {gray-100}
```

### 2. Component Properties 설정
```
Button Component Properties:
- Variant: Primary | Secondary | Danger | Ghost
- Size: Small | Medium | Large
- State: Default | Hover | Pressed | Disabled
- Icon: Boolean
- Full Width: Boolean
```

### 3. Design Token 네이밍 컨벤션
```
{category}-{property}-{variant}-{state}

예시:
- color-background-primary-default
- spacing-padding-medium
- typography-heading-large
```

---

## 🔗 유용한 리소스

### Figma Plugins
1. **Figma Tokens** - 디자인 토큰 관리
2. **Design System Organizer** - 컴포넌트 정리
3. **Figma to Code** - 코드 생성
4. **Stark** - 접근성 체크

### 온라인 도구
1. **Style Dictionary** - 디자인 토큰 변환
2. **Supernova** - 디자인 시스템 문서화
3. **Zeroheight** - 디자인 시스템 게시

### 참고 자료
- [Figma Best Practices](https://www.figma.com/best-practices/)
- [Design Tokens W3C](https://www.w3.org/community/design-tokens/)
- [Atomic Design Methodology](https://atomicdesign.bradfrost.com/)

---

## 🎯 결론

**가장 실용적인 방법:**
1. **디자인 토큰 JSON** 생성 (위 코드 사용)
2. **Figma Tokens 플러그인**으로 임포트
3. **수동으로 핵심 컴포넌트** 생성
4. **Storybook**으로 코드와 디자인 연동

이 방법으로 G마켓 디자인 시스템을 Figma에 효율적으로 구축할 수 있습니다!

---

## 📧 추가 지원

더 자세한 구현이 필요하시면:
- Figma 플러그인 개발
- 자동화 스크립트 작성
- 디자인 토큰 변환 도구

위 내용에 대해 추가 코드나 가이드가 필요하시면 말씀해 주세요!