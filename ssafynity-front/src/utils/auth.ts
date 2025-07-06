// JWT 토큰 관리 유틸리티

const TOKEN_KEY = "ssafynity_token";
const REFRESH_TOKEN_KEY = "ssafynity_refresh_token";

// 토큰 저장 (localStorage 사용)
export const saveToken = (token: string): void => {
    try {
        localStorage.setItem(TOKEN_KEY, token);
    } catch (error) {
        console.error("토큰 저장 실패:", error);
    }
};

// 리프레시 토큰 저장
export const saveRefreshToken = (refreshToken: string): void => {
    try {
        localStorage.setItem(REFRESH_TOKEN_KEY, refreshToken);
    } catch (error) {
        console.error("리프레시 토큰 저장 실패:", error);
    }
};

// 토큰 가져오기
export const getToken = (): string | null => {
    try {
        return localStorage.getItem(TOKEN_KEY);
    } catch (error) {
        console.error("토큰 가져오기 실패:", error);
        return null;
    }
};

// 리프레시 토큰 가져오기
export const getRefreshToken = (): string | null => {
    try {
        return localStorage.getItem(REFRESH_TOKEN_KEY);
    } catch (error) {
        console.error("리프레시 토큰 가져오기 실패:", error);
        return null;
    }
};

// 토큰 삭제 (로그아웃)
export const removeToken = (): void => {
    try {
        localStorage.removeItem(TOKEN_KEY);
        localStorage.removeItem(REFRESH_TOKEN_KEY);
    } catch (error) {
        console.error("토큰 삭제 실패:", error);
    }
};

// 토큰 유효성 검사 (기본적인 만료 시간 체크)
export const isTokenValid = (): boolean => {
    const token = getToken();
    if (!token) return false;

    try {
        // JWT 토큰의 페이로드 부분을 디코드
        const payload = JSON.parse(atob(token.split(".")[1]));
        const currentTime = Date.now() / 1000;

        // 토큰이 만료되었는지 확인
        return payload.exp > currentTime;
    } catch (error) {
        console.error("토큰 유효성 검사 실패:", error);
        return false;
    }
};

// API 요청에 토큰을 자동으로 포함하는 헤더 생성
export const getAuthHeaders = (): HeadersInit => {
    const token = getToken();
    return {
        "Content-Type": "application/json",
        ...(token && { Authorization: `Bearer ${token}` }),
    };
};
