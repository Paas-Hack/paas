package com.namepro.pass.service;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.namepro.pass.model.*;
import com.namepro.pass.repository.UserLoginRepository;
import com.namepro.pass.repository.UserPronunciationRepository;
import com.namepro.pass.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class JwtUserDetailsService implements UserDetailsService {
	

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserLoginRepository userLoginRepository;

	@Autowired
	UserPronunciationRepository userPronunciationRepository;
	
	 
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

	public List<User> findByName(String lanId) {
		return userRepository.findByName(lanId);
	}

	public void savePronunciation(UserPronunciationDTO userDto) {
		Optional<User> user = userRepository.findById(userDto.getUsername());
		if (!user.isPresent()) {
			throw new UsernameNotFoundException("User not found with username: " + userDto.getUsername());
		}
		UserPronunciation pronunciation = new UserPronunciation();
		pronunciation.setUser(user.get());
		pronunciation.setPronunciation(userDto.getRecording());
		pronunciation.setPrimary(userDto.isPrimary());
		pronunciation.setCreatedBy(userDto.getUsername());
		pronunciation.setCreatedTs(LocalDateTime.now());
		userPronunciationRepository.save(pronunciation);
	}

	public List<UserPronunciation>  getRecordings(String name) {
		Optional<User> user = userRepository.findById(name);
		if (!user.isPresent()) {
			throw new UsernameNotFoundException("User not found with username: " + name);
		}
		return userPronunciationRepository.findAllByUser(user.get());
	}

	public void deletePronunciation(String userId, long id) {

		Optional<User> user = userRepository.findById(userId);
		if (!user.isPresent()) {
			throw new UsernameNotFoundException("User not found with username: " + userId);
		}
		Optional<UserPronunciation> userPronunciation = userPronunciationRepository.findById(id);
		if(userPronunciation.isPresent()) {
			userPronunciationRepository.delete(userPronunciation.get());
		}
	}
}