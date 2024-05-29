package com.panel.user;

import com.panel.user.DTO.ResetPassword;
import com.panel.user.DTO.UserDTO;
import com.panel.user.DTO.loginDTO;
import com.panel.user.DTO.newPassword;
import com.panel.user.Entity.User;
import com.panel.user.Response.AuthenticationResponse;
import com.panel.user.Response.RegisterResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class UserApplicationTests {

	@Test
	void contextLoads() {
	}

	RestTemplate restTemplate=new RestTemplate();

	@Test
	public void testRegister(){
		String url="http://localhost:8075/api/v1/user/register";
		UserDTO user=new UserDTO("ahmedhany@gmail.com","389","389",false);
		ResponseEntity<RegisterResponse> response=restTemplate.postForEntity(url,user,RegisterResponse.class);
		System.out.println(response.getBody());
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}

	@Test
	public void testUpdateUser(){
		String url="http://localhost:8075/api/v1/user/update";
		User user=new User("ahmedhany@gmail.com","459");
		restTemplate.put(url,user);
		System.out.println("updated");
	}

	@Test
	public void testDeleteUser(){
		String url="http://localhost:8075/api/v1/user/delete";
		String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhaG1lZEBnbWFpbC5jb20iLCJleHAiOjE3MTcwMTA4NTIsImlhdCI6MTcxNzAwMDA1Mn0.Brgg-w4933WOQ3hnVd1RQoOAhGBfEdWXFnJ0SniNbJo";
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + token);

		HttpEntity<String> entity = new HttpEntity<>(headers);

		restTemplate.exchange(url, HttpMethod.DELETE, entity, Void.class);

		System.out.println("deleted");
	}

	@Test
	public void testFindById(){
		String url="http://localhost:8075/api/v1/user/findUser";
		String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhaG1lZEBnbWFpbC5jb20iLCJleHAiOjE3MTcwMTA4NTIsImlhdCI6MTcxNzAwMDA1Mn0.Brgg-w4933WOQ3hnVd1RQoOAhGBfEdWXFnJ0SniNbJo";
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + token);

		HttpEntity<String> entity = new HttpEntity<>(headers);

		ResponseEntity<User> response =restTemplate.exchange(url, HttpMethod.GET, entity, User.class);

		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}

	@Test
	public void testLogin(){
		String url="http://localhost:8075/api/v1/user/login";
		loginDTO user=new loginDTO("ahmedhany@gmail.com","389");
		ResponseEntity<AuthenticationResponse> response=restTemplate.postForEntity(url,user,AuthenticationResponse.class);
		System.out.println(response.getBody());
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}

	@Test
	public void testVerifyEmail(){
		String url="http://localhost:8075/api/v1/user/checkEmail";
		ResetPassword email =new ResetPassword("ahmedhany@gmail.com");
		ResponseEntity<String> response=restTemplate.postForEntity(url,email,String.class);
		System.out.println(response.getBody());
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}

	@Test
	public void testResetPassword(){
		String url="http://localhost:8075/api/v1/user/resetPassword";
		newPassword pass =new newPassword("ahmedhany@gmail.com",123456,"789");
		ResponseEntity<String> response=restTemplate.postForEntity(url,pass,String.class);
		System.out.println(response.getBody());
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}


	@Test
	public void testRegenerateOTP(){
		String url="http://localhost:8075/api/v1/user/regenerateOTP";
		String email ="mohamed@gmail.com";
		ResponseEntity<String> response=restTemplate.postForEntity(url,email,String.class);
		System.out.println(response.getBody());
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}


}
