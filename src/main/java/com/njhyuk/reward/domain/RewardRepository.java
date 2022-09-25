package com.njhyuk.reward.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import javax.persistence.LockModeType;
import java.time.LocalDate;
import java.util.Optional;

public interface RewardRepository extends JpaRepository<Reward, LocalDate> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Reward> findByRewardDate(LocalDate rewardDate);
}
