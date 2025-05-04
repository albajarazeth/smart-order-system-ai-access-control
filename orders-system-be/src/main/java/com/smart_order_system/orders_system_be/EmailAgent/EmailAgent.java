package com.smart_order_system.orders_system_be.EmailAgent;

import com.smart_order_system.orders_system_be.AIService.GeminiService;
import com.smart_order_system.orders_system_be.Email.Email;
import com.smart_order_system.orders_system_be.PartPickerAgent.PartPickerAgent;

public class EmailAgent {

    public static void processEmail(Email email){
        String subject=email.getSubject();
        String body=email.getBody();
        String message = "subject: "+subject+" body: "+body+""" 
        You are an AI agent that processes emails for an online E-commerce webiste that sells 
        drone parts. Process this email and determine which output you will decide on.
        You only have one of the following options that you can output as a response:
        
        1. SendToPartPicker(String parts): You choose this when you determine that the customer is requesting
        to purchase parts and you will extract the parts and the amount of each part that the customer wants.
        
        2. SendSubstitutionsToApprovalAgent(Email email): You choose this when you determine that the message is about the 
        part picker agent needing to make certain substitutions for certain parts.
        
        3. SendQuoteToCustomer(Email email): You choose this when you determine that the message is coming from the approval
        agent wanting you to send the quote to the customer.

        4. SendCustomerApprovalOfQuoteToApprovalAgent(Email email): You choose this when you determine that the message is 
        about the customer approving the quote that was given to them.

        5. SendOrderConfirmationToCustomer(Email email): You choose this when you determine that the message is about the 
        BuilderAgent wanting you to send the order confirmation to the customer.

        6. SendCustomerServiceRequest(): You choose this when the message does not fit any of the other options and 
        human intervention is required.

        Only respond with the name of the function and if your choice is the 1st option respond with the name of the function
        followed by a semicolon and then a list of the parts and quantities seperated by commas like so: Motor 3, Propeller 1,
        Controller 5. Do not respond with any sort of "I understand..." statements or anything of the sort ONLY the name of the function
        and in the case of the first one also provide what i asked for
                """;
        String response = GeminiService.getResponse(message);

        if (response.startsWith("SendToPartPicker")) {
            PartPickerAgent partPickerAgent = new PartPickerAgent();
            partPickerAgent.retrievePartsFromResponse(response);
        }
        System.out.println(response);
    }
    


}
