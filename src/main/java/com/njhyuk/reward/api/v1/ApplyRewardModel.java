package com.njhyuk.reward.api.v1;

import com.njhyuk.reward.domain.RewardHistory;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApplyRewardModel {
    private Long userId;
    private Integer point;

    public static ApplyRewardModel from(RewardHistory rewardHistory) {
        return new ApplyRewardModel(rewardHistory.getUserId(), rewardHistory.getPoint());
    }
}
