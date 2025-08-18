import React, { useState, useEffect, useRef } from 'react';
import { ChevronDown, ChevronRight, X, Check, AlertCircle, Info, ChevronLeft, Search, Menu, ShoppingCart, User, Heart, Star, Plus, Minus, Home, Filter, MoreVertical } from 'lucide-react';

// G마켓 디자인 시스템 컬러 팔레트
const colors = {
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
  }
};

// Button 컴포넌트
const Button = ({ children, variant = 'primary', size = 'medium', fullWidth = false, disabled = false, onClick, icon, ...props }) => {
  const baseClass = 'inline-flex items-center justify-center font-medium transition-all duration-200 rounded-md focus:outline-none focus:ring-2 focus:ring-offset-2';
  
  const variants = {
    primary: `bg-green-500 text-white hover:bg-green-600 focus:ring-green-500 ${disabled ? 'opacity-50 cursor-not-allowed' : ''}`,
    secondary: `bg-white text-gray-700 border border-gray-300 hover:bg-gray-50 focus:ring-gray-500 ${disabled ? 'opacity-50 cursor-not-allowed' : ''}`,
    danger: `bg-red-500 text-white hover:bg-red-600 focus:ring-red-500 ${disabled ? 'opacity-50 cursor-not-allowed' : ''}`,
    ghost: `text-gray-700 hover:bg-gray-100 focus:ring-gray-500 ${disabled ? 'opacity-50 cursor-not-allowed' : ''}`
  };
  
  const sizes = {
    small: 'px-3 py-1.5 text-xs',
    medium: 'px-4 py-2 text-sm',
    large: 'px-6 py-3 text-base'
  };
  
  return (
    <button
      className={`${baseClass} ${variants[variant]} ${sizes[size]} ${fullWidth ? 'w-full' : ''} ${props.className || ''}`}
      disabled={disabled}
      onClick={onClick}
      {...props}
    >
      {icon && <span className="mr-2">{icon}</span>}
      {children}
    </button>
  );
};

// Badge 컴포넌트
const Badge = ({ children, variant = 'default', size = 'medium' }) => {
  const variants = {
    default: 'bg-gray-100 text-gray-700',
    primary: 'bg-green-100 text-green-700',
    success: 'bg-green-100 text-green-700',
    warning: 'bg-yellow-100 text-yellow-700',
    error: 'bg-red-100 text-red-700',
    info: 'bg-blue-100 text-blue-700'
  };
  
  const sizes = {
    small: 'px-2 py-0.5 text-xs',
    medium: 'px-2.5 py-1 text-sm',
    large: 'px-3 py-1.5 text-base'
  };
  
  return (
    <span className={`inline-flex items-center rounded-full font-medium ${variants[variant]} ${sizes[size]}`}>
      {children}
    </span>
  );
};

// Chip 컴포넌트
const Chip = ({ label, selected = false, onDelete, onClick }) => {
  return (
    <div
      className={`inline-flex items-center px-3 py-1.5 rounded-full text-sm font-medium transition-colors cursor-pointer
        ${selected ? 'bg-green-500 text-white' : 'bg-gray-100 text-gray-700 hover:bg-gray-200'}`}
      onClick={onClick}
    >
      {label}
      {onDelete && (
        <button
          onClick={(e) => {
            e.stopPropagation();
            onDelete();
          }}
          className="ml-2 -mr-1"
        >
          <X className="h-3 w-3" />
        </button>
      )}
    </div>
  );
};

// Card 컴포넌트
const Card = ({ children, className = '', hoverable = false, onClick }) => {
  return (
    <div
      className={`bg-white rounded-lg border border-gray-200 ${hoverable ? 'hover:shadow-lg transition-shadow cursor-pointer' : ''} ${className}`}
      onClick={onClick}
    >
      {children}
    </div>
  );
};

