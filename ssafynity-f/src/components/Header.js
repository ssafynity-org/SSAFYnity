import React, { useState, useRef, useEffect } from "react";
import { useSelector } from "react-redux"; // useSelector import
import { useLocation, Link } from "react-router-dom";
import "../styles/Header.css";

function Header() {
  const user = useSelector(state => state.user.userInfo); // Redux store에서 user 데이터 가져오기
  const location = useLocation(); // 현재 위치 정보를 가져옵니다.
  const [dropdownVisible, setDropdownVisible] = useState(false);
  const navRef = useRef(null);
  const dropdownRef = useRef(null);

  useEffect(() => {
    if (navRef.current && dropdownRef.current) {
      const navRect = navRef.current.getBoundingClientRect();
      dropdownRef.current.style.width = `${navRect.width}px`;
      dropdownRef.current.style.left = `${navRect.left}px`;
    }
  }, [dropdownVisible]);


  // 로그인 페이지에서는 헤더를 숨깁니다.
  if (location.pathname === "/login" || location.pathname === "/signup") {
    return null;
  }
  
  return (
    <header
      className="header"
      onMouseEnter={() => setDropdownVisible(true)}
      onMouseLeave={() => setDropdownVisible(false)}
    >
      <div className="header-container">
        <div className="header-logo">
          <Link to="/main">
            <img src="/images/bigLogo.png" alt="Logo" />
          </Link>
        </div>
        <nav className="header-navigation" ref={navRef}>
          <ul>
            <li><Link to="/mycampus">싸피니티 소개</Link></li>
            <li><Link to="/education">컨퍼런스</Link></li>
            <li><Link to="/community">커뮤니티</Link></li>
            <li><Link to="/mentoring">멘토링</Link></li>
            <li><Link to="/helpdesk">Help Desk</Link></li>
          </ul>
        </nav>
        <div className="header-link">
          <div className="header-support">
            <img src="/images/아이들과미래재단후원하기.png" alt="아이들과미래재단후원하기" />
          </div>
          <div className="header-discord">
            <img src="/images/디스코드참여하기.png" alt="디스코드참여하기" />
          </div>
        </div>

        {/* ✅ 동적으로 크기 및 위치 조절된 드롭다운 메뉴 */}
        <div
          ref={dropdownRef}
          className={`dropdown-container ${dropdownVisible ? "visible" : ""}`}
        >
          <div className="dropdown-content">
            <div className="dropdown-section">
              <ul>
                <li><a href="#">레벨&마일리지</a></li>
                <li><a href="#">홈 선택창</a></li>
                <li><a href="#">학습용 이클립스</a></li>
                <li><a href="#">발표 목록</a></li>
                <li><a href="#">서류 제출</a></li>
                <li><a href="#">교육생 서약서</a></li>
                <li><a href="#">SSAFY e-book</a></li>
              </ul>
            </div>
            <div className="dropdown-section">
              <ul>
                <li><a href="#">리뷰어 버전기기</a></li>
                <li><a href="#">내용과 다시보기</a></li>
                <li><a href="#">진행력 다시보기</a></li>
                <li><a href="#">주차별 커리큘럼</a></li>
                <li><a href="#">Quest 평가</a></li>
                <li><a href="#">필수 학습</a></li>
                <li><a href="#">학습자료</a></li>
              </ul>
            </div>
            <div className="dropdown-section">
              <ul>
                <li><a href="#">설문조사</a></li>
                <li><a href="#">열린 게시판</a></li>
                <li><a href="#">익명 게시판</a></li>
                <li><a href="#">우리반 보기</a></li>
              </ul>
            </div>
            <div className="dropdown-section">
              <ul>
                <li><a href="#">공지사항</a></li>
                <li><a href="#">FAQ</a></li>
                <li><a href="#">1:1 문의</a></li>
                <li><a href="#">학사규정</a></li>
              </ul>
            </div>
            <div className="dropdown-section">
              <ul>
                <li><a href="#">멘토 스토리</a></li>
                <li><a href="#">멘토링</a></li>
                <li><a href="#">멘토링 공지사항</a></li>
                <li><a href="#">간담회 후기</a></li>
              </ul>
            </div>
          </div>
        </div>
      </div>
    </header>
  );
}

export default Header;