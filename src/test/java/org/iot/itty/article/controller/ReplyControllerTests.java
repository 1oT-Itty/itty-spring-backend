// package org.iot.itty.article.controller;
//
// import static org.mockito.BDDMockito.*;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
// import java.util.Arrays;
//
// import org.iot.itty.article.service.ReplyService;
// import org.iot.itty.article.vo.ResponseSelectReplyByArticleCodeFk;
// import org.iot.itty.dto.ReplyDTO;
//
// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.Test;
// import org.modelmapper.ModelMapper;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.http.MediaType;
// import org.springframework.test.web.servlet.MockMvc;
//
// @SpringBootTest
// @AutoConfigureMockMvc
// class ReplyControllerTests {
//
// 	@Autowired
// 	private MockMvc mockMvc;
//
// 	@MockBean
// 	private ModelMapper modelMapper;
//
// 	@MockBean
// 	private ReplyService replyService;
//
// 	@DisplayName("댓글 전체 조회 테스트")
// 	@Test
// 	void selectReplyByArticleCodeFk() throws Exception {
// 		// 준비
// 		int articleCodeFk = 1; // 테스트용 글 코드
// 		ReplyDTO mockReplyDTO = new ReplyDTO(); // 테스트용 댓글 DTO 생성 및 초기화
// 		ResponseSelectReplyByArticleCodeFk mockResponse = new ResponseSelectReplyByArticleCodeFk(); // 변환될 응답 객체
//
// 		given(replyService.selectReplyByArticleCodeFk(articleCodeFk)).willReturn(Arrays.asList(mockReplyDTO));
// 		given(modelMapper.map(any(ReplyDTO.class), eq(ResponseSelectReplyByArticleCodeFk.class))).willReturn(mockResponse);
//
// 		// 실행 & 검증
// 		mockMvc.perform(get("/reply/article/{articleCodeFk}", articleCodeFk)
// 				.contentType(MediaType.APPLICATION_JSON))
// 			.andExpect(status().isOk())
// 			.andExpect(jsonPath("$").isArray()); // 반환되는 JSON이 배열인지 확인
//
// 		// 후처리
// 		verify(replyService, times(1)).selectReplyByArticleCodeFk(articleCodeFk);
// 	}
//
// }