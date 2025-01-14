import { createSlice } from '@reduxjs/toolkit';

export const userSlice = createSlice({
  name: 'user',
  initialState: {
    userInfo: null,
    isAuthenticated: false, // 로그인 상태를 추적
    loading: false,         // 로딩 상태를 추적
  },
  reducers: {
    login: (state, action) => {
      state.userInfo = {
        memberId: action.payload.memberId,
        name: action.payload.name,
        company: action.payload.company,
        profileImage: action.payload.profileImage
      };
      state.isAuthenticated = true; // 로그인이 성공하면 인증 상태를 참(true)으로 설정
      state.loading = false;       // 로딩 완료
    },
    logout: (state) => {
      state.userInfo = null;
      state.isAuthenticated = false; // 로그아웃하면 인증 상태를 거짓(false)으로 설정
      state.loading = false;        // 로딩 완료
    },
    setLoading: (state, action) => {
      state.loading = action.payload; // 로딩 상태 동적 관리
    }
  },
});

export const { login, logout, setLoading } = userSlice.actions;

export default userSlice.reducer;
