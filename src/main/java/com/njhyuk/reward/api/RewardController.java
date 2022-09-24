package com.njhyuk.reward.api;

import com.njhyuk.reward.api.v1.RewardModel;
import com.njhyuk.reward.domain.Reward;
import com.njhyuk.reward.service.RewardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RewardController {
    private final RewardService rewardService;

    @GetMapping("/v1/reward")
    public RewardModel getReward() {
        Reward reword = rewardService.getReword();
        return RewardModel.from(reword);
    }
}
