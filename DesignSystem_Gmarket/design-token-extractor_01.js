#!/usr/bin/env node

/**
 * G마켓 디자인 시스템 - 디자인 토큰 자동 추출 스크립트
 * 
 * 사용법:
 * 1. 이 파일을 프로젝트 루트에 저장
 * 2. 실행: node design-token-extractor.js
 * 3. 생성된 파일들:
 *    - gmarket-tokens.json (Figma Tokens 플러그인용)
 *    - gmarket-tokens-style-dictionary.json (Style Dictionary용)
 *    - gmarket-components.json (컴포넌트 구조)
 */

const fs = require('fs');
const path = require('path');

// G마켓 디자인 시스템 토큰 정의
const gmarketDesignSystem = {
  // 1. 색상 토큰
  colors: {
    primary: {
      main: '#00C73C',
      light: '#4FD66E',
      lighter: '#B7E5C4',
      dark: '#009E2E',
      darker: '#007723'
    },
    secondary: {
      main: '#FF3D32',
      light: '#FF6B63',
      dark: '#CC312A'
    },
    gray: {
      50: '#F8F9FA',
      100: '#F1F3F5',
      200: '#E9ECEF',
      300: '#DEE2E6',
      400: '#CED4DA',
      500: '#ADB5BD',
      600: '#868E96',
      700: '#495057',
      800: '#343A40',
      900: '#212529'
    },
    semantic: {
      info: '#3182F6',
      success: '#00C73C',
      warning: '#FF9500',
      error: '#FF3D32'
    },
    background: {
      white: '#FFFFFF',
      gray: '#F8F9FA'
    }
  },
  
  // 2. 타이포그래피 토큰
  typography: {
    fontFamily: {
      primary: 'Gmarket Sans, -apple-system, BlinkMacSystemFont, Segoe UI, Roboto, sans-serif',
      mono: 'SF Mono, Monaco, Consolas, monospace'
    },
    fontSize: {
      xs: '12px',
      sm: '14px',
      base: '16px',
      lg: '18px',
      xl: '20px',
      '2xl': '24px',
      '3xl': '30px',
      '4xl': '36px',
      '5xl': '48px',
      '6xl': '60px'
    },
    fontWeight: {
      normal: 400,
      medium: 500,
      bold: 700
    },
    lineHeight: {
      tight: 1.25,
      normal: 1.5,
      relaxed: 1.75
    },
    letterSpacing: {
      tight: '-0.025em',
      normal: '0',
      wide: '0.025em'
    }
  },
  
  // 3. 스페이싱 토큰
  spacing: {
    xs: '4px',
    sm: '8px',
    md: '16px',
    lg: '24px',
    xl: '32px',
    xxl: '48px',
    '3xl': '64px',
    '4xl': '96px'
  },
  
  // 4. 레이아웃 토큰
  layout: {
    container: {
      sm: '640px',
      md: '768px',
      lg: '1024px',
      xl: '1280px',
      '2xl': '1536px'
    },
    grid: {
      columns: 12,
      gap: '16px'
    }
  },
  
  // 5. Border 토큰
  border: {
    width: {
      thin: '1px',
      medium: '2px',
      thick: '4px'
    },
    radius: {
      none: '0',
      sm: '4px',
      md: '6px',
      lg: '8px',
      xl: '12px',
      full: '9999px'
    }
  },
  
  // 6. 그림자 토큰
  shadows: {
    sm: '0 1px 2px 0 rgba(0, 0, 0, 0.05)',
    md: '0 4px 6px -1px rgba(0, 0, 0, 0.1)',
    lg: '0 10px 15px -3px rgba(0, 0, 0, 0.1)',
    xl: '0 20px 25px -5px rgba(0, 0, 0, 0.1)',
    '2xl': '0 25px 50px -12px rgba(0, 0, 0, 0.25)',
    inner: 'inset 0 2px 4px 0 rgba(0, 0, 0, 0.06)'
  },
  
  // 7. 애니메이션 토큰
  animation: {
    duration: {
      fast: '150ms',
      normal: '300ms',
      slow: '500ms'
    },
    easing: {
      linear: 'linear',
      ease: 'ease',
      easeIn: 'ease-in',
      easeOut: 'ease-out',
      easeInOut: 'ease-in-out'
    }
  },
  
  // 8. Z-Index 토큰
  zIndex: {
    base: 0,
    dropdown: 1000,
    sticky: 1020,
    fixed: 1030,
    backdrop: 1040,
    modal: 1050,
    popover: 1060,
    tooltip: 1070
  }
};

