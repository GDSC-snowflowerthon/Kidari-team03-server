package com.committers.snowflowerthon.domain.member.entity;

import jakarta.persistence.*;
import lombok.*;
import com.committers.snowflowerthon.domain.item.entity.Item;

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

    @Column(nullable = false)
    private Long snowmanHeight; // 눈사람 키

    @Column(nullable = false)
    private Long snowflake; // 눈송이 수

    // 단방향 매핑
    @OneToOne(mappedBy = "Item", fetch = FetchType.LAZY)
    @JoinColumn(name = "itemId")
    private Item item; // 아이템 고유 번호
}
