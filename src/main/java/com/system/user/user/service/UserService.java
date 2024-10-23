package com.system.user.user.service;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.system.user.user.dao.UserRepo;
import com.system.user.user.dto.UserDTO;
import com.system.user.user.entity.UserEntity;
import com.system.user.user.exception.GeneralException;
import com.system.user.user.exception.UserAlreadyPresentException;
import com.system.user.user.exception.UserNotFoundException;

import lombok.Builder;

@Builder
@Service
public class UserService implements UserDetailsService{

//public class UserService implements UserDetailsService{
	@Autowired
	BCryptPasswordEncoder passwordEncoder;

	@Autowired
	UserRepo userRepo;

	public UserDTO saveUserDetails(UserDTO userDetails) {
		UserEntity existingUser = null;
		if (userDetails.getUserName() != null) {
			existingUser = userRepo.findByUserName(userDetails.getUserName()).orElse(null);
		} else {
			throw new GeneralException("User Name field is required");
		}
		if (existingUser == null) {
			UserEntity userDetailEntity = convertUserDTOintoUserEntity(userDetails);
			String encodedPassword = passwordEncoder.encode(userDetailEntity.getPassword());
			userDetailEntity.setPassword(encodedPassword);
			UserEntity savedUserEntity = userRepo.save(userDetailEntity);
			UserDTO saveUserDTO = convertUserEntityToUserDTO(savedUserEntity);
			return saveUserDTO;
		} else {
			throw new UserAlreadyPresentException(
					"User with user name: " + userDetails.getUserName() + " is already present");
		}
	}

	private UserDTO convertUserEntityToUserDTO(UserEntity savedUserEntity) {
		return new UserDTO().builder().firstName(savedUserEntity.getFirstName()).lastName(savedUserEntity.getLastName())
				.role(savedUserEntity.getRole()).id(savedUserEntity.getId()).userName(savedUserEntity.getUserName())
				.password(savedUserEntity.getPassword())
				.build();
	}

	

	private UserEntity convertUserDTOintoUserEntity(UserDTO userDetails) {
		return new UserEntity().builder().firstName(userDetails.getFirstName()).lastName(userDetails.getLastName())
				.role(userDetails.getRole()).id(userDetails.getId()).userName(userDetails.getUserName())
				.password(userDetails.getPassword())
				.build();

	}

	public UserDTO getUserDetailsById(String userName) {
		UserEntity userEntity = userRepo.findByUserName(userName).orElse(null);
		if (userEntity == null) {
			throw new UserNotFoundException("User is not present in the system");
		}
		return convertUserEntityToUserDTO(userEntity);
	}

	public List<UserDTO> getAllUsersDetails() {
		List<UserEntity> userEntityList = userRepo.findAll();
		if (userEntityList.isEmpty()) {
			throw new UserNotFoundException("No User Present in the system");
		}
		List<UserDTO> userDTOList = userEntityList.stream().map(x -> convertUserEntityToUserDTO(x)).toList();
		return userDTOList;
	}

	public String deleteUserDetailsById(String userName) {
		UserEntity existingUser = userRepo.findByUserName(userName).orElse(null);
		if (null == existingUser) {
			throw new UserNotFoundException("User is not present");
		} else {
			try {
				userRepo.deleteById(existingUser.getId());
				return "User deleted successfully";
			} catch (Exception e) {
				throw new GeneralException("Exception occured while deleting user"+e);
			}
		}
	}

	public UserDTO updateUserDetailsById(UserDTO userDetails) {
		UserEntity existingUser = userRepo.findByUserName(userDetails.getUserName()).orElse(null);

		if (null == existingUser) {
			throw new UserNotFoundException("User is not present");
		} else {
			if (userDetails.getFirstName() != null) {
				existingUser.setFirstName(userDetails.getFirstName());
			}
			if (userDetails.getLastName() != null) {
				existingUser.setLastName(userDetails.getLastName());
			}
			if (userDetails.getRole() != null) {
				existingUser.setRole(userDetails.getRole());
			}
			if (userDetails.getPassword() != null) {
				String encodedPassword = passwordEncoder.encode(userDetails.getPassword());
				userDetails.setPassword(encodedPassword);
				existingUser.setPassword(userDetails.getPassword());
			}
			UserEntity updatedUserEntity = userRepo.save(existingUser);

			return convertUserEntityToUserDTO(updatedUserEntity);
		}
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity user = userRepo.findByUserName(username).get();
		if(user == null) {
			throw new UsernameNotFoundException("User is not present");
		}
		return new User(user.getUserName(), user.getPassword(), (Collection<? extends GrantedAuthority>) user.getRole());
	}
}