// 컴포넌트 구조 정의
const componentStructure = {
  Button: {
    variants: ['primary', 'secondary', 'danger', 'ghost'],
    sizes: ['small', 'medium', 'large'],
    states: ['default', 'hover', 'pressed', 'disabled'],
    properties: {
      padding: {
        small: '6px 12px',
        medium: '8px 16px',
        large: '12px 24px'
      },
      fontSize: {
        small: '12px',
        medium: '14px',
        large: '16px'
      },
      borderRadius: '6px'
    }
  },
  Card: {
    variants: ['default', 'hoverable'],
    properties: {
      padding: '16px',
      borderRadius: '8px',
      borderWidth: '1px',
      borderColor: '#E9ECEF',
      backgroundColor: '#FFFFFF',
      boxShadow: {
        default: 'none',
        hover: '0 10px 15px -3px rgba(0, 0, 0, 0.1)'
      }
    }
  },
  TextField: {
    states: ['default', 'focus', 'error', 'disabled'],
    properties: {
      padding: '8px 12px',
      borderRadius: '6px',
      borderWidth: '1px',
      borderColor: {
        default: '#DEE2E6',
        focus: '#00C73C',
        error: '#FF3D32'
      },
      fontSize: '14px',
      lineHeight: '1.5'
    }
  },
  Badge: {
    variants: ['default', 'primary', 'success', 'warning', 'error', 'info'],
    sizes: ['small', 'medium', 'large'],
    properties: {
      padding: {
        small: '2px 8px',
        medium: '4px 10px',
        large: '6px 12px'
      },
      fontSize: {
        small: '11px',
        medium: '12px',
        large: '14px'
      },
      borderRadius: '9999px',
      fontWeight: '500'
    }
  },
  Chip: {
    states: ['default', 'selected'],
    properties: {
      padding: '6px 12px',
      borderRadius: '9999px',
      fontSize: '14px',
      backgroundColor: {
        default: '#F1F3F5',
        selected: '#00C73C'
      },
      color: {
        default: '#495057',
        selected: '#FFFFFF'
      }
    }
  }
};

// Figma Tokens 플러그인 형식으로 변환
function convertToFigmaTokens() {
  const figmaTokens = {
    global: {
      // 색상 변환
      color: {},
      // 타이포그래피 변환
      typography: {},
      // 스페이싱 변환
      spacing: {},
      // Border Radius 변환
      borderRadius: {},
      // 그림자 변환
      boxShadow: {}
    }
  };
  
  // 색상 토큰 변환
  Object.entries(gmarketDesignSystem.colors).forEach(([category, values]) => {
    figmaTokens.global.color[category] = {};
    Object.entries(values).forEach(([key, value]) => {
      figmaTokens.global.color[category][key] = {
        value: value,
        type: 'color'
      };
    });
  });
  
  // 타이포그래피 토큰 변환
  figmaTokens.global.typography = {
    fontSize: {},
    fontWeight: {},
    lineHeight: {},
    fontFamily: {}
  };
  
  Object.entries(gmarketDesignSystem.typography.fontSize).forEach(([key, value]) => {
    figmaTokens.global.typography.fontSize[key] = {
      value: value,
      type: 'fontSizes'
    };
  });
  
  Object.entries(gmarketDesignSystem.typography.fontWeight).forEach(([key, value]) => {
    figmaTokens.global.typography.fontWeight[key] = {
      value: value.toString(),
      type: 'fontWeights'
    };
  });
  
  Object.entries(gmarketDesignSystem.typography.lineHeight).forEach(([key, value]) => {
    figmaTokens.global.typography.lineHeight[key] = {
      value: value.toString(),
      type: 'lineHeights'
    };
  });
  
  // 스페이싱 토큰 변환
  Object.entries(gmarketDesignSystem.spacing).forEach(([key, value]) => {
    figmaTokens.global.spacing[key] = {
      value: value,
      type: 'spacing'
    };
  });
  
  // Border Radius 토큰 변환
  Object.entries(gmarketDesignSystem.border.radius).forEach(([key, value]) => {
    figmaTokens.global.borderRadius[key] = {
      value: value,
      type: 'borderRadius'
    };
  });
  
  // 그림자 토큰 변환
  Object.entries(gmarketDesignSystem.shadows).forEach(([key, value]) => {
    figmaTokens.global.boxShadow[key] = {
      value: value,
      type: 'boxShadow'
    };
  });
  
  return figmaTokens;
}

