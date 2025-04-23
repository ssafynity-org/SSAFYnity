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

@SpringBootTest(classes = SSAFYnityBApplication.class)
@Rollback(false)
public class BoardBatchInsertTest {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private MemberRepository memberRepository;

    private static final int BOARD_COUNT = 15_000_000;
    private static final int BATCH_SIZE = 1000;

    private static final String[] CATEGORIES = {"개발", "잡담", "취업", "이직"};
    private static final String[] RANDOM_KOREAN_WORDS = {
            "안녕하세요", "테스트", "샘플", "데이터", "자바", "스프링", "부트", "랜덤", "개발자", "프로그래밍",
            "코드", "실행", "삽입", "성공", "실패", "기록", "로그", "정보", "서버", "클라이언트", "통신", "응답",
            "요청", "사용자", "관리자", "페이지", "조회", "등록", "수정", "삭제", "목록", "상세", "입력", "출력"
            // ... 생략 가능
    };

    private static final Random random = new Random();

    @Test
    public void insertBoards() {
        List<Board> boards = new ArrayList<>();

        for (int i = 3000001; i <= BOARD_COUNT; i++) {
        Member member = memberRepository.getReferenceById((long)random.nextInt(1_000_000) + 1);

            Board board = Board.builder()
                    .title(generateRandomKoreanText(3, 10))
                    .content(generateRandomKoreanText(30, 100))
                    .category(CATEGORIES[random.nextInt(CATEGORIES.length)])
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
