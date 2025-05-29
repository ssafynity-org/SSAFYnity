import React, { useState, useEffect } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import axiosInstance from "../api/axiosInstance";
import ConferenceHeader from "../components/ConferenceHeader";
import "../styles/Board.css";

const Board = () => {
    const navigate = useNavigate();
    const location = useLocation();

    const [posts, setPosts] = useState([]);
    const [currentPage, setCurrentPage] = useState(1);
    const [firstId, setFirstId] = useState();
    const [lastId, setLastId] = useState();
    const [nextPage, setNextPage] = useState(1);
    const [isLastButtonClicked, setIsLastButtonClicked] = useState(false);

    // 페이지네이션 상태
    const [startPage, setStartPage] = useState(1);
    const [endPage, setEndPage] = useState(1);
    const [hasPrev, setHasPrev] = useState(false);
    const [hasNext, setHasNext] = useState(false);
    const [hasLast, setHasLast] = useState(false);

    const goToCreatePost = () => {
        navigate("/create-post");
    };

    useEffect(() => {
        const params = {
            currentPage: currentPage,
            firstId: firstId,
            lastId: lastId,
            nextPage: nextPage,
            lastButton: isLastButtonClicked,
        };

        console.log("이번 요청에 들어간 currentPage : " + params.currentPage);
        console.log("이번 요청에 들어간 firstId : " + params.firstId);
        console.log("이번 요청에 들어간 lastId : " + params.lastId);
        console.log("이번 요청에 들어간 nextPage : " + params.nextPage);
        console.log("이번 요청에 들어간 lastButton : " + params.lastButton);

        axiosInstance
            .get("/api/board", { params })
            .then((response) => {
                const data = response.data.data;

                setPosts(data.content);
                setCurrentPage(data.currentPage);
                setFirstId(data.firstId);
                setLastId(data.lastId);
                setStartPage(data.startPage);
                setEndPage(data.endPage);
                setHasNext(data.nextButton);
                setHasLast(data.lastButton);
                setIsLastButtonClicked(false);
            })
            .catch((error) => {
                console.error("게시글 불러오기 실패:", error);
            });
    }, [nextPage]);

    return (
        <div className="board-page">
            <ConferenceHeader />
            <div className="board-container">
                <button className="write-post-button" onClick={goToCreatePost}>
                    글쓰기
                </button>

                <table className="board-table">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>제목</th>
                            <th>작성자</th>
                            <th>작성일</th>
                            <th>조회수</th>
                            <th>좋아요</th>
                        </tr>
                    </thead>
                    <tbody>
                        {posts.map((post) => (
                            <tr key={post.boardId}>
                                <td>{post.boardId}</td>
                                <td className="title">{post.title}</td>
                                <td>{post.author}</td>
                                <td>{post.createdAt}</td>
                                <td>{post.views}</td>
                                <td>{post.likes}</td>
                            </tr>
                        ))}
                    </tbody>
                </table>

                {/* 페이지네이션 */}
                <div className="pagination-buttons">
                    {/* << 첫 페이지 */}
                    <button onClick={() => setNextPage(1)} disabled={currentPage === 1}>
                        &laquo;
                    </button>

                    {/* < 이전 그룹 */}
                    <button onClick={() => setNextPage(startPage - 10)}>
                        {/* console.log(startPage - 1); */}
                        &lt;
                    </button>

                    {/* 페이지 번호들 */}
                    {Array.from({ length: endPage - startPage + 1 }, (_, index) => {
                        const page = startPage + index;
                        return (
                            <button
                                key={page}
                                onClick={() => setNextPage(page)}
                                style={{
                                    fontWeight: page === currentPage ? "bold" : "normal",
                                }}
                            >
                                {page}
                            </button>
                        );
                    })}

                    {/* > 다음 그룹 */}
                    <button onClick={() => setNextPage(endPage + 1)} disabled={!hasNext}>
                        &gt;
                    </button>

                    <button onClick={() => {setIsLastButtonClicked(true); setNextPage(130000);}} disabled={!hasLast}>
                        &raquo;
                    </button>
                </div>
            </div>
        </div>
    );
};

export default Board;
