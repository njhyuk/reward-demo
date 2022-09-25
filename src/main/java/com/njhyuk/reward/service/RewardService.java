package com.njhyuk.reward.service;

import com.njhyuk.reward.domain.Reward;
import com.njhyuk.reward.domain.RewardBenefit;

public interface RewardService {
    Reward getReword();
    
    RewardBenefit applyReword(Long userId);
}
