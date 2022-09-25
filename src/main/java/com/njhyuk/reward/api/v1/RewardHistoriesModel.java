package com.njhyuk.reward.api.v1;

import com.njhyuk.reward.domain.RewardHistory;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class RewardHistoriesModel {
    private List<RewardHistoryModel> rewardHistories;

    public static RewardHistoriesModel from(List<RewardHistory> rewardHistories) {
        return new RewardHistoriesModel(
            rewardHistories.stream()
                .map(v -> new RewardHistoryModel(v.getUserId(), v.getUser().getName(), v.getPoint()))
                .collect(Collectors.toList())
        );
    }

    @Getter
    @AllArgsConstructor
    static class RewardHistoryModel {
        private Long userId;
        private String userName;
        private Integer point;
    }
}