// Style Dictionary 형식으로 변환
function convertToStyleDictionary() {
  return {
    color: gmarketDesignSystem.colors,
    size: {
      font: gmarketDesignSystem.typography.fontSize,
      spacing: gmarketDesignSystem.spacing,
      borderRadius: gmarketDesignSystem.border.radius,
      borderWidth: gmarketDesignSystem.border.width
    },
    typography: {
      fontFamily: gmarketDesignSystem.typography.fontFamily,
      fontWeight: gmarketDesignSystem.typography.fontWeight,
      lineHeight: gmarketDesignSystem.typography.lineHeight,
      letterSpacing: gmarketDesignSystem.typography.letterSpacing
    },
    shadow: gmarketDesignSystem.shadows,
    animation: gmarketDesignSystem.animation,
    zIndex: gmarketDesignSystem.zIndex
  };
}

// CSS 변수 생성
function generateCSSVariables() {
  let css = ':root {\n';
  
  // 색상 변수
  Object.entries(gmarketDesignSystem.colors).forEach(([category, values]) => {
    Object.entries(values).forEach(([key, value]) => {
      css += `  --color-${category}-${key}: ${value};\n`;
    });
  });
  
  // 스페이싱 변수
  Object.entries(gmarketDesignSystem.spacing).forEach(([key, value]) => {
    css += `  --spacing-${key}: ${value};\n`;
  });
  
  // 폰트 크기 변수
  Object.entries(gmarketDesignSystem.typography.fontSize).forEach(([key, value]) => {
    css += `  --font-size-${key}: ${value};\n`;
  });
  
  // Border Radius 변수
  Object.entries(gmarketDesignSystem.border.radius).forEach(([key, value]) => {
    css += `  --border-radius-${key}: ${value};\n`;
  });
  
  // 그림자 변수
  Object.entries(gmarketDesignSystem.shadows).forEach(([key, value]) => {
    css += `  --shadow-${key}: ${value};\n`;
  });
  
  css += '}\n';
  
  return css;
}

// Tailwind Config 생성
function generateTailwindConfig() {
  return {
    theme: {
      extend: {
        colors: {
          primary: gmarketDesignSystem.colors.primary,
          secondary: gmarketDesignSystem.colors.secondary,
          gray: gmarketDesignSystem.colors.gray,
          semantic: gmarketDesignSystem.colors.semantic
        },
        spacing: gmarketDesignSystem.spacing,
        fontSize: gmarketDesignSystem.typography.fontSize,
        fontWeight: gmarketDesignSystem.typography.fontWeight,
        borderRadius: gmarketDesignSystem.border.radius,
        boxShadow: gmarketDesignSystem.shadows,
        zIndex: gmarketDesignSystem.zIndex
      }
    }
  };
}

// SCSS 변수 생성
function generateSCSSVariables() {
  let scss = '// G마켓 디자인 시스템 SCSS 변수\n\n';
  
  // 색상 변수
  scss += '// Colors\n';
  Object.entries(gmarketDesignSystem.colors).forEach(([category, values]) => {
    Object.entries(values).forEach(([key, value]) => {
      scss += `$color-${category}-${key}: ${value};\n`;
    });
  });
  
  // 스페이싱 변수
  scss += '\n// Spacing\n';
  Object.entries(gmarketDesignSystem.spacing).forEach(([key, value]) => {
    scss += `$spacing-${key}: ${value};\n`;
  });
  
  // 폰트 크기 변수
  scss += '\n// Font Sizes\n';
  Object.entries(gmarketDesignSystem.typography.fontSize).forEach(([key, value]) => {
    scss += `$font-size-${key}: ${value};\n`;
  });
  
  // 믹스인
  scss += '\n// Mixins\n';
  scss += '@mixin button-primary {\n';
  scss += '  background-color: $color-primary-main;\n';
  scss += '  color: white;\n';
  scss += '  padding: $spacing-sm $spacing-md;\n';
  scss += '  border-radius: $border-radius-md;\n';
  scss += '  &:hover {\n';
  scss += '    background-color: $color-primary-dark;\n';
  scss += '  }\n';
  scss += '}\n';
  
  return scss;
}

