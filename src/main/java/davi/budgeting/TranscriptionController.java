package davi.budgeting;

import org.springframework.ai.audio.transcription.TranscriptionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class TranscriptionController {

    // Interface de transcrição (alto nível)
    // O Spring injeta automaticamente a implementação configurada (ex: OpenAI)
    @Autowired
    private final TranscriptionModel transcriptionModel;

    // Injeção via construtor (boa prática)
    // Garante que o objeto sempre será criado com a dependência obrigatória
    public TranscriptionController(TranscriptionModel transcriptionModel) {
        this.transcriptionModel = transcriptionModel;
    }

    // Endpoint POST: /api/transcribe
    // Aceita requisições com arquivo (multipart/form-data)
    @PostMapping(value = "/transcribe", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    String transcribe(@RequestParam("file") MultipartFile file) {

        // Converte o arquivo enviado em um Resource (formato que o Spring AI entende)
        var resource = file.getResource();

        // Envia o áudio para o modelo de transcrição
        // A IA converte fala → texto
        return transcriptionModel.transcribe(resource);
    }
}
