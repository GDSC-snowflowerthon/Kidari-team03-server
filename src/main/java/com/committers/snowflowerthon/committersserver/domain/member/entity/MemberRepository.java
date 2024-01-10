package com.committers.snowflowerthon.committersserver.domain.member.entity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findById(Long id);
    Optional<Member> findByNickname(String nickname);
    List<Member> findAllByNickname(String nickname);
}
