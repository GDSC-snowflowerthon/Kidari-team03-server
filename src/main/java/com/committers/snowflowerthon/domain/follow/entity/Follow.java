package com.committers.snowflowerthon.domain.follow.entity;

import jakarta.persistence.*;
import lombok.*;
import com.committers.snowflowerthon.domain.member.entity.Member;

@Getter
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "followId")
    private Long id; // 친구 추가 고유 번호

    // 단방향 매핑
    @ManyToOne
    @JoinColumn(name = "memberId")
    private Member member; // 멤버 고유 번호
    
    @Column(nullable = false)
    private Long buddyId; // 추가 당하는 사람
    
}
