import React, { useState, useEffect, useRef, useCallback } from "react";
import axiosInstance from "../api/axiosInstance";
import ConferenceHeader from "../components/ConferenceHeader";
import SearchBar from "../components/SearchBar";
import "../styles/Conference.css";

function Conference() {
  const [menu, setMenu] = useState("전체 글");
  const [companyList, setCompanyList] = useState([]);
  const [videoData, setVideoData] = useState([]);
  const [loading, setLoading] = useState(false);
  const [page, setPage] = useState(0);
  const [hasMore, setHasMore] = useState(true);
  const [selectedCompany, setSelectedCompany] = useState(null); 

  const observer = useRef();

  const handleCompany = (newCompany) => {
    setCompanyList(prevList => 
      prevList.includes(newCompany) 
        ? prevList.filter(item => item !== newCompany)  // 존재하면 삭제
        : [...prevList, newCompany]  // 없으면 추가
    );
    setPage(0);
    setVideoData([]);
  };

  const handleMenu = (newMenu) => {
    if (menu === newMenu) return;
    console.log("show");
    setMenu(newMenu);
    setPage(0);
    setVideoData([]);
    setHasMore(true);
    setCompanyList(null);
    fetchVideoData();
  };

  const fetchVideoData = async (currentPage) => {
    if (loading || !hasMore) return;

    try {
      setLoading(true);

      const formattedCompanies = companyList.length > 0 
        ? companyList.join(",") 
        : null;

      const response = await axiosInstance.get("/api/video/videolist", {
        params: {
          tags: selectedCompany ? [selectedCompany] : null,  // ✅ 선택된 회사에 따라 태그 설정
          companies: formattedCompanies,  // ✅ Swagger에 맞게 변환된 companies
          page: currentPage,
          size: 15,
        },
      });

      setVideoData((prevVideos) => [...prevVideos, ...response.data]);
      setHasMore(response.data.length > 0);
    } catch (error) {
      console.error("Error fetching video data:", error.response?.data);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchVideoData(page);
  }, [page, companyList, selectedCompany]);  // ✅ selectedCompany 의존성 추가

  const lastVideoElementRef = useCallback(
    (node) => {
      if (loading) return;
      if (observer.current) observer.current.disconnect();

      observer.current = new IntersectionObserver((entries) => {
        if (entries[0].isIntersecting && hasMore) {
          setPage((prevPage) => prevPage + 1);
        }
      });

      if (node) observer.current.observe(node);
    },
    [loading, hasMore]
  );

  return (
    <div className="conference-page">
      <ConferenceHeader />
      <div className="conference-main">
        <SearchBar selectedMenu={handleMenu} selectedCompany={handleCompany} />
        <div className="conference-wrap">
          <div className="video-list">
            {videoData.map((video, index) => (
              <div
                ref={index === videoData.length - 1 ? lastVideoElementRef : null}
                key={video.id}
                className="video-container"
              >
                <div className="video-allcontent">
                  <div className="video-thumbnail">
                    <img src={video.thumbnail} alt="video thumbnail" />
                  </div>
                  <div className="video-subcontent">
                    <div className="video-channelImage">
                      <img src={video.channelImage} alt="channel" />
                    </div>
                    <div className="video-info">
                      <div className="video-titleAndPost">
                        <div className="video-title">
                          <p>{video.title}</p>
                        </div>
                        <div className="video-channelName">
                          <p>{video.channelName}</p>
                        </div>
                        <div className="video-post">
                          <p>조회수 {video.viewCount}</p>
                          <p>•</p>
                          <p>{video.publishedAt}</p>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            ))}
          </div>
          {loading && <p className="loading">Loading...</p>}
        </div>
      </div>
    </div>
  );
}

export default Conference;
