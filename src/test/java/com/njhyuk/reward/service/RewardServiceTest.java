package com.njhyuk.reward.service;

import com.njhyuk.reward.common.configuration.RewardConfiguration;
import com.njhyuk.reward.common.exception.ClosedRewardException;
import com.njhyuk.reward.common.exception.DuplicatedRewardException;
import com.njhyuk.reward.domain.Reward;
import com.njhyuk.reward.domain.RewardHistory;
import com.njhyuk.reward.domain.RewardHistoryRepository;
import com.njhyuk.reward.domain.RewardRepository;
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
    private RewardRepository rewardRepository;
    @Autowired
    private RewardHistoryRepository rewardHistoryRepository;
    private RewardService rewardService;
    private final LocalDateTime rewardDateTime = LocalDateTime.of(2022, 10, 1, 0, 0, 0);

    @BeforeEach
    void setUp() {
        rewardService = new RewardServiceImpl(rewardConfiguration, rewardRepository, rewardHistoryRepository);
    }

    @Test
    @DisplayName("선착순 10명 이내인 경우 보상을 지급한다.")
    void applyRewordWhenFirstArrivals() {
        rewardRepository.save(new Reward(rewardDateTime.toLocalDate(), 9));

        RewardHistory rewardHistory = rewardService.applyReword(rewardDateTime, 10L);
        assertEquals(100, rewardHistory.getPoint());
    }

    @Test
    @DisplayName("선착순 10명이 이미 응모한경우 보상이 지급되지 않는다.")
    void failApplyRewordWhenNotFirstArrivals() {
        rewardRepository.save(new Reward(rewardDateTime.toLocalDate(), 10));

        assertThrows(ClosedRewardException.class, () -> rewardService.applyReword(rewardDateTime, 11L));
    }

    @Test
    @DisplayName("같은날 보상이 이미 지급된 유저는 보상이 지급되지 않는다.")
    void failApplyRewordWhenBenefitedUser() {
        rewardHistoryRepository.save(
            RewardHistory.builder()
                .userId(10L)
                .point(100)
                .rewardedAt(rewardDateTime)
                .consecutiveCount(1)
                .build()
        );
        assertThrows(DuplicatedRewardException.class, () -> rewardService.applyReword(rewardDateTime, 10L));
    }

    @Test
    @DisplayName("3일 연속 성공한경우 300포인트를 받는다.")
    void additionalPointWhen3InARow() {
        rewardHistoryRepository.save(
            RewardHistory.builder()
                .userId(10L)
                .point(100)
                .rewardedAt(rewardDateTime.minusDays(1))
                .consecutiveCount(2)
                .build()
        );
        RewardHistory rewardHistory = rewardService.applyReword(rewardDateTime, 10L);
        assertEquals(300, rewardHistory.getPoint());
    }

    @Test
    @DisplayName("5일 연속 성공한경우 500포인트를 받는다.")
    void additionalPointWhen5InARow() {
        rewardHistoryRepository.save(
            RewardHistory.builder()
                .userId(10L)
                .point(100)
                .rewardedAt(rewardDateTime.minusDays(1))
                .consecutiveCount(4)
                .build()
        );
        RewardHistory rewardHistory = rewardService.applyReword(rewardDateTime, 10L);
        assertEquals(500, rewardHistory.getPoint());
    }

    @Test
    @DisplayName("10일 연속 성공한경우 1000포인트를 받는다.")
    void additionalPointWhen10InARow() {
        rewardHistoryRepository.save(
            RewardHistory.builder()
                .userId(10L)
                .point(100)
                .rewardedAt(rewardDateTime.minusDays(1))
                .consecutiveCount(9)
                .build()
        );
        RewardHistory rewardHistory = rewardService.applyReword(rewardDateTime, 10L);
        assertEquals(1000, rewardHistory.getPoint());
    }

    @Test
    @DisplayName("10일 이상 성공한 경우 1회부터 다시 시작한다.")
    void additionalPointWhenExceeding10InARow() {
        rewardHistoryRepository.save(
            RewardHistory.builder()
                .userId(10L)
                .point(100)
                .rewardedAt(rewardDateTime.minusDays(1))
                .consecutiveCount(10)
                .build()
        );
        RewardHistory rewardHistory = rewardService.applyReword(rewardDateTime, 10L);
        assertEquals(100, rewardHistory.getPoint());
    }
}
