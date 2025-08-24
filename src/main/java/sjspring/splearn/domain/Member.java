package sjspring.splearn.domain;

import lombok.Getter;
import lombok.ToString;

import java.util.Objects;
import java.util.regex.Pattern;

import static java.util.Objects.requireNonNull;
import static org.springframework.util.Assert.state;

@Getter
@ToString
public class Member {
    private Email email;

    /** 닉네임 **/
    private String nickname;

    private String passwordHash;

    private MemberStatus status;

    private Member() {}

    /**
     * 정적 팩토리 메서드
     **/
    public static Member create(MemberCreateRequest createRequest, PasswordEncoder passwordEncoder) {
        Member member = new Member();


        member.email = new Email(createRequest.email());
        member.nickname = requireNonNull(createRequest.nickname());
        member.passwordHash = requireNonNull(passwordEncoder.encode(createRequest.password()));
        // 맴버 상태는 처음에 PENDING으로 되어있어야 하므로 파라미터로 넘기는게 아닌 생성자에서 직접 생성
        member.status = MemberStatus.PENDING;

        return member;
    }

    public void activate() {
        state(status == MemberStatus.PENDING, "PENDING 상태가 아닙니다.");

        this.status = MemberStatus.ACTIVE;
    }

    public void deactivate() {
        state(status == MemberStatus.ACTIVE, "ACTIVE 상태가 아닙니다.");

        this.status = MemberStatus.DEACTIVATED;
    }

    public boolean verifyPassword(String password, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(password, this.passwordHash);
    }

    public void changeNickname(String nickname) {
        this.nickname = nickname;
    }

    public void changePassword(String password, PasswordEncoder passwordEncoder) {
        this.passwordHash = passwordEncoder.encode(requireNonNull(password));
    }

    public boolean isActive() {
        return this.status == MemberStatus.ACTIVE;
    }
}
