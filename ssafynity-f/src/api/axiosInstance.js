import axios from "axios";

// Axios 인스턴스 생성
const axiosInstance = axios.create({
  baseURL: process.env.REACT_APP_API_URL, // 환경 변수에서 API URL 로드
});

// 요청 인터셉터 설정
axiosInstance.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem("jwtToken"); // localStorage에서 JWT 가져오기
    if (token) {
      config.headers["Authorization"] = `Bearer ${token}`; // Authorization 헤더에 추가
    }
    return config;
  },
  (error) => {
    return Promise.reject(error); // 오류 처리
  }
);

export default axiosInstance;