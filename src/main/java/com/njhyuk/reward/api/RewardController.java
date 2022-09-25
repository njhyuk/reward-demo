package com.njhyuk.reward.api;

import com.njhyuk.reward.api.v1.ApplyRewardModel;
import com.njhyuk.reward.api.v1.RewardHistoriesModel;
import com.njhyuk.reward.api.v1.RewardModel;
import com.njhyuk.reward.domain.Reward;
import com.njhyuk.reward.domain.RewardHistory;
import com.njhyuk.reward.service.RewardService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class RewardController {
    private final RewardService rewardService;

    @GetMapping("/v1/reward")
    public RewardModel getReward() {
        Reward reword = rewardService.getReword();
        return RewardModel.from(reword);
    }

    @PostMapping("/v1/reward/apply")
    public ApplyRewardModel applyReward(@RequestHeader("X-USER-ID") Long userId) {
        RewardHistory rewardHistory = rewardService.applyReword(LocalDateTime.now(), userId);
        return ApplyRewardModel.from(rewardHistory);
    }

    @GetMapping("/v1/reward/history")
    public RewardHistoriesModel getRewardHistories(
        @RequestParam("rewardDate")
        @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate rewardDate
    ) {
        List<RewardHistory> rewardHistories = rewardService.getRewardHistories(rewardDate);
        return RewardHistoriesModel.from(rewardHistories);
    }
}
