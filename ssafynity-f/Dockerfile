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
