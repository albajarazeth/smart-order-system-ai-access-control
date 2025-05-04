package com.smart_order_system.orders_system_be.AIService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class GeminiService {

    private static ChatClient client;
        
            public GeminiService(ChatClient.Builder builder) {
                GeminiService.client = builder.build();
        }
    
        public static String getResponse(String prompt) {
            return client
                .prompt(prompt)
                .call()
                .content();
    }
}
