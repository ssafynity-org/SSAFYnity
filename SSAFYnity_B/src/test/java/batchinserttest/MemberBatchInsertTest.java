package batchinserttest;

import com.ssafynity_b.SSAFYnityBApplication;
import com.ssafynity_b.domain.member.entity.Member;
import com.ssafynity_b.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@SpringBootTest(classes = SSAFYnityBApplication.class)
@Rollback(false)
public class MemberBatchInsertTest {

    @Autowired
    private MemberRepository memberRepository;

    private static final int MEMBER_COUNT = 1_000_000;
    private static final int BATCH_SIZE = 1000;

    private static final String[] CAMPUSES = {
            "서울캠퍼스", "대전캠퍼스", "부울경캠퍼스", "광주캠퍼스", "구미캠퍼스"
    };

    private static final Random random = new Random();

    @Test
    public void insertMembers() {
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
                memberRepository.saveAll(members);
                members.clear();
                System.out.println(i + "명의 멤버 삽입 완료");
            }
        }

        if (!members.isEmpty()) {
            memberRepository.saveAll(members);
        }

        System.out.println("멤버 생성 완료. 총 " + MEMBER_COUNT + "명");
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
}
