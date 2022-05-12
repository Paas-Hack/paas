package com.namepro.pass.service;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

import com.namepro.pass.model.User;
import com.namepro.pass.model.UserLogin;
import com.namepro.pass.repository.UserLoginRepository;
import com.namepro.pass.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.namepro.pass.dao.UserDao;
import com.namepro.pass.model.DAOUser;
import com.namepro.pass.model.UserDTO;



@Service
public class JwtUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserDao userDao;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserLoginRepository userLoginRepository;
	
	 
	private PasswordEncoder bcryptEncoder = new BCryptPasswordEncoder();
	

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = userRepository.findById(username);
		UserLogin loginUser;
		if (!user.isPresent()) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		} else {
			loginUser = userLoginRepository.findByUser(user.get());
		}
		return new org.springframework.security.core.userdetails.User(username, loginUser.getPassword(),
				new ArrayList<>());
	}

	public UserDetails loadUserByUsername(String username, boolean isLoggedIn) throws UsernameNotFoundException {
		Optional<User> user = userRepository.findById(username);
		if (!user.isPresent()) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		UserLogin loginUser = saveLoginDetails(user.get(), isLoggedIn);
		if(Objects.nonNull(loginUser)) {
			return new org.springframework.security.core.userdetails.User(username, loginUser.getPassword(),
					new ArrayList<>());
		}
		return null;
	}


	public UserLogin saveUser(UserDTO userDto) {
		Optional<User> user = userRepository.findById(userDto.getUsername());
		UserLogin userLogin = new UserLogin();
		if(user.isPresent()) {
			userLogin.setUser(user.get());
			userLogin.setCreatedBy(userDto.getUsername());
			userLogin.setPassword(bcryptEncoder.encode(userDto.getPassword()));
			userLogin.setCreatedTs(LocalDateTime.now());
		}
		return userLoginRepository.save(userLogin);
	}

	public UserLogin saveLoginDetails(User user, boolean isLoggedIn) {
		UserLogin loginUser = userLoginRepository.findByUser(user);
		if(Objects.nonNull(loginUser)) {
			if(isLoggedIn) {
				loginUser.setLastLogin(LocalDateTime.now());
			}
			loginUser.setLoggedIn(isLoggedIn);
			return userLoginRepository.save(loginUser);
		}
		return null;
	}
}