import React, { useState } from "react";
import { useSelector } from "react-redux";
import { useLocation, Link } from "react-router-dom";
import "../styles/Header.css";

function Header() {
  const user = useSelector((state) => state.user.userInfo);
  const location = useLocation();

  const [openDropdown, setOpenDropdown] = useState(null);

  const handleDropdown = (menu) => {
    setOpenDropdown((prev) => (prev === menu ? null : menu));
  };

  if (location.pathname === "/login" || location.pathname === "/signup") {
    return null;
  }

  return (
    <header className="header">
        <div className="header-logo">
          <Link to="/main">
            <img src="/images/bigLogo.png" alt="Logo" />
          </Link>
        </div>
        <nav className="header-navigation">
          <div className="nav-menu">
            <div className="menu-item" onClick={() => handleDropdown("introduction")}>
              <span>싸피니티 소개</span>
              {openDropdown === "introduction" && (
                <div className="dropdown-menu">
                  <div className="dropdown-link">
                    <Link to="/introduction/shareholders">소개1</Link>
                    <Link to="/introduction/meetings">소개2</Link>
                    <Link to="/introduction/board">소개3</Link>
                    <Link to="/introduction/rules">소개4</Link>
                    <Link to="/introduction/structure">소개5</Link>
                  </div>
                  <div class='dropdown-v-line'></div>
                  <img className="test-image" src="/images/상세헤더예시이미지.jpg" alt="test"/>
                </div>
              )}
            </div>

            <div className="menu-item" onClick={() => handleDropdown("conference")}>
              <span>컨퍼런스</span>
              {openDropdown === "conference" && (
                <div className="dropdown-menu">
                  <div className="dropdown-link">
                    <Link to="/conference/shareholders">Seminar</Link>
                    <Link to="/conference/meetings">외부 컨퍼런스</Link>
                    <Link to="/conference/board">컨퍼런스3</Link>
                    <Link to="/conference/rules">컨퍼런스4</Link>
                    <Link to="/conference/structure">컨퍼런스5</Link>
                  </div>
                  <div class='dropdown-v-line'></div>
                  <img className="test-image" src="/images/상세헤더예시이미지.jpg" alt="Logo"/>
                </div>
              )}
            </div>

            <div className="menu-item" onClick={() => handleDropdown("community")}>
              <span>커뮤니티</span>
              {openDropdown === "community" && (
                <div className="dropdown-menu">
                  <div className="dropdown-link">
                    <Link to="/community/shareholders">커뮤니티1</Link>
                    <Link to="/community/meetings">커뮤니티2</Link>
                    <Link to="/community/board">커뮤니티3</Link>
                    <Link to="/community/rules">커뮤니티4</Link>
                    <Link to="/community/structure">커뮤니티5</Link>
                    </div>
                    <div class='dropdown-v-line'></div>
                    <img className="test-image" src="/images/상세헤더예시이미지.jpg" alt="Logo"/>
                </div>
              )}
            </div>

            <div className="menu-item" onClick={() => handleDropdown("mentoring")}>
              <span>멘토링</span>
              {openDropdown === "mentoring" && (
                <div className="dropdown-menu">
                  <div className="dropdown-link">
                      <Link to="/mentoring/shareholders">멘토링1</Link>
                      <Link to="/mentoring/meetings">멘토링2</Link>
                      <Link to="/mentoring/board">멘토링3</Link>
                      <Link to="/mentoring/rules">멘토링4</Link>
                      <Link to="/mentoring/structure">멘토링5</Link>
                    </div>
                    <div class='dropdown-v-line'></div>
                    <img className="test-image" src="/images/상세헤더예시이미지.jpg" alt="Logo"/>
                </div>
              )}
            </div>

            <div className="menu-item" onClick={() => handleDropdown("helpdesk")}>
              <span>Help Desk</span>
              {openDropdown === "helpdesk" && (
                <div className="dropdown-menu">
                  <div className="dropdown-link">
                      <Link to="/helpdesk/shareholders">헬프데스크1</Link>
                      <Link to="/helpdesk/meetings">헬프데스크2</Link>
                      <Link to="/helpdesk/board">헬프데스크3</Link>
                      <Link to="/helpdesk/rules">헬프데스크4</Link>
                    </div>
                    <div class='dropdown-v-line'></div>
                    <img className="test-image" src="/images/상세헤더예시이미지.jpg" alt="Logo"/>
                </div>
              )}
            </div>
          </div>
        </nav>
        <div className="header-link">
          <div className="header-support">
            <img src="/images/아이들과미래재단후원하기.png" alt="아이들과미래재단후원하기" />
          </div>
          <div className="header-discord">
            <img src="/images/디스코드참여하기.png" alt="디스코드참여하기" />
          </div>
        </div>
    </header>
  );
}

export default Header;
