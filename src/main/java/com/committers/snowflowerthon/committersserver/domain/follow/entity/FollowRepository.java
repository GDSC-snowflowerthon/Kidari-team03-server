package com.committers.snowflowerthon.committersserver.domain.follow.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import com.committers.snowflowerthon.committersserver.domain.member.entity.Member;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    List<Follow> findAllByMember(Member member);
    Optional<Follow> findByMemberAndBuddyId(Member member, Long id);
}
