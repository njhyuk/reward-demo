package com.njhyuk.reward.service;

import com.njhyuk.reward.common.configuration.RewardConfiguration;
import com.njhyuk.reward.domain.Reward;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RewardServiceImpl implements RewardService {
    private final RewardConfiguration rewardConfiguration;

    @Override
    public Reward getReword() {
        return new Reward(rewardConfiguration.getId(),
            rewardConfiguration.getSubject(),
            rewardConfiguration.getDescription());
    }
}
