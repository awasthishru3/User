package com.system.user.user.dto;

import java.util.Set;

import com.system.user.user.entity.Role;
import com.system.user.user.entity.UserEntity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserDTO {

	private Integer id;
	private String firstName;
	private String lastName;
	private String userName;
	private String password;
	private Set<Role> role;
	
}