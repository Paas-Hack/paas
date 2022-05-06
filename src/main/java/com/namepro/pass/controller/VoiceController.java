package com.namepro.pass.controller;

import com.namepro.pass.service.VoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VoiceController {

    @Autowired
    VoiceService voiceService;

    @GetMapping("/base64/{name}")
    byte[] convertNameToBase64(@PathVariable String name) {
        return voiceService.textToSpeech(name);
    }

}
