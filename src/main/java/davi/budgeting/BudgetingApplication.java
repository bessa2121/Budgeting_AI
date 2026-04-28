package davi.budgeting;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.beans.BeanProperty;

@SpringBootApplication
public class BudgetingApplication {

    //Builder é injetado pela autoconfiguração do springboot
    //Com isso, criamos o chatClient
    @Bean
    ChatClient chatChatClient(ChatClient.Builder builder) {
        return builder.build();
    }

	public static void main(String[] args) {
		SpringApplication.run(BudgetingApplication.class, args);
	}

}
