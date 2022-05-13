package com.namepro.pass.controller;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import com.namepro.pass.service.MSVoiceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.namepro.pass.model.UserCred;
import com.namepro.pass.model.UserSpeech;
import com.namepro.pass.repository.UserCredRepository;
import com.namepro.pass.repository.UserSpeechRepository;
import com.namepro.pass.service.VoiceService;

@Slf4j
@RestController
public class VoiceController {

    @Autowired
    VoiceService voiceService;

	@Autowired
	MSVoiceService msVoiceService;
    
    @Autowired
	UserCredRepository userCredRepository;
    
    @Autowired
	UserSpeechRepository userSpeechRepository;

    @GetMapping("/base64/{name}")
    byte[] convertNameToBase64(@PathVariable String name) {
        return voiceService.textToSpeech(name);
    }
        
    @GetMapping("/file/standard/{name}")
    public ResponseEntity<?> convertNameToFile(@PathVariable String name) {
    	log.info(":::::name::::{}",name);
    	try {
			File file = voiceService.textToFile(name);
			InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
			return ResponseEntity.ok()
			        .contentLength(file.length())
			        .contentType(MediaType.APPLICATION_OCTET_STREAM)
			        .body(resource);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
    }

	@GetMapping("/standardMs/file/{text}")
	public ResponseEntity<?> convertTextToFile(@PathVariable String text) {
		log.info(":::::name::::{}",text);
		try {
			File file = this.msVoiceService.generateWaveFile(text);
			InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
			return ResponseEntity.ok()
					.contentLength(file.length())
					.contentType(MediaType.APPLICATION_OCTET_STREAM)
					.body(resource);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/standardMs/speak")
	public ResponseEntity<?> convertSpeakToText() {
		try {
			String text = this.msVoiceService.speechToText();
			return ResponseEntity.ok()
					.contentType(MediaType.TEXT_PLAIN)
					.body(text);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
    
    @GetMapping("/usercred")
	public ResponseEntity<List<UserCred>> getAllUserCreds(@RequestParam(required = false) String username) {
		try {
			List<UserCred> userCred = new ArrayList<UserCred>();

			if (username == null)
				userCredRepository.findAll().forEach(userCred::add);
			else
				//userCredRepository.findByUsernameContaining(username).forEach(userCred::add);
				userCredRepository.findByUsername(username).forEach(userCred::add);

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
