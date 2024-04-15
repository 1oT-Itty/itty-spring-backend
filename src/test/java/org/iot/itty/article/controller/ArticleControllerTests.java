// package org.iot.itty.article.controller;
//
// import static org.hamcrest.Matchers.*;
// import static org.mockito.BDDMockito.*;
// import static org.mockito.ArgumentMatchers.any;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
// import java.util.List;
//
// import org.iot.itty.article.service.ArticleService;
// import org.iot.itty.article.vo.ResponseArticle;
// import org.iot.itty.article.vo.ResponseSelectAllArticleByUserCodeFk;
// import org.iot.itty.dto.ArticleDTO;
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
// class ArticleControllerTests {
//
// 	@Autowired
// 	private MockMvc mockMvc;
//
// 	@MockBean
// 	private ArticleService articleService;
//
// 	@MockBean
// 	private ModelMapper modelMapper;
//
// 	@Test
// 	@DisplayName("자유게시판 전체 조회 테스트")
// 	void selectAllArticleFromFreeBoard() throws Exception {
// 		// 예제 데이터 준비
// 		ArticleDTO articleDTO1 = new ArticleDTO(); // ArticleDTO 설정
// 		articleDTO1.setArticleTitle("Title 1");
// 		ArticleDTO articleDTO2 = new ArticleDTO(); // ArticleDTO 설정
// 		articleDTO2.setArticleTitle("Title 2");
//
// 		List<ArticleDTO> articleDTOs = List.of(articleDTO1, articleDTO2);
//
// 		// ResponseArticle 변환을 위한 예시 데이터
// 		ResponseArticle responseArticle1 = new ResponseArticle();
// 		responseArticle1.setArticleTitle(articleDTO1.getArticleTitle());
//
// 		ResponseArticle responseArticle2 = new ResponseArticle();
// 		responseArticle2.setArticleTitle(articleDTO2.getArticleTitle());
//
// 		// Service 모킹
// 		given(articleService.selectAllArticleFromFreeBoard()).willReturn(articleDTOs);
//
//
// 		// ModelMapper 모킹
// 		given(modelMapper.map(any(ArticleDTO.class), eq(ResponseArticle.class)))
// 			.willAnswer(invocation -> {
// 				ArticleDTO source = invocation.getArgument(0);
// 				ResponseArticle response = new ResponseArticle();
// 				response.setArticleTitle(source.getArticleTitle());
// 				// 필요한 다른 필드도 설정
// 				return response;
// 			});
//
// 		// Service 모킹
// 		given(articleService.selectAllArticleFromFreeBoard()).willReturn(articleDTOs);
//
// 		// 테스트 실행 및 검증
// 		mockMvc.perform(get("/article/freeboard")
// 				.contentType(MediaType.APPLICATION_JSON))
// 			.andExpect(status().isOk())
// 			.andExpect(jsonPath("$[0].articleTitle", is("Title 1")))
// 			.andExpect(jsonPath("$[1].articleTitle", is("Title 2")));
// 	}
//
// 	@Test
// 	@DisplayName("자유게시글 하나 조회 테스트")
// 	void selectFreeBoardArticleByArticleCodePk() throws Exception {
//
// 		int articleCodeFk = 1;
// 		ArticleDTO articleDTO = new ArticleDTO();
// 		articleDTO.setArticleTitle("Title 1");
// 		ResponseArticle responseArticle = new ResponseArticle();
// 		responseArticle.setArticleTitle("Title 2");
//
// 		given(articleService.selectFreeBoardArticleByArticleCodePk(articleCodeFk)).willReturn(articleDTO);
// 		given(modelMapper.map(any(ArticleDTO.class), eq(ResponseArticle.class))).willReturn(responseArticle);
//
// 		mockMvc.perform(get("/article/freeboard/{articleCodePk}", articleCodeFk)
// 				.contentType(MediaType.APPLICATION_JSON))
// 			.andExpect(status().isOk())
// 			.andExpect(jsonPath("$.articleTitle", is("Title 2")));
// 	}
//
// 	@Test
// 	@DisplayName("유저가 작성한 모든 게시글 출력 테스트")
// 	void selectAllArticleByUserCodeFk() throws Exception {
// 		int userCodeFK = 1;
// 		// ArticleDTO 설정
// 		ArticleDTO articleDTO1 = new ArticleDTO();
// 		articleDTO1.setArticleTitle("Title 2");
// 		ArticleDTO articleDTO2 = new ArticleDTO();
// 		articleDTO2.setArticleTitle("Title 2");
//
// 		List<ArticleDTO> articleDTOs = List.of(articleDTO1, articleDTO2);
// 		// 서비스 메서드 호출 시 예상되는 반환 값 설정
// 		given(articleService.selectAllArticleByUserCodeFk(userCodeFK)).willReturn(articleDTOs);
//
// 		// ModelMapper 설정
// 		given(modelMapper.map(any(ArticleDTO.class), eq(ResponseSelectAllArticleByUserCodeFk.class)))
// 			.willAnswer(invocation -> {
// 				ArticleDTO source = invocation.getArgument(0);
// 				ResponseSelectAllArticleByUserCodeFk response = new ResponseSelectAllArticleByUserCodeFk();
// 				response.setArticleTitle(source.getArticleTitle());
// 				// 필요한 다른 필드도 설정
// 				return response;
// 			});
//
// 		// MockMvc를 사용한 컨트롤러 엔드포인트 테스트
// 		mockMvc.perform(get("/user/{userCodeFk}/articles", userCodeFK)
// 				.contentType(MediaType.APPLICATION_JSON))
// 			.andExpect(status().isOk())
// 			.andExpect(jsonPath("$[0].articleTitle", is("Title 2")))
// 			.andExpect(jsonPath("$[1].articleTitle", is("Title 2")));
// 	}
// }
