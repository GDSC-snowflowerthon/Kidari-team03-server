package com.committers.snowflowerthon.domain.follow.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import com.committers.snowflowerthon.domain.member.entity.Member;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    List<Follow> findAllByMember(Member member);
    Optional<Follow> findByBuddyId(Long id);
}
