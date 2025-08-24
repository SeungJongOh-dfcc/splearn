package sjspring.splearn.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MemberTest {
    Member member;
    PasswordEncoder passwordEncoder;

    @BeforeEach
    void setup() {
        this.passwordEncoder = new PasswordEncoder() {
            @Override
            public String encode(String password) {
                return password.toUpperCase();
            }

            @Override
            public boolean matches(String password, String passwordHash) {
                return encode(password).equals(passwordHash);
            }
        };

        member = Member.create(new MemberCreateRequest("sj@splearn.app", "SJ", "secret"), passwordEncoder);
    }

    @Test
    void createMember() throws Exception {
        // given
        // when

        // then
        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
    }

    @Test
    void activate() throws Exception {
        // given

        // when
        member.activate();
        // then
        assertThat(member.getStatus()).isEqualTo(MemberStatus.ACTIVE);
    }

    @Test
    void activateFail() throws Exception {
        // given
        // when
        member.activate();

        // then
        assertThatThrownBy(() -> {
            member.activate();
        }).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void deactivate() throws Exception {
        // given
        member.activate();
        // when
        member.deactivate();
        // then
        assertThat(member.getStatus()).isEqualTo(MemberStatus.DEACTIVATED);
    }

    @Test
    void deactivateFail() throws Exception {
        // given

        assertThatThrownBy(() -> member.deactivate()).isInstanceOf(IllegalStateException.class);

        member.activate();
        member.deactivate();

        assertThatThrownBy(member::deactivate).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void verifyPassword() throws Exception {
        // given
        assertThat(member.verifyPassword("secret", passwordEncoder)).isTrue();
        assertThat(member.verifyPassword("hello", passwordEncoder)).isFalse();
        // when

        // then
    }

    @Test
    void changeNickname() throws Exception {
        // given
        assertThat(member.getNickname()).isEqualTo("SJ");
        // when
        member.changeNickname("changedName");

        // then
        assertThat(member.getNickname()).isEqualTo("changedName");
    }

    @Test
    void changePassword() throws Exception {
        // given
        // when
        member.changePassword("verySecret", passwordEncoder);
        // then
        assertThat(member.verifyPassword("verySecret", passwordEncoder)).isTrue();
    }

    @Test
    void isActive() throws Exception {
        // given
        Assertions.assertThat(member.isActive()).isFalse();
        // when
        member.activate();

        // then
        Assertions.assertThat(member.isActive()).isTrue();

        member.deactivate();

        Assertions.assertThat(member.isActive()).isFalse();

    }

    @Test
    void invalidEmail() throws Exception {
        // given
        assertThatThrownBy(() ->
                Member.create(new MemberCreateRequest("invalid email", "Toby", "secret"), passwordEncoder)
        ).isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() ->
                Member.create(new MemberCreateRequest("", "Toby", "secret"), passwordEncoder)
        ).isInstanceOf(IllegalArgumentException.class);

        // when
        Member.create(new MemberCreateRequest("tmdwhd319@gmail.com", "SJ", "secret"), passwordEncoder);

        // then
    }

}