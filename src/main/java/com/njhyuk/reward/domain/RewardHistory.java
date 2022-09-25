package com.njhyuk.reward.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@Entity
@Builder
@Table(name = "reward_histories")
@NoArgsConstructor
@AllArgsConstructor
public class RewardHistory {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "point")
    private Integer point;
    @Column(name = "rewarded_at")
    private LocalDateTime rewardedAt;
    @Column(name = "consecutive_count")
    private Integer consecutiveCount;
    @OneToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    public static final RewardHistory EMPTY = new RewardHistory(0L, 0L, 0, LocalDateTime.MIN, 0, User.EMPTY);
}
