package com.committers.snowflowerthon.domain.attack.entity;

import com.committers.snowflowerthon.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Attack {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attackId")
    private Long id; // 공격 고유 번호

    @Column(nullable = false)
    private Date time; // 공격 시간

    @Column(nullable = false)
    private String attacker; // 공격자 깃허브 아이디

    @Column(nullable = false)
    private Boolean isChecked; // 단일 공격값 조회 여부

    // 단방향 매핑
    @ManyToOne
    @JoinColumn(name = "memberId")
    private Member member; // 공격 받은 사람 고유 번호
}