// ItemCard 컴포넌트
const ItemCard = ({ image, title, price, originalPrice, discount, rating, reviewCount, freeShipping, onClick }) => {
  return (
    <Card hoverable onClick={onClick} className="overflow-hidden">
      <div className="aspect-square bg-gray-100 relative">
        {image ? (
          <img src={image} alt={title} className="w-full h-full object-cover" />
        ) : (
          <div className="w-full h-full flex items-center justify-center text-gray-400">
            <ShoppingCart className="w-12 h-12" />
          </div>
        )}
        {discount && (
          <div className="absolute top-2 left-2">
            <Badge variant="error">{discount}% 할인</Badge>
          </div>
        )}
      </div>
      <div className="p-4">
        <h3 className="text-sm font-normal text-gray-700 mb-2 line-clamp-2">{title}</h3>
        <div className="flex items-end gap-2 mb-2">
          {originalPrice && (
            <span className="text-xs text-gray-500 line-through">{originalPrice.toLocaleString()}원</span>
          )}
          <span className="text-lg font-bold text-gray-900">{price.toLocaleString()}원</span>
        </div>
        {rating && (
          <div className="flex items-center gap-1 mb-2">
            <div className="flex">
              {[...Array(5)].map((_, i) => (
                <Star
                  key={i}
                  className={`w-3 h-3 ${i < rating ? 'fill-yellow-400 text-yellow-400' : 'text-gray-300'}`}
                />
              ))}
            </div>
            <span className="text-xs text-gray-600">({reviewCount})</span>
          </div>
        )}
        {freeShipping && (
          <Badge variant="primary" size="small">무료배송</Badge>
        )}
      </div>
    </Card>
  );
};

// TextField 컴포넌트
const TextField = ({ label, value, onChange, placeholder, error, helperText, type = 'text', ...props }) => {
  return (
    <div className="w-full">
      {label && (
        <label className="block text-sm font-medium text-gray-700 mb-1">
          {label}
        </label>
      )}
      <input
        type={type}
        value={value}
        onChange={onChange}
        placeholder={placeholder}
        className={`w-full px-3 py-2 border rounded-md text-sm transition-colors focus:outline-none focus:ring-2 focus:ring-green-500
          ${error ? 'border-red-500' : 'border-gray-300 hover:border-gray-400'}`}
        {...props}
      />
      {(error && helperText) && (
        <p className="mt-1 text-xs text-red-500">{helperText}</p>
      )}
      {(!error && helperText) && (
        <p className="mt-1 text-xs text-gray-500">{helperText}</p>
      )}
    </div>
  );
};

// Select 컴포넌트
const Select = ({ label, value, onChange, options, placeholder = '선택하세요' }) => {
  const [isOpen, setIsOpen] = useState(false);
  const dropdownRef = useRef(null);
  
  useEffect(() => {
    const handleClickOutside = (event) => {
      if (dropdownRef.current && !dropdownRef.current.contains(event.target)) {
        setIsOpen(false);
      }
    };
    
    document.addEventListener('mousedown', handleClickOutside);
    return () => document.removeEventListener('mousedown', handleClickOutside);
  }, []);
  
  const selectedOption = options.find(opt => opt.value === value);
  
  return (
    <div className="relative w-full" ref={dropdownRef}>
      {label && (
        <label className="block text-sm font-medium text-gray-700 mb-1">
          {label}
        </label>
      )}
      <button
        onClick={() => setIsOpen(!isOpen)}
        className="w-full px-3 py-2 text-left border border-gray-300 rounded-md bg-white hover:border-gray-400 focus:outline-none focus:ring-2 focus:ring-green-500 flex items-center justify-between"
      >
        <span className={selectedOption ? 'text-gray-900' : 'text-gray-500'}>
          {selectedOption ? selectedOption.label : placeholder}
        </span>
        <ChevronDown className={`w-4 h-4 text-gray-500 transition-transform ${isOpen ? 'rotate-180' : ''}`} />
      </button>
      
      {isOpen && (
        <div className="absolute z-10 w-full mt-1 bg-white border border-gray-200 rounded-md shadow-lg">
          {options.map((option) => (
            <button
              key={option.value}
              onClick={() => {
                onChange(option.value);
                setIsOpen(false);
              }}
              className={`w-full px-3 py-2 text-left text-sm hover:bg-gray-50 transition-colors
                ${value === option.value ? 'bg-green-50 text-green-600' : 'text-gray-700'}`}
            >
              {option.label}
            </button>
          ))}
        </div>
      )}
    </div>
  );
};

