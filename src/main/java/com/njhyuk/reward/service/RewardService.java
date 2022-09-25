package com.njhyuk.reward.service;

import com.njhyuk.reward.domain.Reward;
import com.njhyuk.reward.domain.RewardHistory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface RewardService {
    Reward getReword();
    
    RewardHistory applyReword(LocalDateTime rewardDateTime, Long userId);

    List<RewardHistory> getRewardHistories(LocalDate rewardDate);
}
