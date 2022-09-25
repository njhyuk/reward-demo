package com.njhyuk.reward.service;

import com.njhyuk.reward.common.configuration.RewardConfiguration;
import com.njhyuk.reward.domain.Reward;
import com.njhyuk.reward.domain.RewardBenefit;
import com.njhyuk.reward.domain.RewardBenefitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    public RewardBenefit applyReword(Long userId) {
        RewardBenefit rewardBenefit = RewardBenefit.builder()
            .userId(userId)
            .point(100)
            .createdAt(LocalDateTime.now())
            .consecutiveCount(1)
            .build();

        return rewardBenefitRepository.save(rewardBenefit);
    }
}
