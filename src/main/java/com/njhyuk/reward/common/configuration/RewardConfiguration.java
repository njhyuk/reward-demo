package com.njhyuk.reward.common.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "reward")
public class RewardConfiguration {
    private String id;
    private String subject;
    private String description;
}
