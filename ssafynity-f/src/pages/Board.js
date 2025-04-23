import React, { useState, useEffect } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import axiosInstance from "../api/axiosInstance";
import ConferenceHeader from "../components/ConferenceHeader";
import "../styles/Board.css";

const Board = () => {
    const navigate = useNavigate();
    const location = useLocation();

    const [posts, setPosts] = useState([]);
    const [pageNumber, setPageNumber] = useState(1);
    const [lastId, setLastId] = useState(); // 필요한 경우 유지
    const [moveNumber, setTotalPages] = useState(1); // 백엔드 필요 시 유지

    // 페이지네이션 상태
    const [startPage, setStartPage] = useState(1);
    const [endPage, setEndPage] = useState(1);
    const [hasNext, setHasNext] = useState(false);
    const [hasLast, setHasLast] = useState(false);

    const goToCreatePost = () => {
        navigate("/create-post");
    };

    useEffect(() => {
        const params = {
            pageNumber: pageNumber,
            lastId: lastId,
            moveNumber: moveNumber,
        };

        axiosInstance
            .get("/api/board", { params })
            .then((response) => {
                const data = response.data.data;

                setPosts(data.content);
                setStartPage(data.startPage);
                setEndPage(data.endPage);
                setHasNext(data.nextButton);
                setHasLast(data.lastButton);
            })
            .catch((error) => {
                console.error("게시글 불러오기 실패:", error);
            });
    }, [pageNumber]);

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
                    <button onClick={() => setPageNumber(1)} disabled={pageNumber === 1}>
                        &laquo;
                    </button>

                    {/* < 이전 그룹 */}
                    <button onClick={() => setPageNumber(Math.max(startPage - 1, 1))} disabled={startPage === 1}>
                        &lt;
                    </button>

                    {/* 페이지 번호들 */}
                    {Array.from({ length: endPage - startPage + 1 }, (_, index) => {
                        const page = startPage + index;
                        return (
                            <button
                                key={page}
                                onClick={() => setPageNumber(page)}
                                style={{
                                    fontWeight: page === pageNumber ? "bold" : "normal",
                                }}
                            >
                                {page}
                            </button>
                        );
                    })}

                    {/* > 다음 그룹 */}
                    <button onClick={() => setPageNumber(endPage + 1)} disabled={!hasNext}>
                        &gt;
                    </button>

                    {/* >> 마지막 페이지 (99999는 임시 대체) */}
                    <button onClick={() => setPageNumber(130000)} disabled={!hasLast}>
                        &raquo;
                    </button>
                </div>
            </div>
        </div>
    );
};

export default Board;
