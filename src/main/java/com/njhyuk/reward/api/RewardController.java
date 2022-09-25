package com.njhyuk.reward.api;

import com.njhyuk.reward.api.v1.RewardBenefitModel;
import com.njhyuk.reward.api.v1.RewardModel;
import com.njhyuk.reward.domain.Reward;
import com.njhyuk.reward.domain.RewardBenefit;
import com.njhyuk.reward.service.RewardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

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
    public RewardBenefitModel applyReward(@RequestHeader(name = "X-USER-ID") Long userId) {
        RewardBenefit rewardBenefit = rewardService.applyReword(LocalDateTime.now(), userId);
        return RewardBenefitModel.from(rewardBenefit);
    }
}
