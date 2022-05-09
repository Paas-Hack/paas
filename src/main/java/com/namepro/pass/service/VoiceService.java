package com.namepro.pass.service;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import com.sun.speech.freetts.audio.AudioPlayer;
import com.sun.speech.freetts.audio.SingleFileAudioPlayer;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import javax.sound.sampled.AudioFileFormat;
import javax.speech.Central;
import javax.speech.synthesis.Synthesizer;
import javax.speech.synthesis.SynthesizerModeDesc;
import java.io.File;
import java.io.IOException;
import java.util.Locale;

@Service
public class VoiceService {

    public byte[] textToSpeech(String nameToPrononuce) {
        byte[] byteArray = new byte[0];
        try {
            System.setProperty(
                    "freetts.voices",
                    "com.sun.speech.freetts.en.us"
                            + ".cmu_us_kal.KevinVoiceDirectory");
            Central.registerEngineCentral(
                    "com.sun.speech.freetts"
                            + ".jsapi.FreeTTSEngineCentral");

            Synthesizer synthesizer
                    = Central.createSynthesizer(
                    new SynthesizerModeDesc(Locale.US));

//            synthesizer.allocate();
//            synthesizer.resume();
//            synthesizer.speakPlainText(nameToPrononuce, null);
//            synthesizer.waitEngineState(
//                    Synthesizer.QUEUE_EMPTY);
//
//            // Deallocate the Synthesizer.
//            synthesizer.deallocate();
            convertToWav(nameToPrononuce);
            byteArray = convertToBase64();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return byteArray;
    }

    public void convertToWav(String word) {
        VoiceManager voiceManager = VoiceManager.getInstance();
        Voice helloVoice = voiceManager.getVoice("kevin16");
        helloVoice.setDurationStretch((float)(2));
        helloVoice.setStyle("casual");
        helloVoice.allocate();
        AudioPlayer audioPlayer = new SingleFileAudioPlayer("output", AudioFileFormat.Type.WAVE);
        helloVoice.setAudioPlayer(audioPlayer);
        helloVoice.speak(word);
        helloVoice.deallocate();
        audioPlayer.close();
    }

    public byte[] convertToBase64() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File("output.wav");
        byte[] bytes = FileUtils.readFileToByteArray(file);
        return Base64.encodeBase64(bytes);
    }
}