// Checkbox 컴포넌트
const Checkbox = ({ label, checked, onChange, disabled = false }) => {
  return (
    <label className="flex items-center cursor-pointer">
      <input
        type="checkbox"
        checked={checked}
        onChange={onChange}
        disabled={disabled}
        className="sr-only"
      />
      <div className={`w-5 h-5 rounded border-2 flex items-center justify-center transition-colors
        ${checked ? 'bg-green-500 border-green-500' : 'bg-white border-gray-300'}
        ${disabled ? 'opacity-50 cursor-not-allowed' : 'hover:border-green-500'}`}>
        {checked && <Check className="w-3 h-3 text-white" />}
      </div>
      {label && (
        <span className={`ml-2 text-sm ${disabled ? 'text-gray-400' : 'text-gray-700'}`}>
          {label}
        </span>
      )}
    </label>
  );
};

// Radio 컴포넌트
const Radio = ({ label, name, value, checked, onChange, disabled = false }) => {
  return (
    <label className="flex items-center cursor-pointer">
      <input
        type="radio"
        name={name}
        value={value}
        checked={checked}
        onChange={onChange}
        disabled={disabled}
        className="sr-only"
      />
      <div className={`w-5 h-5 rounded-full border-2 flex items-center justify-center transition-colors
        ${checked ? 'border-green-500' : 'border-gray-300'}
        ${disabled ? 'opacity-50 cursor-not-allowed' : 'hover:border-green-500'}`}>
        {checked && <div className="w-2.5 h-2.5 rounded-full bg-green-500" />}
      </div>
      {label && (
        <span className={`ml-2 text-sm ${disabled ? 'text-gray-400' : 'text-gray-700'}`}>
          {label}
        </span>
      )}
    </label>
  );
};

// Switch 컴포넌트
const Switch = ({ label, checked, onChange, disabled = false }) => {
  return (
    <label className="flex items-center cursor-pointer">
      <button
        type="button"
        role="switch"
        aria-checked={checked}
        onClick={() => !disabled && onChange(!checked)}
        className={`relative inline-flex h-6 w-11 items-center rounded-full transition-colors
          ${checked ? 'bg-green-500' : 'bg-gray-300'}
          ${disabled ? 'opacity-50 cursor-not-allowed' : ''}`}
      >
        <span
          className={`inline-block h-4 w-4 transform rounded-full bg-white transition-transform
            ${checked ? 'translate-x-6' : 'translate-x-1'}`}
        />
      </button>
      {label && (
        <span className={`ml-3 text-sm ${disabled ? 'text-gray-400' : 'text-gray-700'}`}>
          {label}
        </span>
      )}
    </label>
  );
};

// Dialog 컴포넌트
const Dialog = ({ open, onClose, title, children, actions }) => {
  if (!open) return null;
  
  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center">
      <div className="fixed inset-0 bg-black bg-opacity-50" onClick={onClose} />
      <div className="relative bg-white rounded-lg shadow-xl max-w-md w-full mx-4">
        <div className="flex items-center justify-between p-4 border-b border-gray-200">
          <h2 className="text-lg font-bold text-gray-900">{title}</h2>
          <button
            onClick={onClose}
            className="p-1 hover:bg-gray-100 rounded-lg transition-colors"
          >
            <X className="w-5 h-5 text-gray-500" />
          </button>
        </div>
        <div className="p-4">
          {children}
        </div>
        {actions && (
          <div className="flex gap-2 p-4 border-t border-gray-200">
            {actions}
          </div>
        )}
      </div>
    </div>
  );
};

