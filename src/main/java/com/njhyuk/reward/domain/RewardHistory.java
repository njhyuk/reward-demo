package com.njhyuk.reward.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@Entity
@Builder
@Table(name = "reward_benefits")
@NoArgsConstructor
@AllArgsConstructor
public class RewardHistory {
    @Id
    @GeneratedValue
    private Long id;
    private Long userId;
    private Integer point;
    private LocalDateTime rewardedAt;
    private Integer consecutiveCount;

    public static final RewardHistory EMPTY = new RewardHistory(0L, 0L, 0, LocalDateTime.MIN, 0);
}
