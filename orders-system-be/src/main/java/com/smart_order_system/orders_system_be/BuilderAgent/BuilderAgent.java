package com.smart_order_system.orders_system_be.BuilderAgent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.smart_order_system.orders_system_be.AIService.GeminiService;
import com.smart_order_system.orders_system_be.Email.Email;
import com.smart_order_system.orders_system_be.EmailAgent.EmailAgent;

@Component
public class BuilderAgent {

    @Autowired
    private EmailAgent emailAgent;
    
    
    public void makeConfirmationEmail(Email email){

        String prompt="""
            Read the following email and create a order confirmation email that lists the parts, quantity of each part
            and total price. Also make a random date in past 5/4/2025 that the package should arrive. Make sure that
            the words "Order confirmation" are included at the top. Do not include ANYTHING besides the order confirmation
            omit any signs that you are an ai from the email just as if it was an automated order confirmation system
            also include a phone number of 555-555-5555 and that the customer can reach support at support@AirRulers.com
            """;
            
            String response=GeminiService.getResponse(prompt+email.getBody());
            System.out.println(response);

            Email newEmail = new Email();
            newEmail.setSubject(email.getUser().getId().toString());
            newEmail.setEmailAddress("BuilderAgent");
            newEmail.setBody(response);
            newEmail.setUser(email.getUser());
            newEmail.setisSentFromAgent(true);
            emailAgent.processEmail(newEmail);
    }
}
