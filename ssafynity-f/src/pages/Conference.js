import React, { useState, useEffect } from "react";
import axiosInstance from "../api/axiosInstance";
import { useNavigate } from "react-router-dom"; // useNavigate import
import SearchBar from "../components/SearchBar";
import "../styles/Conference.css";

function Conference() {

  const [menu, setMenu] = useState("전체 글");
  const [companyList, setCompanyList] = useState([]); // ✅ 여러 개의 회사 선택 가능
  const [videoData, setVideoData] = useState([]); // ✅ API 데이터를 저장할 상태
  const [loading, setLoading] = useState(true); // ✅ 로딩 상태 추가

  const handleCompany = (companies) => {
    setCompanyList(companies);
  }

  const handleMenu = (menu) => {
    setMenu(menu);
    if(menu === "기업 별"){
      setCompanyList([]); // ✅ 메뉴가 변경되면 회사 선택 초기화
    }
  }

  // ✅ API 호출 함수
  useEffect(() => {
    const fetchVideoData = async () => {
      try {
        setLoading(true);
        const response = await axiosInstance.get("/videolist", {
          params: {
            tags: null, // 태그 필터가 있다면 추가
            companies: companyList.length > 0 ? companyList : null, // ✅ 다중 선택된 회사 리스트 전달
            page: 0, // 페이지네이션 (기본 첫 페이지)
            size: 10, // 가져올 영상 개수
          },
        });

        // ✅ API 응답을 상태에 저장
        setVideoData(response.data);
      } catch (error) {
        console.error("Error fetching video data:", error);
      } finally {
        setLoading(false);
      }
    };

    fetchVideoData();
  }, [companyList]); // ✅ `company` 값이 변경될 때 API 다시 호출

  return (
    <div className="conference-page">
      <SearchBar selectedMenu={handleMenu} selectedCompany={handleCompany} />
      <div className="conference-wrap">
      <div className="video-list">
      {videoData.map((video) => (
        <div key={video.id} className="video-container">
          <div className="video-thumbnail">
            <img src={video.thumbnail} alt="video thumbnail" />
          </div>
          <div className="video-info">
            <div className="video-titleAndPost">
              <div className="video-title">
                <p>{video.title}</p>
              </div>
              <div className="video-post">
                <p>{video.views}</p>
                <p>•</p>
                <p>{video.time}</p>
              </div>
            </div>
            <div className="video-channel">
              <img src={video.channelImage} alt="channel" />
              <p>{video.channelName}</p>
            </div>
            <div className="video-description">
              <p>{video.description}</p>
            </div>
          </div>
        </div>
      ))}
    </div>
      </div>    
    </div>
  );
}

export default Conference;
