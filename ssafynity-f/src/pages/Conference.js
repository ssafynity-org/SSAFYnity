import React, { useState, useEffect, useRef, useCallback } from "react";
import axiosInstance from "../api/axiosInstance";
import { useNavigate } from "react-router-dom";
import SearchBar from "../components/SearchBar";
import "../styles/Conference.css";

function Conference() {
  const [menu, setMenu] = useState("전체 글");
  const [companyList, setCompanyList] = useState([]); 
  const [videoData, setVideoData] = useState([]); 
  const [loading, setLoading] = useState(false); 
  const [page, setPage] = useState(0); // ✅ 페이지 번호 추가
  const [hasMore, setHasMore] = useState(true); // ✅ 더 가져올 데이터가 있는지 확인

  const observer = useRef();

  const handleCompany = (companies) => {
    setCompanyList(companies);
    setPage(0); // ✅ 새로운 필터 적용 시 페이지 리셋
    setVideoData([]); // ✅ 기존 데이터 초기화
  };

  const handleMenu = (menu) => {
    setMenu(menu);
    if (menu === "기업 별") {
      setCompanyList([]);
    }
    setPage(0); // ✅ 필터 변경 시 페이지 리셋
    setVideoData([]);
  };

  const removeEmojis = (text) => {
    return text.replace(/[\p{Emoji_Presentation}\p{Extended_Pictographic}]/gu, "");
  };

  const truncateText = (text, maxLength) => {
    const cleanText = removeEmojis(text);
    return cleanText.length > maxLength ? cleanText.substring(0, maxLength) + "..." : cleanText;
  };

  // ✅ API 호출 함수 (페이지네이션 적용)
  const fetchVideoData = async (currentPage) => {
    if (loading || !hasMore) return;

    try {
      setLoading(true);
      const response = await axiosInstance.get("/api/video/videolist", {
        params: {
          tags: null,
          companies: companyList.length > 0 ? companyList : null,
          page: currentPage, // ✅ 현재 페이지 적용
          size: 24,
        },
      });

      setVideoData((prevVideos) => [...prevVideos, ...response.data]); // ✅ 기존 데이터 유지하며 추가
      setHasMore(response.data.length > 0); // ✅ 더 이상 데이터가 없으면 false
    } catch (error) {
      console.error("Error fetching video data:", error.response?.data);
    } finally {
      setLoading(false);
    }
  };

  // ✅ 페이지가 변경될 때 API 호출
  useEffect(() => {
    fetchVideoData(page);
  }, [page, companyList]);

  // ✅ 마지막 요소 감지 후 페이지 업데이트
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
      <SearchBar selectedMenu={handleMenu} selectedCompany={handleCompany} />
      <div className="conference-wrap">
        <div className="video-list">
          {videoData.map((video, index) => {
            if (index === videoData.length - 1) {
              return (
                <div ref={lastVideoElementRef} key={video.id} className="video-container">
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
                        <div className="video-description">
                          {/* <p>{truncateText(video.description, 100)}</p> */}
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              );
            } else {
              return (
                <div key={video.id} className="video-container">
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
                        <div className="video-description">
                          {/* <p>{truncateText(video.description, 100)}</p> */}
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              );
            }
          })}
        </div>

        {/* ✅ 로딩 중 표시 */}
        {loading && <p className="loading">Loading...</p>}
      </div>
    </div>
  );
}

export default Conference;
