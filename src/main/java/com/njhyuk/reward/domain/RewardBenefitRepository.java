package com.njhyuk.reward.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RewardBenefitRepository extends JpaRepository<RewardBenefit, Long> {
}
