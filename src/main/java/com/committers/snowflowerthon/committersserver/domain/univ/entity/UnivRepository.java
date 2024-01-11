package com.committers.snowflowerthon.committersserver.domain.univ.entity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UnivRepository extends JpaRepository<Univ, Long> {
    Optional<Univ> findById(Long id);
    Optional<Univ> findByUnivName(String name);
    List<Univ> findAllByUnivName(Univ univ);
}
