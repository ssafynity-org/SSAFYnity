import React, { useState } from "react";
import { useSelector } from "react-redux";
import { useLocation, Link } from "react-router-dom";
import "../styles/SearchBar.css";
import { eventWrapper } from "@testing-library/user-event/dist/utils";

function SearchBar({selectedMenu, selectedCompany}) {

  const [menu, setMenu] = useState("전체 글");

  const handleMenuClick = (menu) => {
    setMenu(menu);
    selectedMenu(menu);
  };

  const [companyList, setCompanyList] = new useState([
    { name: "토스", img: "/images/toss.jpg" },
    { name: "카카오", img: "/images/kakao.png" },
    { name: "우아한형제들", img: "/images/woowahan.png" },
    { name: "당근마켓", img: "/images/daangn.png" },
    { name: "여기어때", img: "/images/yeogi.jpg" },
  ]);

  const [company, setCompany] = new useState();

  const handleCompanyClick = (company) => {
    selectedCompany(company);
  }
  
  // 선택된 체크박스 상태 관리
  const [selected, setSelected] = useState({
    backend: false,
    frontend: false,
    design: false,
    network: false,
    os: false,
    web: false,
    mobile: false,
    embedded: false
  });

  // 체크박스 상태 변경 핸들러
  const handleCheckboxChange = (event) => {
    const { id } = event.target;
    if (!id) return; // ID가 없는 경우 방지
    
    setSelected((prevState) => ({
      ...prevState,
      [id]: !prevState[id] // 기존 값의 반대값으로 업데이트
    }));

    console.log(`${id}: ${!selected[id]}`); // 상태 변경 로그
  };  


  

  return (
    <div className="search-bar">
      <div className="search-menu">
        <div className="search-menu-item" onClick={() => handleMenuClick("전체 글")}><img src="/images/검색바전체.png" alt="" height="20px" decoding="async" />전체 글</div>
        <div className="search-menu-item" onClick={() => handleMenuClick("기업 별")}><img src="/images/검색바기업.png" alt="" height="20px" decoding="async" />기업 별</div>
        <div className="search-menu-item" onClick={() => handleMenuClick("상세 조건")}><img src="/images/검색바조건.png" alt="" height="20px" decoding="async" />상세 조건</div>
      </div>

      <div class='searchbar-v-line'></div>

      {(menu === "기업 별" || menu === "전체 글") && (
        <div className="company">
        {companyList.map((company) => (
          <div className="company-item" onClick={() => handleCompanyClick(company.name)}>
            <img src={company.img} alt={company.name} height="30px" />
            <span>{company.name}</span>
          </div>
        ))}
        </div>
      )}

      {menu === "상세 조건" && (
        <div className="condition">
          <div className="condition-category">
            <div className={`${selected.backend ? "selected" : "unselected"}`} id="backend" onClick={handleCheckboxChange}>백엔드</div>
            <div className={`${selected.frontend ? "selected" : "unselected"}`} id="frontend" onClick={handleCheckboxChange}>프론트엔드</div>
            <div className={`${selected.design ? "selected" : "unselected"}`} id="design" onClick={handleCheckboxChange}>디자인</div>
            <div className={`${selected.network ? "selected" : "unselected"}`} id="network" onClick={handleCheckboxChange}>네트워크</div>
            <div className={`${selected.os ? "selected" : "unselected"}`} id="os" onClick={handleCheckboxChange}>운영체제</div>
            <div className={`${selected.web ? "selected" : "unselected"}`} id="web" onClick={handleCheckboxChange}>웹</div>
            <div className={`${selected.mobile ? "selected" : "unselected"}`} id="mobile" onClick={handleCheckboxChange}>모바일</div>
            <div className={`${selected.embedded ? "selected" : "unselected"}`} id="embedded" onClick={handleCheckboxChange}>임베디드</div>
          </div>
        </div>
      )}
    </div>
    
  );
}

export default SearchBar;
