package com.namepro.pass.controller;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import com.namepro.pass.model.ConfigDTO;
import com.namepro.pass.service.MSVoiceService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.namepro.pass.service.VoiceService;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RestController
@SecurityRequirement(name = "namepro")
public class VoiceController {

    @Autowired
    VoiceService voiceService;

	@Autowired
	MSVoiceService msVoiceService;

	@Autowired
	ConfigDTO configDTO;

	@Value("${azure.subscriptionKey}")
	private String subscriptionKey;

	@Value("${azure.languages-list}")
	private String languagesListUrl;

    @GetMapping("/base64/{name}")
    byte[] convertNameToBase64(@PathVariable String name) {
    	return voiceService.textToSpeech(name);
    }
        
    @GetMapping("/file/standard/{name}")
    public ResponseEntity<?> convertNameToFile(@PathVariable String name) {
    	log.info(":::::name::::{}",name);
		File file = null;
    	try {
			if(!configDTO.isSwitchIndex()) {
				log.info("Using FreeTTS for text to Speech conversion");
				file = voiceService.textToFile(name);
			} else {
				log.info("Using Azure for text to Speech conversion");
				file = this.msVoiceService.generateWaveFile(name);
			}

			InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
			return ResponseEntity.ok()
			        .contentLength(file.length())
			        .contentType(MediaType.APPLICATION_OCTET_STREAM)
			        .body(resource);
		} catch (Exception e) {
			log.error(":::::convertNameToFile Exception::::",e);
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
    }

//	@GetMapping("/standardMs/file/{text}")
//	public ResponseEntity<?> convertTextToFile(@PathVariable String text) {
//		log.info(":::::name::::{}",text);
//		try {
//			File file = this.msVoiceService.generateWaveFile(text);
//			InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
//			return ResponseEntity.ok()
//					.contentLength(file.length())
//					.contentType(MediaType.APPLICATION_OCTET_STREAM)
//					.body(resource);
//		} catch (Exception e) {
//			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//		}
//	}

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

	@GetMapping("/standardMs/languages")
	public ResponseEntity<?> getAllSupportedLanguages() {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Ocp-Apim-Subscription-Key", subscriptionKey);
		HttpEntity<String> entity = new HttpEntity<>("body", headers);
    	return restTemplate.exchange(languagesListUrl, HttpMethod.GET, entity, List.class);

	}
    

	@GetMapping("/update/config/{value}")
	public String setConfig(@PathVariable boolean value) {
		configDTO.setSwitchIndex(value);
		return "Successfully updated";
	}

}
