package com.committers.snowflowerthon.committersserver.domain.member.entity;

import com.committers.snowflowerthon.committersserver.domain.univ.entity.Univ;
import jakarta.persistence.*;
import lombok.*;
import com.committers.snowflowerthon.committersserver.domain.item.entity.Item;

import static java.lang.Math.max;

@Getter
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "memberId")
    private Long id; // 멤버 고유 번호

    @Column(nullable = false)
    private String nickname; // 깃허브 아이디

    @Builder.Default
    @Column(nullable = false)
    private Long snowflake = 0L; // 눈송이 수

    @Builder.Default
    @Column(nullable = false)
    private Long snowmanHeight = 1L; // 눈사람 키

    @Builder.Default
    @Column(nullable = false)
    private Long attacking = 0L; // 공격한 횟수

    @Builder.Default
    @Column(nullable = false)
    private Long damage = 0L; // 공격 받은 횟수

    @Builder.Default
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role = Role.getDefaultRole(); // 유저 권한

    @Builder.Default
    @Column(nullable = false)
    private Boolean newAlarm = false; // 새로운 알림이 있는지

    // 단방향 매핑
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "univId")
    private Univ univ; // 대학 고유 번호

    // 단방향 매핑
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "itemId")
    private Item item; // 아이템 고유 번호

    public boolean useSnowflake(){
        if (this.snowflake <= 0) { // 0 이하이면 사용 불가
            return false;
        }
        this.snowflake--;
        return true;
    }

    public void updateSnowflake(Long totalCommits) {
        this.snowflake = totalCommits;
    }

    public void updateSnowmanHeight(Long newHeight) {
        this.snowmanHeight = max(newHeight, 1L);
    }

    public void alarmUnchecked(){
        this.newAlarm = true;
    }
    public void alarmChecked(){
        this.newAlarm = false;
    }

    public void updateUniv(Univ univ){
        this.univ = univ;
    }
}
