package com.committers.snowflowerthon.domain.commit.entity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommitRepository extends JpaRepository<Commit, Long> {
}
