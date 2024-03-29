package com.committers.snowflowerthon.committersserver.domain.commit.entity;

import com.committers.snowflowerthon.committersserver.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Commit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "commitId")
    private Long id; // 커밋 고유 번호

    @Column(nullable = false)
    private Long count; // 커밋 수

    // 단방향 매핑
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "memberId")
    private Member member;

    public void updateCount(Long totalCommits) {
        this.count = totalCommits;
    }
}

