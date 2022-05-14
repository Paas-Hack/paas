package com.namepro.pass.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Locale;

import javax.sound.sampled.AudioFileFormat;
import javax.speech.Central;
import javax.speech.EngineException;
import javax.speech.synthesis.Synthesizer;
import javax.speech.synthesis.SynthesizerModeDesc;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import com.sun.speech.freetts.audio.AudioPlayer;
import com.sun.speech.freetts.audio.SingleFileAudioPlayer;

@Service
public class VoiceService {
	
	public void setEnv() throws EngineException {
		System.setProperty( "freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
        Central.registerEngineCentral( "com.sun.speech.freetts.jsapi.FreeTTSEngineCentral");
        Synthesizer synthesizer  = Central.createSynthesizer(new SynthesizerModeDesc(Locale.US));
	}

    public byte[] textToSpeech(String nameToPrononuce) {
        byte[] byteArray = new byte[0];
        try {
        	setEnv();
            convertToWav(nameToPrononuce);
            byteArray = convertToBase64(nameToPrononuce);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return byteArray;
    }
    
    
    public File textToFile(String nameToPrononuce) throws Exception{
        try {
        	setEnv();
            convertToWav(nameToPrononuce);
            String fileName = nameToPrononuce.toLowerCase().replace(" ", "_");
            return new File(fileName+".wav");
        } catch (Exception e) {
            throw new Exception("Exception in textToFile:"+e.getMessage());
        }
    }

    public void convertToWav(String word) {
        VoiceManager voiceManager = VoiceManager.getInstance();
        Voice helloVoice = voiceManager.getVoice("kevin16");
        helloVoice.setDurationStretch((float)(2));
        helloVoice.setStyle("casual");
        helloVoice.allocate();
        String fileName = word.toLowerCase().replace(" ", "_");
        AudioPlayer audioPlayer = new SingleFileAudioPlayer(fileName, AudioFileFormat.Type.WAVE);
        helloVoice.setAudioPlayer(audioPlayer);
        helloVoice.speak(word);
        helloVoice.deallocate();
        audioPlayer.close();
    }

    public byte[] convertToBase64(String word) throws IOException {
    	String fileName = word.toLowerCase().replace(" ", "_");
        File file = new File(fileName+".wav");
        byte[] bytes = FileUtils.readFileToByteArray(file);
        return Base64.encodeBase64(bytes);
    }
}
