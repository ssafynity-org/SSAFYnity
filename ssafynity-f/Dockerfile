# 1단계: 빌드 단계
FROM node:16 AS builder

# 작업 디렉토리 설정
WORKDIR /app

# package.json 및 package-lock.json 복사
COPY package*.json ./

# 의존성 설치
RUN npm install

# 프로젝트 파일 복사
COPY . .

# React 빌드
RUN npm run build

# 2단계: Nginx 서버
FROM nginx:alpine

# Nginx 설정 파일 복사 (선택 사항)
# COPY nginx.conf /etc/nginx/conf.d/default.conf

# React 빌드 결과를 Nginx 정적 파일 경로로 복사
COPY --from=builder /app/build /usr/share/nginx/html

# Nginx 기본 포트
EXPOSE 80

# Nginx 실행
CMD ["nginx", "-g", "daemon off;"]
