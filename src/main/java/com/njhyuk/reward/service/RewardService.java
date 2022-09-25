package com.njhyuk.reward.service;

import com.njhyuk.reward.domain.Reward;
import com.njhyuk.reward.domain.RewardBenefit;

import java.time.LocalDateTime;

public interface RewardService {
    Reward getReword();
    
    RewardBenefit applyReword(LocalDateTime rewardDateTime, Long userId);
}
