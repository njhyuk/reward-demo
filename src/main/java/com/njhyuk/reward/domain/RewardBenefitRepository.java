package com.njhyuk.reward.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface RewardBenefitRepository extends JpaRepository<RewardBenefit, Long> {
    Integer countByCreatedAtBetween(LocalDateTime startCreatedAt, LocalDateTime endCreatedAt);
}
