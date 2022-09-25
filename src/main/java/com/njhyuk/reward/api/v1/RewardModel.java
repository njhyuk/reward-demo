package com.njhyuk.reward.api.v1;

import com.njhyuk.reward.domain.RewardDetail;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RewardModel {
    private String id;
    private String subject;
    private String description;

    public static RewardModel from(RewardDetail reword) {
        return new RewardModel(reword.getId(), reword.getSubject(), reword.getDescription());
    }
}
