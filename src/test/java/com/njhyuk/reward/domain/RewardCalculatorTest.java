package com.njhyuk.reward.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RewardCalculatorTest {
    @Test
    @DisplayName("3,5,10일을 제외한 날짜는 100 포인트를 받는다.")
    void calculatePoint() {
        assertAll(
            () -> assertEquals(100, RewardCalculator.calculatePoint(1)),
            () -> assertEquals(100, RewardCalculator.calculatePoint(2)),
            () -> assertEquals(100, RewardCalculator.calculatePoint(4))
        );
    }

    @Test
    @DisplayName("3일 연속 성공한경우 300포인트를 받는다.")
    void additionalPointWhen3InARow() {
        assertEquals(300, RewardCalculator.calculatePoint(3));
    }

    @Test
    @DisplayName("5일 연속 성공한경우 500포인트를 받는다.")
    void additionalPointWhen5InARow() {
        assertEquals(500, RewardCalculator.calculatePoint(5));
    }

    @Test
    @DisplayName("10일 연속 성공한경우 1000포인트를 받는다.")
    void additionalPointWhen10InARow() {
        assertEquals(1000, RewardCalculator.calculatePoint(10));
    }

    @Test
    @DisplayName("10일 이상 성공한 경우 1회부터 다시 시작한다.")
    void additionalPointWhenExceeding10InARow() {
        assertAll(
            () -> assertEquals(100, RewardCalculator.calculatePoint(11)),
            () -> assertEquals(100, RewardCalculator.calculatePoint(12)),
            () -> assertEquals(300, RewardCalculator.calculatePoint(13))
        );
    }
}
