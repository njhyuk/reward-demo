package com.njhyuk.reward.service;

import com.njhyuk.reward.common.configuration.RewardConfiguration;
import com.njhyuk.reward.common.exception.ClosedRewardException;
import com.njhyuk.reward.domain.Reward;
import com.njhyuk.reward.domain.RewardBenefit;
import com.njhyuk.reward.domain.RewardBenefitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;

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
        Integer rewardCount = getRewardCount(rewardDateTime);

        if (rewardCount >= 10) {
            throw new ClosedRewardException("선착순 보상이 마감되었습니다.");
        }

        RewardBenefit rewardBenefit = RewardBenefit.builder()
            .userId(userId)
            .point(100)
            .createdAt(LocalDateTime.now())
            .consecutiveCount(1)
            .build();

        return rewardBenefitRepository.save(rewardBenefit);
    }

    private Integer getRewardCount(LocalDateTime rewardDateTime) {
        return rewardBenefitRepository.countByCreatedAtBetween(
            rewardDateTime.with(LocalTime.of(0, 0, 0)),
            rewardDateTime.with(LocalTime.of(23, 59, 59))
        );
    }
}