// Accordion 컴포넌트
const Accordion = ({ items }) => {
  const [openItems, setOpenItems] = useState([]);
  
  const toggleItem = (index) => {
    setOpenItems(prev =>
      prev.includes(index)
        ? prev.filter(i => i !== index)
        : [...prev, index]
    );
  };
  
  return (
    <div className="border border-gray-200 rounded-lg overflow-hidden">
      {items.map((item, index) => (
        <div key={index} className={index > 0 ? 'border-t border-gray-200' : ''}>
          <button
            className="w-full px-4 py-3 flex items-center justify-between text-left hover:bg-gray-50 transition-colors"
            onClick={() => toggleItem(index)}
          >
            <span className="font-medium text-gray-900">{item.title}</span>
            {openItems.includes(index) ? (
              <ChevronDown className="w-5 h-5 text-gray-500" />
            ) : (
              <ChevronRight className="w-5 h-5 text-gray-500" />
            )}
          </button>
          {openItems.includes(index) && (
            <div className="px-4 py-3 bg-gray-50 text-gray-700">
              {item.content}
            </div>
          )}
        </div>
      ))}
    </div>
  );
};

// Tabs 컴포넌트
const Tabs = ({ tabs, defaultTab = 0 }) => {
  const [activeTab, setActiveTab] = useState(defaultTab);
  
  return (
    <div>
      <div className="flex border-b border-gray-200">
        {tabs.map((tab, index) => (
          <button
            key={index}
            className={`px-4 py-3 text-sm font-medium border-b-2 transition-colors
              ${activeTab === index
                ? 'text-green-600 border-green-600'
                : 'text-gray-500 border-transparent hover:text-gray-700'}`}
            onClick={() => setActiveTab(index)}
          >
            {tab.label}
          </button>
        ))}
      </div>
      <div className="py-4">
        {tabs[activeTab].content}
      </div>
    </div>
  );
};

// Banner 컴포넌트
const Banner = ({ variant = 'info', title, description, action, onClose }) => {
  const variants = {
    info: 'bg-blue-50 border-blue-200 text-blue-800',
    success: 'bg-green-50 border-green-200 text-green-800',
    warning: 'bg-yellow-50 border-yellow-200 text-yellow-800',
    error: 'bg-red-50 border-red-200 text-red-800'
  };
  
  const icons = {
    info: <Info className="w-5 h-5" />,
    success: <Check className="w-5 h-5" />,
    warning: <AlertCircle className="w-5 h-5" />,
    error: <X className="w-5 h-5" />
  };
  
  return (
    <div className={`p-4 border rounded-lg ${variants[variant]}`}>
      <div className="flex">
        <div className="flex-shrink-0">{icons[variant]}</div>
        <div className="ml-3 flex-1">
          {title && <h3 className="text-sm font-medium">{title}</h3>}
          {description && <p className="mt-1 text-sm">{description}</p>}
          {action && <div className="mt-3">{action}</div>}
        </div>
        {onClose && (
          <button onClick={onClose} className="ml-3">
            <X className="w-5 h-5" />
          </button>
        )}
      </div>
    </div>
  );
};

// Toast 컴포넌트
const Toast = ({ message, variant = 'info', duration = 3000, onClose }) => {
  useEffect(() => {
    const timer = setTimeout(onClose, duration);
    return () => clearTimeout(timer);
  }, [duration, onClose]);
  
  const variants = {
    info: 'bg-blue-500',
    success: 'bg-green-500',
    warning: 'bg-yellow-500',
    error: 'bg-red-500'
  };
  
  return (
    <div className={`fixed bottom-4 right-4 z-50 px-4 py-3 rounded-lg text-white shadow-lg ${variants[variant]}`}>
      <div className="flex items-center gap-3">
        <span>{message}</span>
        <button onClick={onClose} className="text-white/80 hover:text-white">
          <X className="w-4 h-4" />
        </button>
      </div>
    </div>
  );
};

