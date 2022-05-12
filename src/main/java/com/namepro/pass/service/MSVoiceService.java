package com.namepro.pass.service;

import java.io.File;
import java.util.concurrent.ExecutionException;
import com.microsoft.cognitiveservices.speech.*;
import com.microsoft.cognitiveservices.speech.audio.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MSVoiceService {

    @Value("${azure.subscriptionKey}")
    private static String subscriptionKey;

    @Value("${azure.serviceRegion}")
    private static String serviceRegion;


    // Speech synthesis to wave file.
    public File generateWaveFile(String  txtToPronounce) throws InterruptedException, ExecutionException
    {
       //derive values from speech config
        SpeechConfig config = SpeechConfig.fromSubscription(subscriptionKey, serviceRegion);

        // Creates a speech synthesizer using file as audio output.
        String fileName =  txtToPronounce.toLowerCase().replace(" ", "_");
        AudioConfig fileOutput = AudioConfig.fromWavFileOutput(fileName);

        // Creates a speech synthesizer using a wave file as audio output.
        SpeechSynthesizer synthesizer = new SpeechSynthesizer(config, fileOutput);
        {
            System.out.println("Text to pronounce is " +  txtToPronounce);
                SpeechSynthesisResult result = synthesizer.SpeakTextAsync( txtToPronounce).get();

                // Checks result.
                if (result.getReason() == ResultReason.SynthesizingAudioCompleted) {
                    System.out.println("Speech synthesized for text [" +  txtToPronounce + "], and the audio was saved to [" + fileName + "]");
                }
                else if (result.getReason() == ResultReason.Canceled) {
                    SpeechSynthesisCancellationDetails cancellation = SpeechSynthesisCancellationDetails.fromResult(result);
                    System.out.println("CANCELED: Reason=" + cancellation.getReason());

                    if (cancellation.getReason() == CancellationReason.Error) {
                        System.out.println("CANCELED: ErrorCode=" + cancellation.getErrorCode());
                        System.out.println("CANCELED: ErrorDetails=" + cancellation.getErrorDetails());
                        System.out.println("CANCELED: Did you update the subscription info?");
                    }

                result.close();
            }
        }

        synthesizer.close();
        fileOutput.close();

        return new File(fileName+".wav");
    }

}
