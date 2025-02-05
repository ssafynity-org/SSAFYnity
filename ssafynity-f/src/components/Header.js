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
                    <Link to="/introduction">SSAFYnity</Link>
                    <Link to="/introduction/mento">개발 연사님</Link>
                    <Link to="/introduction/maneger">운영진</Link>
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
                    <Link to="/conference/ssafynale">SSAFY-nale</Link>
                    <Link to="/conference/seminar">SSAFYnity-Seminar</Link>
                    <Link to="/conference/external">외부 컨퍼런스</Link>
                    <Link to="/conference/hackerton">해커톤</Link>
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
                    <Link to="/community/board">자유 게시판</Link>
                    <Link to="/community/group">그룹 활동</Link>
                    <Link to="/community/mycampus">마이 캠퍼스</Link>
                    <Link to="/community/ssafycial">SSAFYcial</Link>
                    <Link to="/community/letter">드림 레터</Link>
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
                      
                      <Link to="/mentoring/counsel">이직 상담</Link>
                      <Link to="/mentoring/coffeechat">Coffee Chat</Link>
                      <Link to="/mentoring/content">멘토링 영상</Link>
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
                      <Link to="/helpdesk/suggestion">건의 사항</Link>
                      <Link to="/helpdesk/qna">Q&A</Link>
                      <Link to="/helpdesk/report">이용자 신고</Link>
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
