package davi.budgeting;

import org.springframework.ai.audio.tts.TextToSpeechModel;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class TextToSpeechController {
    private final TextToSpeechModel textToSpeechModel;

    public TextToSpeechController(TextToSpeechModel textToSpeechModel) {
        this.textToSpeechModel = textToSpeechModel;
    }

    @PostMapping(value = "/sinthesize", produces = "audio/mp3")
    public void sinthesize(@RequestBody SinthesizeRequest request) {
        byte[] audio = textToSpeechModel.call(request.text());
        var resource = new ByteArrayResource(audio);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        ContentDisposition.attachment()
                                .filename("audio.mp3")
                                .build()
                                .toString())
                .body(resource);
    }

    record SinthesizeRequest(String text) {

    }
}
