package com.committers.snowflowerthon.committersserver.domain.attack.entity;

import com.committers.snowflowerthon.committersserver.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttackRepository extends JpaRepository<Attack, Long> {
    List<Attack> findAllByMember(Member member);
}
