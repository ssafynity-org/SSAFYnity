const BASE_URL = process.env.NEXT_PUBLIC_API_URL!;

export async function apiFetch<T>(path: string, options: RequestInit = {}) {
    const res = await fetch(`${BASE_URL}${path}`, {
        headers: { "Content-Type": "application/json" },
        ...options,
    });
    if (!res.ok) throw new Error(`${res.status} ${res.statusText}`);
    return res.json() as Promise<T>;
}

// CREATE - 데이터 생성
export async function apiCreate<T>(path: string, data: any): Promise<T> {
    return apiFetch<T>(path, {
        method: "POST",
        body: JSON.stringify(data),
    });
}
// 사용 예시:
// const newUser = await apiCreate<User>('/users', { name: '홍길동', email: 'hong@example.com' });
// const newPost = await apiCreate<Post>('/posts', { title: '제목', content: '내용' });

// READ - 데이터 조회
export async function apiRead<T>(path: string): Promise<T> {
    return apiFetch<T>(path, {
        method: "GET",
    });
}
// 사용 예시:
// const user = await apiRead<User>('/users/1');
// const users = await apiRead<User[]>('/users');
// const post = await apiRead<Post>('/posts/123');

// UPDATE - 데이터 수정
export async function apiUpdate<T>(path: string, data: any): Promise<T> {
    return apiFetch<T>(path, {
        method: "PUT",
        body: JSON.stringify(data),
    });
}
// 사용 예시:
// const updatedUser = await apiUpdate<User>('/users/1', { name: '김철수', email: 'kim@example.com' });
// const updatedPost = await apiUpdate<Post>('/posts/123', { title: '새 제목', content: '새 내용' });

// DELETE - 데이터 삭제
export async function apiDelete<T>(path: string): Promise<T> {
    return apiFetch<T>(path, {
        method: "DELETE",
    });
}
// 사용 예시:
// await apiDelete('/users/1');
// await apiDelete('/posts/123');

// PATCH - 부분 데이터 수정
export async function apiPatch<T>(path: string, data: any): Promise<T> {
    return apiFetch<T>(path, {
        method: "PATCH",
        body: JSON.stringify(data),
    });
}
// 사용 예시:
// const patchedUser = await apiPatch<User>('/users/1', { email: 'new@example.com' });
// const patchedPost = await apiPatch<Post>('/posts/123', { title: '수정된 제목' });