// Navigation Bar 컴포넌트
const NavigationBar = () => {
  const [searchQuery, setSearchQuery] = useState('');
  const [mobileMenuOpen, setMobileMenuOpen] = useState(false);
  
  return (
    <nav className="bg-white border-b border-gray-200">
      <div className="bg-green-500 text-white text-xs py-2">
        <div className="container mx-auto px-4 flex justify-between items-center">
          <span>스마일클럽 가입하고 최대 15% 할인받기!</span>
          <div className="flex gap-4">
            <a href="#" className="hover:underline">고객센터</a>
            <a href="#" className="hover:underline">판매자센터</a>
          </div>
        </div>
      </div>
      
      <div className="container mx-auto px-4">
        <div className="flex items-center justify-between py-4">
          <div className="flex items-center gap-8">
            <h1 className="text-2xl font-bold text-green-500">Gmarket</h1>
            
            <div className="hidden lg:flex items-center gap-6">
              <button className="flex items-center gap-1 text-gray-700 hover:text-green-500">
                <Menu className="w-5 h-5" />
                <span className="font-medium">전체 카테고리</span>
              </button>
              <a href="#" className="text-gray-700 hover:text-green-500 font-medium">베스트</a>
              <a href="#" className="text-gray-700 hover:text-green-500 font-medium">슈퍼딜</a>
              <a href="#" className="text-gray-700 hover:text-green-500 font-medium">쿠폰/이벤트</a>
            </div>
          </div>
          
          <div className="flex-1 max-w-xl mx-8 hidden md:block">
            <div className="relative">
              <input
                type="text"
                value={searchQuery}
                onChange={(e) => setSearchQuery(e.target.value)}
                placeholder="찾고 싶은 상품을 검색해보세요!"
                className="w-full px-4 py-2 pr-10 border border-green-500 rounded-full focus:outline-none focus:ring-2 focus:ring-green-500"
              />
              <button className="absolute right-2 top-1/2 -translate-y-1/2 p-1.5 bg-green-500 text-white rounded-full">
                <Search className="w-4 h-4" />
              </button>
            </div>
          </div>
          
          <div className="flex items-center gap-4">
            <button className="p-2 text-gray-600 hover:text-gray-900">
              <Heart className="w-6 h-6" />
            </button>
            <button className="p-2 text-gray-600 hover:text-gray-900 relative">
              <ShoppingCart className="w-6 h-6" />
              <span className="absolute -top-1 -right-1 bg-red-500 text-white text-xs rounded-full w-5 h-5 flex items-center justify-center">
                3
              </span>
            </button>
            <button className="p-2 text-gray-600 hover:text-gray-900">
              <User className="w-6 h-6" />
            </button>
            
            <button
              className="lg:hidden p-2 text-gray-600 hover:text-gray-900"
              onClick={() => setMobileMenuOpen(!mobileMenuOpen)}
            >
              <Menu className="w-6 h-6" />
            </button>
          </div>
        </div>
      </div>
    </nav>
  );
};

