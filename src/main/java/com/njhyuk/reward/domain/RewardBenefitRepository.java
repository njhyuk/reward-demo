package com.njhyuk.reward.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface RewardBenefitRepository extends JpaRepository<RewardBenefit, Long> {
    Integer countByRewardedAtBetween(LocalDateTime startCreatedAt, LocalDateTime endCreatedAt);

    Optional<RewardBenefit> findFirstByUserIdOrderByIdDesc(Long userId);
}
