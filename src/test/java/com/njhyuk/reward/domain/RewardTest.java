package com.njhyuk.reward.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class RewardTest {

    @Test
    @DisplayName("선착순 응모자 수를 증가시킨다.")
    void increaseCount() {
        Reward reward = new Reward(LocalDate.now(), 1);
        reward.increaseCount();
        reward.increaseCount();
        assertEquals(3, reward.getRewardCount());
    }
}
