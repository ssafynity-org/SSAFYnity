import React, { useState } from "react";
import { useSelector } from "react-redux";
import { useLocation, Link } from "react-router-dom";
import "../styles/SearchBar.css";

function SearchBar({ selectedMenu, selectedCompany, onCategorySelect }) {
  const [selectedCategories, setSelectedCategories] = useState([]);

  const [companyList, setCompanyList] = useState([
    { name: "토스", img: "/images/toss.jpg" },
    { name: "카카오", img: "/images/kakao.png" },
    { name: "우아한형제들", img: "/images/woowahan.png" },
    { name: "당근마켓", img: "/images/daangn.png" },
    { name: "여기어때", img: "/images/yeogi.jpg" },
  ]);
  
  const categories = [
    { name: "백엔드", width:53 },
    { name: "프론트엔드", width:70 },
    { name: "배포", width:30 },
    { name: "임베디드", width:63 },
    { name: "보안", width:31 },
    { name: "네트워크", width:60 },
    { name: "자료구조", width:58 },
    { name: "DB" , width:46},
    { name: "UI/UX" , width:50},
    { name: "운영체제", width:56 },
    { name: "디자인패턴", width:69 },
    { name: "취업", width:29 },
  ];

  const handleCompanyClick = (company) => {
    console.log(company);
    selectedCompany(company);
  };
  
  const handleCategoryClick = (category) => {
    const newSelectedCategories = [...selectedCategories];
    
    // 이미 선택된 카테고리면 제거, 아니면 추가
    if (newSelectedCategories.includes(category)) {
      const index = newSelectedCategories.indexOf(category);
      newSelectedCategories.splice(index, 1);
    } else {
      newSelectedCategories.push(category);
    }
    
    setSelectedCategories(newSelectedCategories);
    if (onCategorySelect) {
      onCategorySelect(newSelectedCategories);
    }
  };
  
  const handleSearch = () => {
    // 검색 버튼 클릭 시 필터링 로직 실행
    console.log("검색 실행:", {
      selectedCompany: selectedCompany,
      selectedCategories: selectedCategories
    });
    // 여기에 검색 로직 추가
  };

  return (
    <div className="search-bar">
      {(
        <div className="search-content">
          <div className="company">
            {companyList.map((company, index) => (
              <div 
                key={index}
                className="company-item" 
                onClick={() => handleCompanyClick(company.name)}
              >
                <img src={company.img} alt={company.name} height="30px" />
                <span>{company.name}</span>
              </div>
            ))}
          </div>
          <div className="job-categories">
          {/* {categories.map((category, index) => (
            <div 
              key={index}
              className={`job-category-item ${selectedCategories.includes(category.name) ? "selected" : ""}`}
              style={{ width: `${category.width}px` }}
              onClick={() => handleCategoryClick(category.name)}
            >
              {category.name}
            </div>
          ))} */}
        </div>
          
          {/* <button className="search-button" onClick={handleSearch}>
            검색
          </button> */}
        </div>
      )}
    </div>
  );
}

export default SearchBar;