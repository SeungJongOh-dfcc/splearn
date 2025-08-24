package sjspring.splearn.domain;

/**
 * Record: 불변 Object 만드는데 탁월! JAVA 14 부터
 * parameter -> record component 라고 부름
 * field가 final 처럼 동작함. 바꿀 수 있는 수단을 제공 X
 * Getter 제공. 다만, java Bean 처럼 getXXX 이렇게 아닌 Record component 그 자체 이름으로 제공
 */
public record MemberCreateRequest(String email, String nickname, String password) {
}
