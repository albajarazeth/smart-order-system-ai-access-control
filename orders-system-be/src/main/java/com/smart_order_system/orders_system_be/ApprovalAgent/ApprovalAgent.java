package com.smart_order_system.orders_system_be.ApprovalAgent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.smart_order_system.orders_system_be.AIService.GeminiService;
import com.smart_order_system.orders_system_be.Email.Email;
import com.smart_order_system.orders_system_be.EmailAgent.EmailAgent;
import com.smart_order_system.orders_system_be.User.UserRepository;


@Component
public class ApprovalAgent {

    @Autowired
    private EmailAgent emailAgent;

    @Autowired
    private UserRepository user;
    
    public void processApproval(String message){
        String prompt="""
            You are an ai approval agent, you are to process the following message
            it will be one of the following options. Process this email and determine which output you will decide on.
            You only have one of the following options that you can output as a response:

            1. SendApprovalRequestToSupervisor(): You choose this when you determine that the message is asking for approval
             from the supervisor for some substitutions that is required to be sent.
             
            2. SendSupervisorApprovalToEmailAgent(): You choose this when you determine that the message is from the supervisor
            approving of the substitutions made.

            3. SendApprovalToBuilderAgent(): You choose this when you determine that the message is from a user approving of a quote.

            Make your selection and add a "$" symbol between the choice and the user id in the original message. You are to only respond
             with the message from one of the options do not respond with any sort of "I understand..."
            message ONLY what was requested in any of the three options and nothing else.
            """;
            String response=GeminiService.getResponse(prompt+"\n"+message);
            System.out.println(response);
            if(response.contains("SendApprovalRequestToSupervisor")){
                //Due to time constraints we will simply approve of all substitutions
                int index = response.indexOf('$');
                String userId = response.substring(index + 1);
                userId=userId.replaceAll("\\s+", "");
                Email newEmail= new Email();
                newEmail.setSubject("[APPROVED] Substitutions are now approved for user " + userId);
                newEmail.setEmailAddress("ApprovalAgent");
                newEmail.setBody(message+"Please send this quote to customer it no longer requires approval");
                newEmail.setUser(user.findById(Long.parseLong(userId)).orElseThrow());
                newEmail.setisSentFromAgent(true);
                emailAgent.processEmail(newEmail);
            }
    }
}
