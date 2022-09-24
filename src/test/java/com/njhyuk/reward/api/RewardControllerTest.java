package com.njhyuk.reward.api;

import com.njhyuk.reward.domain.Reward;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RewardControllerTest extends AbstractRestDocControllerTest {
    @Test
    @DisplayName("보상데이터 조회")
    void calculator() throws Exception {
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
}
