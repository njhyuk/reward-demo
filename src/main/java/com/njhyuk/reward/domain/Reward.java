package com.njhyuk.reward.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Getter
@Entity
@Table(name = "rewards")
@NoArgsConstructor
@AllArgsConstructor
public class Reward {
    @Id
    @Column(name = "rewardDate")
    private LocalDate rewardDate;
    private Integer rewardCount;

    public void increaseCount() {
        rewardCount += 1;
    }
}
