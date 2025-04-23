import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import axiosInstance from "../api/axiosInstance";

const CreatePost = () => {
    const navigate = useNavigate();
    const [title, setTitle] = useState("");
    const [content, setContent] = useState("");

    const handleSubmit = async (e) => {
        e.preventDefault();
    
        try {
            const response = await axiosInstance.post("/api/board", { title, content });
            
            if (response.status !== 200) {
                throw new Error("게시글 저장 실패");
            }
    
            console.log("새 게시글 저장 완료");
            navigate("/community/board"); // 저장 후 게시판으로 이동
        } catch (error) {
            console.error("에러 발생:", error);
        }
    };

    
    return (
        <div>
            <h2>게시글 작성</h2>
            <form onSubmit={handleSubmit}>
                <div>
                    <label>제목:</label>
                    <input 
                        type="text" 
                        value={title} 
                        onChange={(e) => setTitle(e.target.value)} 
                        required 
                    />
                </div>
                <div>
                    <label>내용:</label>
                    <textarea 
                        value={content} 
                        onChange={(e) => setContent(e.target.value)} 
                        required 
                    />
                </div>
                <button type="submit">게시글 작성</button>
            </form>
        </div>
    );
};

export default CreatePost;