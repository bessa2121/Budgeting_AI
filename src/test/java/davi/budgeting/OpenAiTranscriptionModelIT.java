package davi.budgeting;

import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.ai.openai.OpenAiAudioTranscriptionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@EnabledIfEnvironmentVariable(named = "OPENAI_API_KEY", matches = ".+")
public class OpenAiTranscriptionModelIT {

    // Injeta automaticamente o modelo de transcrição configurado
    @Autowired
    OpenAiAudioTranscriptionModel openAiTranscriptionModel;


    // Teste parametrizado → roda várias vezes com valores diferentes
    @ParameterizedTest
    @CsvSource({
            // Formato: "nome do arquivo, palavra/frase esperada na transcrição"
            "recording-1.m4a, 80 reais",
            "recording-2.m4a, 40 reais",
            "recording-3.m4a, 120 reais",
            "recording-4.m4a, 90 reais",
            "recording-5.m4a, 200 reais",
            "recording-6.m4a, 60 reais",
    })
    public void should_containExpectedKeywords_when_audioFilesAreProcessed(String fileName, String expectedKeyword) {

        // Carrega o arquivo de áudio da pasta:
        // src/main/resources/audio/
        var recording = new ClassPathResource("audio/" + fileName);


        // Envia o áudio para o modelo de transcrição
        // A IA converte fala → texto
        var response = openAiTranscriptionModel.call(recording);

        // Verifica se a transcrição contém o valor esperado
        // OBS:
        // Usa "contains" porque a IA pode retornar frases maiores
        // Ex: "O valor é 80 reais" ao invés de só "80 reais"
        assertThat(response).contains(expectedKeyword);

        // Exibe a transcrição no console (debug)
        System.out.println(response);
    }
}