// 메인 앱 컴포넌트
export default function GmarketDesignSystem() {
  const [activeSection, setActiveSection] = useState('overview');
  const [dialogOpen, setDialogOpen] = useState(false);
  const [toastVisible, setToastVisible] = useState(false);
  const [checkboxChecked, setCheckboxChecked] = useState(false);
  const [radioValue, setRadioValue] = useState('option1');
  const [switchChecked, setSwitchChecked] = useState(false);
  const [selectValue, setSelectValue] = useState('');
  const [textFieldValue, setTextFieldValue] = useState('');
  
  const sampleProducts = [
    {
      id: 1,
      title: '애플 아이폰 15 프로 맥스 256GB 자급제',
      price: 1590000,
      originalPrice: 1890000,
      discount: 15,
      rating: 4.5,
      reviewCount: 324,
      freeShipping: true
    },
    {
      id: 2,
      title: '삼성 갤럭시 버즈2 프로 블루투스 이어폰',
      price: 189000,
      originalPrice: 279000,
      discount: 32,
      rating: 4.8,
      reviewCount: 1523,
      freeShipping: true
    },
    {
      id: 3,
      title: 'LG 그램 17인치 2024 노트북 17Z90S',
      price: 2190000,
      originalPrice: 2490000,
      discount: 12,
      rating: 4.7,
      reviewCount: 89,
      freeShipping: true
    },
    {
      id: 4,
      title: '다이슨 V15 디텍트 무선청소기',
      price: 899000,
      originalPrice: 1099000,
      discount: 18,
      rating: 4.9,
      reviewCount: 567,
      freeShipping: false
    }
  ];
  
  const accordionItems = [
    { title: '배송 정보', content: '전국 무료배송! 오늘 주문하면 내일 도착합니다.' },
    { title: '교환 및 반품', content: '구매 후 7일 이내 교환 및 반품이 가능합니다.' },
    { title: '결제 방법', content: '신용카드, 체크카드, 무통장입금, 간편결제 등 다양한 결제수단을 지원합니다.' }
  ];
  
  const tabItems = [
    { label: '상품정보', content: <div>상품에 대한 자세한 정보가 여기에 표시됩니다.</div> },
    { label: '리뷰', content: <div>고객 리뷰가 여기에 표시됩니다.</div> },
    { label: '문의', content: <div>상품 문의 내용이 여기에 표시됩니다.</div> },
    { label: '배송/교환/반품', content: <div>배송 및 교환/반품 정보가 여기에 표시됩니다.</div> }
  ];
  
  const selectOptions = [
    { value: 'option1', label: '옵션 1' },
    { value: 'option2', label: '옵션 2' },
    { value: 'option3', label: '옵션 3' }
  ];
  
  return (
    <div className="min-h-screen bg-gray-50 flex flex-col">
      <NavigationBar />
      
      <div className="flex-1 container mx-auto px-4 py-8">
        {/* Section Navigation */}
        <div className="mb-8 flex gap-2 flex-wrap">
          {['overview', 'components', 'examples'].map((section) => (
            <Button
              key={section}
              variant={activeSection === section ? 'primary' : 'ghost'}
              onClick={() => setActiveSection(section)}
            >
              {section.charAt(0).toUpperCase() + section.slice(1)}
            </Button>
          ))}
        </div>
        
        {/* Overview Section */}
        {activeSection === 'overview' && (
          <div className="space-y-8">
            <Card className="p-8">
              <h1 className="text-3xl font-bold text-gray-900 mb-4">G마켓 디자인 시스템</h1>
              <p className="text-gray-600 mb-6">
                G마켓의 브랜드 정체성, 사용성, 일관성을 고려한 디자인 시스템입니다.
                모든 컴포넌트는 실제 상용 서비스 수준의 완성도를 가지고 있습니다.
              </p>
              
              <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
                <div className="text-center p-6 bg-green-50 rounded-lg">
                  <div className="w-16 h-16 bg-green-500 rounded-full mx-auto mb-4 flex items-center justify-center text-white text-2xl font-bold">
                    G
                  </div>
                  <h3 className="font-bold text-gray-900 mb-2">브랜드</h3>
                  <p className="text-sm text-gray-600">G마켓만의 독특한 아이덴티티</p>
                </div>
                <div className="text-center p-6 bg-blue-50 rounded-lg">
                  <div className="w-16 h-16 bg-blue-500 rounded-full mx-auto mb-4 flex items-center justify-center text-white">
                    <Filter className="w-8 h-8" />
                  </div>
                  <h3 className="font-bold text-gray-900 mb-2">파운데이션</h3>
                  <p className="text-sm text-gray-600">색상, 타이포그래피, 스페이싱</p>
                </div>
                <div className="text-center p-