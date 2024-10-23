package com.system.user.user.controller;

import java.util.List;

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

import com.system.user.user.dto.UserDTO;
import com.system.user.user.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService userService;
	
	@PostMapping("/saveUser")
	public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDetails) {
		UserDTO savedUserDetails = userService.saveUserDetails(userDetails);
		return new ResponseEntity<UserDTO>(savedUserDetails, HttpStatus.CREATED);
	}
	
	@GetMapping("/getUser/{userName}")
	public ResponseEntity<UserDTO> getUserDetailsById(@PathVariable String userName){
		UserDTO savedUserDetails = userService.getUserDetailsById(userName);
		return new ResponseEntity<UserDTO>(savedUserDetails, HttpStatus.OK);

	}
	
	@GetMapping("/getAllUsers")
	public ResponseEntity<List<UserDTO>> getAllUsers(){
		List<UserDTO> savedUserDetails = userService.getAllUsersDetails();
		return new ResponseEntity<List<UserDTO>>(savedUserDetails, HttpStatus.OK);
	}
	
	@DeleteMapping("/deleteUser/{userName}")
	public ResponseEntity<String> deleteUserDetailsById(@PathVariable String userName){
		String savedUserDetails = userService.deleteUserDetailsById(userName);
		return new ResponseEntity<String>(savedUserDetails, HttpStatus.OK);

	}
	
	@PutMapping("/updateUser")
	public ResponseEntity<UserDTO> updateUserDetailsById(@RequestBody UserDTO userDetails){
		UserDTO savedUserDetails = userService.updateUserDetailsById(userDetails);
		return new ResponseEntity<UserDTO>(savedUserDetails, HttpStatus.OK);

	}
}
