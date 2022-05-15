package com.namepro.pass.service;

import java.io.File;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import com.microsoft.cognitiveservices.speech.*;
import com.microsoft.cognitiveservices.speech.audio.AudioConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MSVoiceService {

    @Value("${azure.subscriptionKey}")
    private String subscriptionKey;

    @Value("${azure.subscriptionRegion}")
    private String serviceRegion;


    // Speech synthesis to wave file.
    public File generateWaveFile(String  txtToPronounce) throws InterruptedException, ExecutionException
    {
        SpeechConfig config = getSpeechConfig();

        // Creates a speech synthesizer using file as audio output.
        String fileName =  txtToPronounce.toLowerCase().replace(" ", "_");

        AudioConfig fileOutput = AudioConfig.fromWavFileOutput(fileName+".wav");

        // Creates a speech synthesizer using a wave file as audio output.
        SpeechSynthesizer synthesizer = new SpeechSynthesizer(config, fileOutput);
        {
            log.info("Text to pronounce is " +  txtToPronounce);
                SpeechSynthesisResult result = synthesizer.SpeakTextAsync( txtToPronounce).get();

                if (result.getReason() == ResultReason.SynthesizingAudioCompleted) {
                    log.info("Speech synthesized for text [" +  txtToPronounce + "], and the audio was saved to [" + fileName + "]");
                }
                else if (result.getReason() == ResultReason.Canceled) {
                    SpeechSynthesisCancellationDetails cancellation = SpeechSynthesisCancellationDetails.fromResult(result);
                    log.info("CANCELED: Reason=" + cancellation.getReason());

                    if (cancellation.getReason() == CancellationReason.Error) {
                        log.info("CANCELED: ErrorCode=" + cancellation.getErrorCode());
                        log.info("CANCELED: ErrorDetails=" + cancellation.getErrorDetails());
                        log.info("CANCELED: Did you update the subscription info?");
                    }
                result.close();
            }
        }
        synthesizer.close();
        fileOutput.close();
        return new File(fileName);
    }

    private SpeechConfig getSpeechConfig() {
        SpeechConfig config = SpeechConfig.fromSubscription(subscriptionKey, serviceRegion);
        return config;
    }


    public String speechToText() throws InterruptedException, ExecutionException {
        String text = "";
        SpeechConfig speechConfig = getSpeechConfig();
        AudioConfig audioConfig = AudioConfig.fromDefaultMicrophoneInput();
        SpeechRecognizer speechRecognizer = new SpeechRecognizer(speechConfig, audioConfig);

        log.info("Speak into your microphone.");
        Future<SpeechRecognitionResult> task = speechRecognizer.recognizeOnceAsync();
        SpeechRecognitionResult speechRecognitionResult = task.get();

        if (speechRecognitionResult.getReason() == ResultReason.RecognizedSpeech) {
            text = speechRecognitionResult.getText();
        }
        else if (speechRecognitionResult.getReason() == ResultReason.NoMatch) {
            log.info("NOMATCH: Speech could not be recognized.");
        }
        else if (speechRecognitionResult.getReason() == ResultReason.Canceled) {
            CancellationDetails cancellation = CancellationDetails.fromResult(speechRecognitionResult);
            log.info("CANCELED: Reason=" + cancellation.getReason());

            if (cancellation.getReason() == CancellationReason.Error) {
                log.info("CANCELED: ErrorCode=" + cancellation.getErrorCode());
                log.info("CANCELED: ErrorDetails=" + cancellation.getErrorDetails());
                log.info("CANCELED: Did you set the speech resource key and region values?");
            }
        }
        return text;
    }

}
