package org.iot.itty.login.controller;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.iot.itty.dto.UserDTO;
import org.iot.itty.login.service.LoginService;
import org.iot.itty.login.vo.RequestRegist;
import org.iot.itty.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LoginControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserRepository userRepository;

	@MockBean
	private LoginService loginService;

	@Test
	@DisplayName("회원 가입 성공 테스트")
	public void testRegistUser_Success() {
		// 준비(DB에 존재하지 않는 값으로 입력)
		RequestRegist requestRegist = new RequestRegist();
		requestRegist.setUserEmail("test001@example.com");
		requestRegist.setUserPassword("password");
		requestRegist.setUserName("testName");
		requestRegist.setUserNickname("nickName");
		requestRegist.setUserPhoneNumber("123456789");

		UserDTO userDTO = new UserDTO();
		userDTO.setUserEmail(requestRegist.getUserEmail());
		userDTO.setUserPassword(requestRegist.getUserPassword());
		userDTO.setUserName(requestRegist.getUserName());
		userDTO.setUserNickname(requestRegist.getUserNickname());
		userDTO.setUserPhoneNumber(requestRegist.getUserPhoneNumber());

		// 아이디 중복 시 false 반환
		when(userRepository.existsByUserEmail(userDTO.getUserEmail())).thenReturn(false);

		// When
		int userCode = loginService.registUser(userDTO);
		System.out.println("userCode: " + userCode);

		// Then
		assertThat(userCode).isNotEqualTo(null);
	}

	@Test
	@DisplayName("mock 객체 전달 값 검증 테스트")
	public void registMethodTest() {
		// When
		UserDTO userDTO = new UserDTO();
		userDTO.setUserEmail("user1@example.com");
		userDTO.setUserPassword("password1");
		userDTO.setUserName("username1");
		userDTO.setUserPhoneNumber("123456789");
		userDTO.setUserNickname("user1");

		int userCode = loginService.registUser(userDTO);

		// Then
		verify(loginService, times(1)).registUser(userDTO); // loginService의 registUser 메서드가 1번 호출되었는지 확인

		// mock 객체에 전달된 값 확인
		ArgumentCaptor<UserDTO> argument = ArgumentCaptor.forClass(UserDTO.class);
		verify(loginService).registUser(argument.capture());

		UserDTO capturedUserDTO = argument.getValue();
		System.out.println("userCode: " + userCode);
		System.out.println("UserDTO userEmail: " + capturedUserDTO.getUserEmail());
		System.out.println("UserDTO userPassword: " + capturedUserDTO.getUserPassword());
		System.out.println("UserDTO userName: " + capturedUserDTO.getUserName());
		System.out.println("UserDTO userPhoneNumber: " + capturedUserDTO.getUserPhoneNumber());
		System.out.println("UserDTO userNickname: " + capturedUserDTO.getUserNickname());
	}
}
