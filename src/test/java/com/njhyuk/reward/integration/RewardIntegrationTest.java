package com.njhyuk.reward.integration;


import com.njhyuk.reward.service.RewardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RewardIntegrationTest {
    private MockMvc mockMvc;
    @Autowired
    private RewardService rewardService;
    private ExecutorService executorService;
    private CountDownLatch countDownLatch;
    private final int threadCount = 300;

    @BeforeEach
    void init(WebApplicationContext webApplicationContext) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        executorService = Executors.newFixedThreadPool(threadCount);
        countDownLatch = new CountDownLatch(threadCount);
    }

    @Test
    @DisplayName("선착순 보상 지급 동시성 통합 테스트")
    void getRewardDetail() throws Exception {
        IntStream.range(0, threadCount)
            .forEach(e -> executorService.submit(() -> {
                    try {
                        this.mockMvc.perform(
                            post("/v1/reward/apply")
                                .header("X-USER-ID", e + 1L)
                        ).andExpect(
                            status().isOk()
                        );
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    } finally {
                        countDownLatch.countDown();
                    }
                }
            ));
        countDownLatch.await();

        assertEquals(10, rewardService.getRewardHistories(LocalDate.now()).size());
    }

}
