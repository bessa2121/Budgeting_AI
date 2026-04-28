package davi.budgeting;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//Interface de mais alto nível do chatmodel
//Permite que tenha diferentes models configurados dentro do chatclient
//Mais abstrata (melhor usar ela do que o chatmodel)
@RestController
@RequestMapping("/api")
public class ChatClientController {

    //Springboot não injeta automaticamente chatClient
    //Logo se cria um bean em "BudgetingApplication.java"
    private final ChatClient chatClient;

    //Podia ter sido injetado aqui, dessa forma:
    //public ChatClientController(ChatClient.Builder builder) { this.chatClient = builder.build(); }
    public ChatClientController(ChatClient chatClient){
        this.chatClient = chatClient;
    }

    //Envia requisição
    //Exemplo: "Olá!" e ao bater no endpoint, chatclient retorna "Olá! Como posso ajudar?"
    @GetMapping("/chat")
    String chat(String prompt) {
        return this.chatClient.prompt().user(prompt).call().content();
    }
}
