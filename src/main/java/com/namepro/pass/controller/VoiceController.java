package com.namepro.pass.controller;

import com.namepro.pass.model.UserCred;
import com.namepro.pass.model.UserSpeech;
import com.namepro.pass.repository.UserCredRepository;
import com.namepro.pass.repository.UserSpeechRepository;
import com.namepro.pass.service.VoiceService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VoiceController {

    @Autowired
    VoiceService voiceService;
    
    @Autowired
	UserCredRepository userCredRepository;
    
    @Autowired
	UserSpeechRepository userSpeechRepository;

    @GetMapping("/base64/{name}")
    byte[] convertNameToBase64(@PathVariable String name) {
        return voiceService.textToSpeech(name);
    }
    
    @GetMapping("/usercred")
	public ResponseEntity<List<UserCred>> getAllUserCreds(@RequestParam(required = false) String username) {
		try {
			List<UserCred> userCred = new ArrayList<UserCred>();

			if (username == null)
				userCredRepository.findAll().forEach(userCred::add);
			else
				userCredRepository.findByUsernameContaining(username).forEach(userCred::add);

			if (userCred.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(userCred, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
    
    @PostMapping("/usercred")
	public ResponseEntity<UserCred> createUserCred(@RequestBody UserCred usercred) {
		try {
			UserCred _usercred = userCredRepository
					.save(new UserCred(usercred.getUsername(), usercred.getPassword()));
			return new ResponseEntity<>(_usercred, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
    
    @GetMapping("/userspeech")
	public ResponseEntity<List<UserSpeech>> getAllUserSpeeches(@RequestParam(required = false) String username) {
		try {
			List<UserSpeech> userSpeech = new ArrayList<UserSpeech>();

			if (username == null)
				userSpeechRepository.findAll().forEach(userSpeech::add);
			else
				userSpeechRepository.findByUsernameContaining(username).forEach(userSpeech::add);

			if (userSpeech.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(userSpeech, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
    
    @PostMapping("/userspeech")
	public ResponseEntity<UserSpeech> createUserSpeech(@RequestBody UserSpeech userSpeech) {
		try {
			UserSpeech _userspeech = userSpeechRepository
					.save(new UserSpeech(userSpeech.getUsername(), userSpeech.getSpeechtext()));
			return new ResponseEntity<>(_userspeech, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
