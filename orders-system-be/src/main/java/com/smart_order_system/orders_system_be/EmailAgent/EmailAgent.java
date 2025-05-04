package com.smart_order_system.orders_system_be.EmailAgent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.smart_order_system.orders_system_be.AIService.GeminiService;
import com.smart_order_system.orders_system_be.ApprovalAgent.ApprovalAgent;
import com.smart_order_system.orders_system_be.Email.Email;
import com.smart_order_system.orders_system_be.PartPickerAgent.PartPickerAgent;
import com.smart_order_system.orders_system_be.User.UserRepository;

@Component
public class EmailAgent {

    @Autowired
    private PartPickerAgent partPickerAgent;

    @Autowired
    private ApprovalAgent approvalAgent;

    @Autowired
    private UserRepository user;

    public void processEmail(Email email){
        String subject=email.getSubject();
        String body=email.getBody();
        String message = "subject: "+subject+" body: "+body+""" 
        You are an AI agent that processes emails for an online E-commerce webiste that sells 
        drone parts. Process this email and determine which output you will decide on.
        You only have one of the following options that you can output as a response:
        
        1. SendToPartPicker(String parts): You choose this when you determine that the customer is requesting
        to purchase parts and you will extract the parts and the amount of each part that the customer wants. Also 
        if the subject is inquiry the customer only wants to know if the parts are in stock therefore Add the word
        "inquiry" to the end of this choice
        
        2. SendSubstitutionsToApprovalAgent(Email email): You choose this when you determine that the message is about the 
        part picker agent needing to make certain substitutions for certain parts.
        
        3. SendQuoteToCustomerFromApprovalAgent(Email email): You choose this when you determine that the message is coming from the approval
        agent wanting you to send the quote to the customer. If it contains the words Please send this quote to customer it no longer requires approval
        this is the option you should pick

        4. SendQuoteToCustomerFromPartPicker(Email email): You choose this when you determine that the message is coming from the picker
        agent wanting you to send the quote to the customer and it included no substitutions.

        5. SendCustomerApprovalOfQuoteToApprovalAgent(Email email): You choose this when you determine that the message is 
        about the customer approving the quote that was given to them.

        6. SendOrderConfirmationToCustomer(Email email): You choose this when you determine that the message is about the 
        BuilderAgent wanting you to send the order confirmation to the customer.

        7. SendCustomerServiceRequest(): You choose this when the message does not fit any of the other options and 
        human intervention is required.

        Only respond with the name of the function and if your choice is the 1st option respond with the name of the function
        followed by a dollar sign ($) and then a list of the parts and quantities seperated by commas like so: Motor 3, Propeller 1,
        Controller 5. Do not respond with any sort of "I understand..." statements or anything of the sort ONLY the name of the function
        and in the case of the first one also provide what i asked for
                """;
        if (subject.contains("[APPROVED]")) {
            System.out.println("Skipping already approved email");
            return;
        }
        else if(subject.contains("[REQUEST]")){
            System.out.println("Skipping awaiting for response email");
            return;
        }
        String response=GeminiService.getResponse(message);
        System.out.println(response);
        if(response.contains("SendToPartPicker")){
            int index = response.indexOf('$');
            response = response.substring(index + 1);
            partPickerAgent.pickParts(response);
        }
        else if(response.contains("SendSubstitutionsToApprovalAgent")){
            approvalAgent.processApproval(email.getBody()+" Please send this substitution request to the supervisor, the id of the user is "+email.getUser().getId());
        }
        else if(response.contains("SendQuoteToCustomerFromApprovalAgent")){
            String id=GeminiService.getResponse(subject+" what is the user id? respond with ONLY the user id and nothing else do not say any \"i understand...\" message ONLY the id");
            id=id.replaceAll("\\s+", "");
            response=GeminiService.getResponse(body+" Clean this email up so that it only includes the necessary information for the user to read as a quote and nothing else, return ONLY THE QUOTE INFORMATION DO NOT SAY ANY SORT OF \"i understand...\" message");
            Email newEmail = new Email();
            newEmail.setBody(response);
            newEmail.setEmailAddress("Email Agent");
            newEmail.setSubject("We are offering the following quote, please advise for approval, [REQUEST]");
            newEmail.setUser(user.findById(Long.parseLong(id)).orElseThrow());
            newEmail.setisSentFromAgent(true);
            WebClient.Builder builder= WebClient.builder();
            builder.build()
                .post()
                .uri("http://localhost:8080/email?userId="+id)
                .bodyValue(newEmail)
                .retrieve()
                .bodyToMono(Email.class)
                .block();
        }
        

    }
} 


