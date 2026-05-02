package davi.budgeting;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.springframework.ai.openai.OpenAiAudioSpeechModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.file.Files;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@EnabledIfEnvironmentVariable(named = "OPENAI_API_KEY", matches = ".+")
public class OpenAiSpeechModelIT {

    // Injeta automaticamente o modelo de transcrição configurado
    @Autowired
    OpenAiAudioSpeechModel openAiAudioSpeechModel;

    @Test
    public void should_produceAudio_when_textIsProvided() {
        var response = openAiAudioSpeechModel.call("O valor total do serviço ficou em 80 reais. Posso confirmar o pagamento?");

        assertThat(response).hasSizeGreaterThan(1024);

        var tempFile = Files.createTempFile("AUDIO_", ".mp3 ");
        Files.write(tempFile, response);
        System.out.println(tempFile.toAbsolutePath());
    }
}
