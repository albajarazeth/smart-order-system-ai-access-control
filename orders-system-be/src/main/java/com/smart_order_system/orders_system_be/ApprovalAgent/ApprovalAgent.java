package com.smart_order_system.orders_system_be.ApprovalAgent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.smart_order_system.orders_system_be.AIService.GeminiService;
import com.smart_order_system.orders_system_be.BuilderAgent.BuilderAgent;
import com.smart_order_system.orders_system_be.Email.Email;
import com.smart_order_system.orders_system_be.EmailAgent.EmailAgent;
import com.smart_order_system.orders_system_be.User.UserRepository;


@Component
public class ApprovalAgent {

    @Autowired
    private EmailAgent emailAgent;

    @Autowired
    private UserRepository user;

    @Autowired
    private BuilderAgent builderAgent;
    
    public void processApproval(Email email){
        String prompt="""
            You are an AI Approval Agent for a drone parts e-commerce system. Based on the message provided, choose ONLY ONE of the following actions:

            1. SendApprovalRequestToSupervisor()
                - Use this when the message is requesting supervisor approval for substitutions.

            2. SendSupervisorApprovalToEmailAgent()
                - Use this when the message is from a supervisor approving the substitutions.

            3. SendApprovalToBuilderAgent()
                - Use this when the message contains anything about is from a customer approving a quote or if the subject says approval or similar.


            Respond with the function name
            
            - Do not include any explanations or extra text.
            - Do NOT return words like "part picker" as the user ID.
            """;


            String response=GeminiService.getResponse(prompt+email.getBody());
            System.out.println(response);
            if (response.contains("SendApprovalRequestToSupervisor")) {
                    String cleanedBody = email.getBody().replace("Please send the following quote to supervisor for substitution approval:", "").trim();
                    Email newEmail = new Email();
                    newEmail.setSubject(email.getSubject());
                    newEmail.setEmailAddress("ApprovalAgent");
                    newEmail.setBody(cleanedBody + "\n\nSubstitutions are now approved for user. Please send this quote to customer. It no longer requires approval.");
                    newEmail.setUser(email.getUser());
                    newEmail.setisSentFromAgent(true);
                    emailAgent.processEmail(newEmail);
            }
            else if (response.contains("SendApprovalToBuilderAgent")) {
                Email newEmail = new Email();
                newEmail.setSubject(email.getSubject());
                newEmail.setEmailAddress("ApprovalAgent");
                newEmail.setBody(email.getBody() + " quote is now approved from user. Please make a confirmation of order email");
                newEmail.setUser(email.getUser());
                newEmail.setisSentFromAgent(true);
                builderAgent.makeConfirmationEmail(newEmail);
        }

            
    }
}
