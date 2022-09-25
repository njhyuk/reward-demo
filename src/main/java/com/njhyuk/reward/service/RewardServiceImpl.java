package com.njhyuk.reward.service;

import com.njhyuk.reward.common.configuration.RewardConfiguration;
import com.njhyuk.reward.common.exception.ClosedRewardException;
import com.njhyuk.reward.common.exception.DuplicatedRewardException;
import com.njhyuk.reward.domain.RewardDetail;
import com.njhyuk.reward.domain.RewardCalculator;
import com.njhyuk.reward.domain.RewardHistory;
import com.njhyuk.reward.domain.RewardHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RewardServiceImpl implements RewardService {
    private final RewardConfiguration rewardConfiguration;
    private final RewardHistoryRepository rewardHistoryRepository;

    @Override
    public RewardDetail getRewordDetail() {
        return new RewardDetail(rewardConfiguration.getId(),
            rewardConfiguration.getSubject(),
            rewardConfiguration.getDescription());
    }

    @Override
    @Transactional
    public RewardHistory applyReword(LocalDateTime rewardDateTime, Long userId) {
        Integer rewardCount = rewardHistoryRepository.countByRewardedAtBetween(
            rewardDateTime.with(LocalTime.of(0, 0, 0)),
            rewardDateTime.with(LocalTime.of(23, 59, 59))
        );
        if (rewardCount >= 10) {
            throw new ClosedRewardException("선착순 보상이 마감되었습니다.");
        }

        RewardHistory lastRewardHistory = rewardHistoryRepository.findFirstByUserIdOrderByIdDesc(userId)
            .orElse(RewardHistory.EMPTY);

        if (compareDuplicatedReward(lastRewardHistory, rewardDateTime)) {
            throw new DuplicatedRewardException("당일 중복 응모는 불가능합니다.");
        }

        int consecutiveCount = getConsecutiveCount(rewardDateTime, lastRewardHistory);

        RewardHistory rewardHistory = RewardHistory.builder()
            .userId(userId)
            .consecutiveCount(consecutiveCount)
            .point(RewardCalculator.calculatePoint(consecutiveCount))
            .rewardedAt(rewardDateTime)
            .build();

        return rewardHistoryRepository.save(rewardHistory);
    }

    @Override
    public List<RewardHistory> getRewardHistories(LocalDate rewardDate) {
        return rewardHistoryRepository.findByRewardedAtBetweenOrderByRewardedAt(
            rewardDate.atTime(0, 0, 0),
            rewardDate.atTime(23, 59, 59)
        );
    }

    private int getConsecutiveCount(LocalDateTime rewardDateTime, RewardHistory lastRewardHistory) {
        int consecutiveCount = 1;
        if (isConsecutiveReward(lastRewardHistory, rewardDateTime)) {
            consecutiveCount = lastRewardHistory.getConsecutiveCount() + 1;
        }
        return consecutiveCount;
    }

    private boolean isConsecutiveReward(RewardHistory lastRewardHistory, LocalDateTime rewardDateTime) {
        return Period.between(
            lastRewardHistory.getRewardedAt().toLocalDate(),
            rewardDateTime.toLocalDate()
        ).getDays() == 1;
    }

    private boolean compareDuplicatedReward(RewardHistory lastRewardHistory, LocalDateTime rewardDateTime) {
        return lastRewardHistory.getRewardedAt().toLocalDate()
            .isEqual(rewardDateTime.toLocalDate());
    }
}
