package com.committers.snowflowerthon.committersserver.domain.commit.entity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommitRepository extends JpaRepository<Commit, Long> {
}
