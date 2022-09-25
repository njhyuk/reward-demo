package com.njhyuk.reward.api;

import com.njhyuk.reward.domain.Reward;
import com.njhyuk.reward.domain.RewardBenefit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RewardControllerTest extends AbstractRestDocControllerTest {
    @Test
    @DisplayName("보상데이터 조회")
    void getReward() throws Exception {
        when(rewardService.getReword())
            .thenReturn(new Reward("아이디", "제목", "설명"));

        this.mockMvc.perform(
            get("/v1/reward")
        ).andExpect(
            status().isOk()
        ).andDo(
            document("reward",
                getDocumentRequest(),
                getDocumentResponse(),
                responseFields(
                    fieldWithPath("id").type(STRING).description("아이디"),
                    fieldWithPath("subject").type(STRING).description("제목"),
                    fieldWithPath("description").type(STRING).description("설명")
                )
            )
        );
    }

    @Test
    @DisplayName("보상 지급")
    void applyReward() throws Exception {
        RewardBenefit rewardBenefit = RewardBenefit.builder()
            .userId(10L)
            .point(100)
            .createdAt(LocalDateTime.of(2022, 10, 1, 0, 1, 1))
            .consecutiveCount(1)
            .build();

        when(rewardService.applyReword(10L))
            .thenReturn(rewardBenefit);

        this.mockMvc.perform(
            post("/v1/reward/apply")
                .header("X-USER-ID", 10)
        ).andExpect(
            status().isOk()
        ).andDo(
            document("reward",
                getDocumentRequest(),
                getDocumentResponse(),
                responseFields(
                    fieldWithPath("userId").type(NUMBER).description(10L),
                    fieldWithPath("point").type(NUMBER).description(100)
                )
            )
        );
    }
}
