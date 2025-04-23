package batchinserttest;

import com.ssafynity_b.SSAFYnityBApplication;
import com.ssafynity_b.domain.board.entity.Board;
import com.ssafynity_b.domain.board.repository.BoardRepository;
import com.ssafynity_b.domain.member.entity.Member;
import com.ssafynity_b.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@SpringBootTest(classes = SSAFYnityBApplication.class)
@Rollback(false)
public class BatchInsertTest {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private MemberRepository memberRepository;

    private static final int MEMBER_COUNT = 1_000_000;
    private static final int BOARD_COUNT = 13_000_000;
    private static final int BATCH_SIZE = 1000;

    private static final String[] CAMPUSES = {
            "서울캠퍼스", "대전캠퍼스", "부울경캠퍼스", "광주캠퍼스", "구미캠퍼스"
    };

    private static final String[] CATEGORIES = {
            "개발", "잡담", "취업", "이직"
    };

    private static final String[] RANDOM_KOREAN_WORDS = {
            "안녕하세요", "테스트", "샘플", "데이터", "자바", "스프링", "부트", "랜덤", "개발자", "프로그래밍",
            "코드", "실행", "삽입", "성공", "실패", "기록", "로그", "정보", "서버", "클라이언트", "통신", "응답",
            "요청", "사용자", "관리자", "페이지", "조회", "등록", "수정", "삭제", "목록", "상세", "입력", "출력",
            "연결", "설정", "환경", "에러", "예외", "디버깅", "컴파일", "배포", "빌드", "테스트케이스", "자동화",
            "속도", "최적화", "성능", "보안", "암호화", "인증", "인가", "토큰", "세션", "쿠키", "프론트엔드", "백엔드",
            "인터페이스", "패키지", "클래스", "메서드", "변수", "상수", "반복문", "조건문", "함수", "모듈", "라이브러리",
            "프레임워크", "구조", "설계", "패턴", "디자인", "객체", "인스턴스", "추상화", "캡슐화", "상속", "다형성",
            "리스트", "배열", "맵", "집합", "정렬", "탐색", "트리", "그래프", "큐", "스택", "데이터베이스", "쿼리",
            "인덱스", "조인", "트랜잭션", "커밋", "롤백", "JDBC", "MyBatis", "JPA", "ORM", "엔티티", "리포지토리",
            "컨트롤러", "서비스", "구현", "인터셉터", "필터", "로그인", "회원가입", "비밀번호", "이메일", "닉네임",
            "댓글", "좋아요", "조회수", "게시판", "공지사항", "자유게시판", "질문", "답변", "프로필", "업로드", "다운로드",
            "이미지", "파일", "첨부파일", "관리", "운영", "배너", "슬라이드", "레이아웃", "테마", "색상", "폰트"
    };

    private static final Random random = new Random();

    @Test
    public void runBatchInsert() {
        List<Long> memberIds = createMembers();
        createBoards(memberIds);
    }

    private List<Long> createMembers() {
        List<Long> memberIds = new ArrayList<>();
        List<Member> members = new ArrayList<>();

        for (int i = 1; i <= MEMBER_COUNT; i++) {
            Member member = Member.builder()
                    .email("user" + i + "@example.com")
                    .password(UUID.randomUUID().toString())
                    .name(generateRandomKoreanName())
                    .cohort(random.nextInt(14) + 1)
                    .campus(CAMPUSES[random.nextInt(CAMPUSES.length)])
                    .jobSearch(random.nextBoolean())
                    .company("회사" + random.nextInt(100))
                    .profileImage(random.nextBoolean())
                    .companyBlind(random.nextBoolean())
                    .role("USER")
                    .build();

            members.add(member);

            if (i % BATCH_SIZE == 0) {
                List<Member> saved = memberRepository.saveAll(members);
                saved.forEach(m -> memberIds.add(m.getId()));
                members.clear();
                System.out.println(i + "명의 멤버 삽입 완료");
            }
        }

        if (!members.isEmpty()) {
            List<Member> saved = memberRepository.saveAll(members);
            saved.forEach(m -> memberIds.add(m.getId()));
        }

        System.out.println("멤버 생성 완료. 총 " + memberIds.size() + "명");
        return memberIds;
    }

    private void createBoards(List<Long> memberIds) {
        List<Board> boards = new ArrayList<>();

        for (int i = 1; i <= BOARD_COUNT; i++) {
            Long randomMemberId = memberIds.get(random.nextInt(memberIds.size()));
            Member member = memberRepository.getReferenceById(randomMemberId);

            Board board = Board.builder()
                    .title(generateRandomKoreanText(3, 10))
                    .content(generateRandomKoreanText(30, 100))
                    .category(CATEGORIES[random.nextInt(CATEGORIES.length)]) // ✅ 랜덤 카테고리 지정
                    .createdAt(generateRandomDate())
                    .views(random.nextInt(1000))
                    .likes(random.nextInt(500))
                    .build();

            board.updateMember(member);
            boards.add(board);

            if (i % BATCH_SIZE == 0) {
                boardRepository.saveAll(boards);
                boards.clear();
                System.out.println(i + "개의 게시글 삽입 완료");
            }
        }

        if (!boards.isEmpty()) {
            boardRepository.saveAll(boards);
        }

        System.out.println("게시글 생성 완료. 총 " + BOARD_COUNT + "개");
    }

    private String generateRandomKoreanName() {
        String[] firstSyllables = {"김", "이", "박", "최", "정", "강", "조", "윤", "장", "임", "오", "한", "신", "서", "권", "황"};
        String[] middleAndLastSyllables = {
                "가", "강", "건", "경", "고", "관", "광", "구", "권", "규", "균", "기", "길", "나", "남", "노", "도", "동",
                "라", "류", "문", "민", "범", "병", "보", "상", "서", "석", "선", "성", "소", "수", "순", "승", "시", "신",
                "아", "안", "양", "여", "연", "영", "예", "오", "용", "우", "원", "유", "윤", "은", "의", "이", "인", "재",
                "전", "정", "제", "조", "종", "주", "준", "중", "지", "진", "찬", "창", "채", "철", "천", "초", "춘", "태",
                "하", "한", "해", "혁", "현", "형", "호", "홍", "화", "환", "훈", "휘", "희"
        };
        return firstSyllables[random.nextInt(firstSyllables.length)] +
                middleAndLastSyllables[random.nextInt(middleAndLastSyllables.length)] +
                middleAndLastSyllables[random.nextInt(middleAndLastSyllables.length)];
    }

    private String generateRandomKoreanText(int minWords, int maxWords) {
        int wordCount = random.nextInt(maxWords - minWords + 1) + minWords;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < wordCount; i++) {
            sb.append(RANDOM_KOREAN_WORDS[random.nextInt(RANDOM_KOREAN_WORDS.length)]);
            if (i < wordCount - 1) sb.append(" ");
        }
        return sb.toString();
    }

    private LocalDateTime generateRandomDate() {
        int year = random.nextInt(2025 - 2020 + 1) + 2020;
        int month = random.nextInt(12) + 1;
        int day = random.nextInt(28) + 1;
        int hour = random.nextInt(24);
        int minute = random.nextInt(60);
        int second = random.nextInt(60);
        return LocalDateTime.of(year, Month.of(month), day, hour, minute, second);
    }
}
