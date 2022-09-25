package com.njhyuk.reward.api.v1;

import com.njhyuk.reward.domain.RewardBenefit;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RewardBenefitModel {
    private Long userId;
    private Integer point;

    public static RewardBenefitModel from(RewardBenefit rewardBenefit) {
        return new RewardBenefitModel(rewardBenefit.getUserId(), rewardBenefit.getPoint());
    }
}
