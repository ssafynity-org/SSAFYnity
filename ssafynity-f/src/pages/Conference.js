import React, { useState, useEffect } from "react";
import axiosInstance from "../api/axiosInstance";
import { useNavigate } from "react-router-dom"; // useNavigate import
import SearchBar from "../components/SearchBar";
import "../styles/Conference.css";

function Conference() {

  const [menu, setMenu] = useState("전체 글");

  const handleMenu = (menu) => {
    setMenu(menu);
    if(menu === "기업 별"){
      setCompany("")
    }
  }

  const [company, setCompany] = useState("");
  const handleCompany = (company) => {
    setCompany(company);
  }
  
  const videoData = [
    {
      id: 1,
      thumbnail: "https://i.ytimg.com/vi/CjC5OfzjPkk/maxresdefault.jpg",
      title: `"어느 날 고민 많은 주니어 개발자가 찾아왔다"김영한- 성장과 취업, 이직 이야기 | 인프콘 2023`,
      views: "조회수 2.2만회",
      time: "조회수 2년전",
      channelImage: "/images/상세헤더예시이미지.jpg",
      channelName: "인프런 inflearn",
      description: `"어느 날 고민 많은 주니어 개발자가 찾아왔다"김영한- 제 이야기를 통해 한 사람의 삶이 바뀌는 것은 저에게...`,
    },
    {
      id: 2,
      thumbnail: "https://i.ytimg.com/vi/CjC5OfzjPkk/maxresdefault.jpg",
      title: `"어느 날 고민 많은 주니어 개발자가 찾아왔다"김영한- 성장과 취업, 이직 이야기 | 인프콘 2023`,
      views: "조회수 2.2만회",
      time: "조회수 2년전",
      channelImage: "/images/상세헤더예시이미지.jpg",
      channelName: "인프런 inflearn",
      description: `"어느 날 고민 많은 주니어 개발자가 찾아왔다"김영한- 제 이야기를 통해 한 사람의 삶이 바뀌는 것은 저에게...`,
    },
    // 여러 개의 데이터 추가 가능
  ];

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
