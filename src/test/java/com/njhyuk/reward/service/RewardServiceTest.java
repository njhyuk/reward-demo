package com.njhyuk.reward.service;

import com.njhyuk.reward.common.configuration.RewardConfiguration;
import com.njhyuk.reward.common.exception.ClosedRewardException;
import com.njhyuk.reward.common.exception.DuplicatedRewardException;
import com.njhyuk.reward.domain.RewardBenefit;
import com.njhyuk.reward.domain.RewardBenefitRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class RewardServiceTest {
    @MockBean
    private RewardConfiguration rewardConfiguration;
    @Autowired
    private RewardBenefitRepository rewardBenefitRepository;
    private RewardService rewardService;
    private final LocalDateTime rewardDateTime = LocalDateTime.of(2022, 10, 1, 0, 0, 0);

    @BeforeEach
    void setUp() {
        rewardService = new RewardServiceImpl(rewardConfiguration, rewardBenefitRepository);
    }

    @Test
    @DisplayName("선착순 10명인 경우 보상을 지급한다.")
    void applyRewordWhenFirstArrivals() {
        RewardBenefit rewardBenefit = rewardService.applyReword(rewardDateTime, 10L);
        assertTrue(rewardBenefit.getPoint() > 0);
    }

    @Test
    @DisplayName("선착순 10명이 이미 응모한경우 보상이 지급되지 않는다.")
    void failApplyRewordWhenNotFirstArrivals() {
        for (long userId = 1L; userId <= 10L; userId++) {
            rewardBenefitRepository.save(
                RewardBenefit.builder()
                    .userId(userId)
                    .point(100)
                    .createdAt(rewardDateTime)
                    .consecutiveCount(1)
                    .build()
            );
        }

        assertThrows(ClosedRewardException.class, () -> rewardService.applyReword(rewardDateTime, 11L));
    }

    @Test
    @DisplayName("같은날 보상이 이미 지급된 유저는 보상이 지급되지 않는다.")
    void failApplyRewordWhenBenefitedUser() {
        rewardBenefitRepository.save(
            RewardBenefit.builder()
                .userId(10L)
                .point(100)
                .createdAt(rewardDateTime)
                .consecutiveCount(1)
                .build()
        );
        assertThrows(DuplicatedRewardException.class, () -> rewardService.applyReword(rewardDateTime, 10L));
    }
}
