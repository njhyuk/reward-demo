package com.njhyuk.reward.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface RewardHistoryRepository extends JpaRepository<RewardHistory, Long> {
    Integer countByRewardedAtBetween(LocalDateTime startRewardedAt, LocalDateTime endRewardedAt);

    Optional<RewardHistory> findFirstByUserIdOrderByIdDesc(Long userId);

    List<RewardHistory> findByRewardedAtBetween(LocalDateTime startRewardedAt, LocalDateTime endRewardedAt);
}
