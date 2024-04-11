package org.iot.itty.article.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.iot.itty.article.service.ReplyService;
import org.iot.itty.article.vo.RequestModifyReply;
import org.iot.itty.article.vo.RequestRegistReply;
import org.iot.itty.article.vo.ResponseModifyReply;
import org.iot.itty.article.vo.ResponseRegistReply;
import org.iot.itty.article.vo.ResponseSelectAllReplyByUserCodeFk;
import org.iot.itty.article.vo.ResponseSelectReplyByArticleCodeFk;
import org.iot.itty.dto.ReplyDTO;
import org.iot.itty.user.service.UserService;
import org.iot.itty.user.vo.ResponseAuthorOfReplyList;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class ReplyController {

	private ReplyService replyService;
	private UserService userService;
	private ModelMapper mapper;

	@Autowired
	public ReplyController(ReplyService replyService, UserService userService, ModelMapper mapper) {
		this.replyService = replyService;
		this.userService = userService;
		this.mapper = mapper;
	}

	@GetMapping("/reply/article/{articleCodeFk}")
	public ResponseEntity<List<ResponseSelectReplyByArticleCodeFk>> selectReplyByArticleCodeFk(@PathVariable("articleCodeFk") int articleCodeFk) {
		List<ReplyDTO> replyDTOList = replyService.selectReplyByArticleCodeFk(articleCodeFk);

		// mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		List<ResponseSelectReplyByArticleCodeFk> responseSelectReplyByArticleCodeFkList = new ArrayList<>();

		if (replyDTOList != null) {
			responseSelectReplyByArticleCodeFkList = replyDTOList
				.stream()
				.peek(replyDTO -> replyDTO.setUserDTO(userService.selectUserByUserCodePk(replyDTO.getUserCodeFk())))
				.map(ReplyDTO -> mapper.map(ReplyDTO, ResponseSelectReplyByArticleCodeFk.class))
				.toList();
		}

		return ResponseEntity.status(HttpStatus.OK).body(responseSelectReplyByArticleCodeFkList);
	}

	/* 회원별 댓글 list 조회 */
	@GetMapping("reply/user/{userCodeFk}")
	public ResponseEntity<List<ResponseSelectAllReplyByUserCodeFk>> selectAllReplyByUserCodeFk(@PathVariable("userCodeFk") int userCodeFk) {
		List<ReplyDTO> replyDTOList = replyService.selectAllReplyByUserCodeFk(userCodeFk);
		List<ResponseSelectAllReplyByUserCodeFk> responseSelectAllReplyByUserCodeFkList = new ArrayList<>();

		if (replyDTOList != null) {
			responseSelectAllReplyByUserCodeFkList = replyDTOList
				.stream()
				.map(ReplyDTO -> mapper.map(ReplyDTO, ResponseSelectAllReplyByUserCodeFk.class))
				.toList();
		}

		return ResponseEntity.status(HttpStatus.OK).body(responseSelectAllReplyByUserCodeFkList);
	}

	/* 댓글 등록 */
	@PostMapping("/reply/regist")
	public ResponseEntity<ResponseRegistReply> registReply(@RequestBody RequestRegistReply requestRegistReply) {
		ReplyDTO requestReplyDTO = mapper.map(requestRegistReply, ReplyDTO.class);
		ResponseRegistReply responseRegistReply = new ResponseRegistReply();

		try {
			/* 서비스 단 다녀오기 */
			replyService.registReply(requestReplyDTO);
			responseRegistReply.setResultCode(201);
			responseRegistReply.setMessage("댓글 등록 성공");
		} catch (Exception e) {
			responseRegistReply.setResultCode(500);
			responseRegistReply.setMessage(e.getMessage());
		}

		return ResponseEntity.status(HttpStatus.CREATED).body(responseRegistReply);
	}

	/* 댓글 수정 */
	@PutMapping("/reply/{replyCodePk}/modify")
	public ResponseEntity<Map<String, String>> modifyReply(
		@RequestBody RequestModifyReply requestModifyReply,
		@PathVariable("replyCodePk") int replyCodePk
	) {
		ReplyDTO requestReplyDTO = mapper.map(requestModifyReply, ReplyDTO.class);
		ReplyDTO responseReplyDTO = replyService.modifyReply(requestReplyDTO, replyCodePk);
		Map<String, String> result = new HashMap<>();
		if (responseReplyDTO != null) {
			result.put(
				"message",
				"reply from article #" + responseReplyDTO.getArticleCodeFk() +
					" written by user #" + responseReplyDTO.getUserCodeFk() +
					" modified successfully."
			);
		} else {
			result.put(
				"message",
				"Failed to modify reply."
			);
		}
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(result);
	}

	@DeleteMapping("/reply/{replyCodePk}/delete")
	public ResponseEntity<Map<String, String>> deleteReply(@PathVariable("replyCodePk") int replyCodePk) {
		String returnedMessage = replyService.deleteReply(replyCodePk);
		Map<String, String> result = new HashMap<>();

		result.put("message", returnedMessage);
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
}
