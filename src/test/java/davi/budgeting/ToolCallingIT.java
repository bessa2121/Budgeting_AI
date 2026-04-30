package davi.budgeting;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@EnabledIfEnvironmentVariable(named = "OPENAI_API_KEY", matches = ".+")
public class ToolCallingIT {

    // Injeta automaticamente o modelo da OpenAI configurado na aplicação
    @Autowired
    OpenAiChatModel openAiChatModel;

    // Classe que define "ferramentas" que a IA pode usar
    // (como se fossem funções externas que o modelo pode chamar)
    static class MathTools {

        // Define uma ferramenta de soma disponível para o modelo
        @Tool(description = "Soma dois números inteiros, a e b")
        public int sum(int a, int b) {
            return a + b;
        }

        // Define uma ferramenta de subtração disponível para o modelo
        @Tool(description = "Subtrai dois números inteiros, a e b")
        public int diff(int a, int b) {
            return a - b;
        }
    }

    @Test
    void should_executeSum_when_prompted() {

        // Cria um ChatClient baseado no modelo OpenAI
       var chatClient = ChatClient.builder(openAiChatModel)

               // Define o comportamento base da IA (prompt de sistema)
               // Aqui: ela deve agir como um matemático
               .defaultSystem("Voce é um matemático")

               // Registra as ferramentas (funções) que a IA pode usar
               // Ela vai passar nas funções antes de ir para LLM
               .defaultTools(new MathTools())

               // Constrói o cliente
               .build();

        // Envia um prompt para a IA
        // IMPORTANTE:
        // A IA pode decidir usar automaticamente as ferramentas definidas
       var response = chatClient.prompt("Soma 10 mais 20. Depois subtraia 30 do resultado anterior. Exiba apenas o resultado final sem explicações")

               // Executa a chamada para o modelo
               .call()

               // Pega apenas o conteúdo textual da resposta
               .content();

        // Verifica se o resultado contém "0" ao invés de confirmar que é "0" (a IA pode retornar "Sua resposta é 0")
        // (10 + 20 = 30, 30 - 30 = 0)
       assertThat(response).contains("0");

        // Exibe o resultado no console (debug)
       System.out.println(response);
    }

}