// TypeScript 타입 정의 생성
function generateTypeScriptTypes() {
  return `// G마켓 디자인 시스템 TypeScript 타입 정의

export interface GmarketDesignTokens {
  colors: {
    primary: {
      main: string;
      light: string;
      lighter: string;
      dark: string;
      darker: string;
    };
    secondary: {
      main: string;
      light: string;
      dark: string;
    };
    gray: Record<string, string>;
    semantic: {
      info: string;
      success: string;
      warning: string;
      error: string;
    };
  };
  typography: {
    fontSize: Record<string, string>;
    fontWeight: Record<string, number>;
    lineHeight: Record<string, number>;
    letterSpacing: Record<string, string>;
  };
  spacing: Record<string, string>;
  border: {
    width: Record<string, string>;
    radius: Record<string, string>;
  };
  shadows: Record<string, string>;
}

export type ButtonVariant = 'primary' | 'secondary' | 'danger' | 'ghost';
export type ButtonSize = 'small' | 'medium' | 'large';
export type ComponentState = 'default' | 'hover' | 'pressed' | 'disabled';
`;
}

// 파일 저장 함수
function saveFiles() {
  const outputDir = path.join(process.cwd(), 'design-tokens');
  
  // 출력 디렉토리 생성
  if (!fs.existsSync(outputDir)) {
    fs.mkdirSync(outputDir, { recursive: true });
  }
  
  // 1. Figma Tokens 플러그인용 JSON
  const figmaTokens = convertToFigmaTokens();
  fs.writeFileSync(
    path.join(outputDir, 'gmarket-tokens-figma.json'),
    JSON.stringify(figmaTokens, null, 2)
  );
  console.log('✅ gmarket-tokens-figma.json 생성 완료');
  
  // 2. Style Dictionary용 JSON
  const styleDictionary = convertToStyleDictionary();
  fs.writeFileSync(
    path.join(outputDir, 'gmarket-tokens-style-dictionary.json'),
    JSON.stringify(styleDictionary, null, 2)
  );
  console.log('✅ gmarket-tokens-style-dictionary.json 생성 완료');
  
  // 3. 컴포넌트 구조 JSON
  fs.writeFileSync(
    path.join(outputDir, 'gmarket-components.json'),
    JSON.stringify(componentStructure, null, 2)
  );
  console.log('✅ gmarket-components.json 생성 완료');
  
  // 4. CSS 변수 파일
  const cssVariables = generateCSSVariables();
  fs.writeFileSync(
    path.join(outputDir, 'gmarket-tokens.css'),
    cssVariables
  );
  console.log('✅ gmarket-tokens.css 생성 완료');
  
  // 5. SCSS 변수 파일
  const scssVariables = generateSCSSVariables();
  fs.writeFileSync(
    path.join(outputDir, 'gmarket-tokens.scss'),
    scssVariables
  );
  console.log('✅ gmarket-tokens.scss 생성 완료');
  
  // 6. Tailwind Config
  const tailwindConfig = generateTailwindConfig();
  fs.writeFileSync(
    path.join(outputDir, 'gmarket-tailwind.config.js'),
    `module.exports = ${JSON.stringify(tailwindConfig, null, 2)}`
  );
  console.log('✅ gmarket-tailwind.config.js 생성 완료');
  
  // 7. TypeScript 타입 정의
  const tsTypes = generateTypeScriptTypes();
  fs.writeFileSync(
    path.join(outputDir, 'gmarket-tokens.d.ts'),
    tsTypes
  );
  console.log('✅ gmarket-tokens.d.ts 생성 완료');
  
  // 8. README 파일 생성
  const readme = `# G마켓 디자인 토큰

## 📁 생성된 파일들

1. **gmarket-tokens-figma.json**
   - Figma Tokens 플러그인에서 직접 임포트 가능
   - 사용법: Figma에서 Tokens 플러그인 실행 → Import → 이 파일 선택

2. **gmarket-tokens-style-dictionary.json**
   - Style Dictionary로 다양한 플랫폼용 코드 생성 가능
   - iOS, Android, Web 등 멀티 플랫폼 지원

3. **gmarket-components.json**
   - 컴포넌트별 상세 속성 정의
   - Figma 컴포넌트 생성 시 참고용

4. **gmarket-tokens.css**
   - CSS 변수로 정의된 디자인 토큰
   - HTML/CSS 프로젝트에서 바로 사용 가능

5. **gmarket-tokens.scss**
   - SCSS 변수와 믹스인
   - Sass 프로젝트에서 사용

6. **gmarket-tailwind.config.js**
   - Tailwind CSS 설정 파일
   - Tailwind 프로젝트에 바로 적용 가능

7. **gmarket-tokens.d.ts**
   - TypeScript 타입 정의
   - 타입 안정성 보장

## 🚀 사용 방법

### Figma에서 사용
1. Figma Tokens 플러그인 설치
2. 플러그인 실행 → Import
3. \`gmarket-tokens-figma.json\` 파일 선택
4. 자동으로 모든 스타일 생성됨

### React 프로젝트에서 사용
\`\`\`javascript
import tokens from './design-tokens/gmarket-tokens-style-dictionary.json';

const Button = styled.button\`
  background-color: \${tokens.color.primary.main};
  padding: \${tokens.size.spacing.md};
  border-radius: \${tokens.size.borderRadius.md};
\`;
\`\`\`

### CSS에서 사용
\`\`\`html
<link rel="stylesheet" href="./design-tokens/gmarket-tokens.css">

<style>
  .button {
    background-color: var(--color-primary-main);
    padding: var(--spacing-md);
    border-radius: var(--border-radius-md);
  }
</style>
\`\`\`

## 📊 토큰 통계
- 색상: ${Object.keys(gmarketDesignSystem.colors).reduce((acc, cat) => acc + Object.keys(gmarketDesignSystem.colors[cat]).length, 0)}개
- 타이포그래피: ${Object.keys(gmarketDesignSystem.typography.fontSize).length}개 크기
- 스페이싱: ${Object.keys(gmarketDesignSystem.spacing).length}개
- 그림자: ${Object.keys(gmarketDesignSystem.shadows).length}개
- 컴포넌트: ${Object.keys(componentStructure).length}개

생성 시간: ${new Date().toLocaleString('ko-KR')}
`;
  
  fs.writeFileSync(
    path.join(outputDir, 'README.md'),
    readme
  );
  console.log('✅ README.md 생성 완료');
  
  console.log(`\n🎉 모든 파일이 ${outputDir} 폴더에 생성되었습니다!`);
  console.log('\n📝 Figma에서 사용하려면:');
  console.log('1. Figma Tokens 플러그인 설치');
  console.log('2. gmarket-tokens-figma.json 파일 임포트');
  console.log('3. 자동으로 모든 디자인 토큰이 생성됩니다!\n');
}

// 메인 실행
function main() {
  console.log('🚀 G마켓 디자인 토큰 추출 시작...\n');
  
  try {
    saveFiles();
    
    // 요약 정보 출력
    console.log('\n📊 디자인 토큰 요약:');
    console.log('================================');
    console.log(`Primary Color: ${gmarketDesignSystem.colors.primary.main}`);
    console.log(`Font Sizes: ${Object.keys(gmarketDesignSystem.typography.fontSize).length}개`);
    console.log(`Spacing Values: ${Object.keys(gmarketDesignSystem.spacing).length}개`);
    console.log(`Components: ${Object.keys(componentStructure).length}개`);
    console.log('================================\n');
    
  } catch (error) {
    console.error('❌ 오류 발생:', error.message);
    process.exit(1);
  }
}

// 스크립트 실행
if (require.main === module) {
  main();
}

// 모듈로 내보내기 (다른 스크립트에서 사용 가능)
module.exports = {
  gmarketDesignSystem,
  componentStructure,
  convertToFigmaTokens,
  convertToStyleDictionary,
  generateCSSVariables,
  generateSCSSVariables,
  generateTailwindConfig,
  generateTypeScriptTypes
};