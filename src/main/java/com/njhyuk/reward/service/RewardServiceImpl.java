package com.njhyuk.reward.service;

import com.njhyuk.reward.common.configuration.RewardConfiguration;
import com.njhyuk.reward.common.exception.ClosedRewardException;
import com.njhyuk.reward.common.exception.DuplicatedRewardException;
import com.njhyuk.reward.domain.Reward;
import com.njhyuk.reward.domain.RewardBenefit;
import com.njhyuk.reward.domain.RewardBenefitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;

@Service
@RequiredArgsConstructor
public class RewardServiceImpl implements RewardService {
    private final RewardConfiguration rewardConfiguration;
    private final RewardBenefitRepository rewardBenefitRepository;

    @Override
    public Reward getReword() {
        return new Reward(rewardConfiguration.getId(),
            rewardConfiguration.getSubject(),
            rewardConfiguration.getDescription());
    }

    @Override
    @Transactional
    public RewardBenefit applyReword(LocalDateTime rewardDateTime, Long userId) {
        LocalDateTime startOfDay = rewardDateTime.with(LocalTime.of(0, 0, 0));
        LocalDateTime endOfDay = rewardDateTime.with(LocalTime.of(23, 59, 59));

        Integer rewardCount = rewardBenefitRepository.countByRewardedAtBetween(startOfDay, endOfDay);
        if (rewardCount >= 10) {
            throw new ClosedRewardException("선착순 보상이 마감되었습니다.");
        }

        RewardBenefit lastRewardBenefit = rewardBenefitRepository.findFirstByUserIdOrderByIdDesc(userId)
            .orElse(RewardBenefit.EMPTY);

        if (compareDuplicatedReward(lastRewardBenefit, rewardDateTime)) {
            throw new DuplicatedRewardException("당일 중복 응모는 불가능합니다.");
        }

        int consecutiveCount = getConsecutiveCount(rewardDateTime, lastRewardBenefit);

        RewardBenefit rewardBenefit = RewardBenefit.builder()
            .userId(userId)
            .consecutiveCount(consecutiveCount)
            .point(calculatePoint(consecutiveCount))
            .rewardedAt(rewardDateTime)
            .build();

        return rewardBenefitRepository.save(rewardBenefit);
    }

    private int getConsecutiveCount(LocalDateTime rewardDateTime, RewardBenefit lastRewardBenefit) {
        int consecutiveCount = 1;
        if (isConsecutiveReward(lastRewardBenefit, rewardDateTime)) {
            consecutiveCount = lastRewardBenefit.getConsecutiveCount() + 1;
        }
        return consecutiveCount;
    }

    private boolean isConsecutiveReward(RewardBenefit lastRewardBenefit, LocalDateTime rewardDateTime) {
        return Period.between(
            lastRewardBenefit.getRewardedAt().toLocalDate(),
            rewardDateTime.toLocalDate()
        ).getDays() == 1;
    }

    private boolean compareDuplicatedReward(RewardBenefit lastRewardBenefit, LocalDateTime rewardDateTime) {
        return lastRewardBenefit.getRewardedAt().toLocalDate()
            .isEqual(rewardDateTime.toLocalDate());
    }

    private int calculatePoint(Integer consecutiveCount) {
        int validCount = consecutiveCount % 10;

        return switch (validCount) {
            case 3 -> 300;
            case 5 -> 500;
            case 0 -> 1000;
            default -> 100;
        };
    }
}
