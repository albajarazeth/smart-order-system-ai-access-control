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
        String message = "subject: "+subject+" body: "+body+" is sent from the ai agent?: "+email.getisSentFromAgent().toString()+""" 
        You are an AI agent that processes customer emails for an online drone parts store.

        You must choose ONLY ONE of the following actions, based on the email's content:

        1. SendToPartPicker(String parts)
            - Use this when a customer is requesting to buy parts.
            - Extract part names and quantities. Format like: `Motor 3, Propeller 1`.
            - only treat the message as an inquiry if the subject line contains the word "inquiry" (case-insensitive).
                Do not assume it is an inquiry based on the way the message is phrased.
                If the subject does not contain the word "inquiry", treat the message as a purchase request.

        2. SendSubstitutionsToApprovalAgent(Email email)
            - Use this when substitutions are proposed and need supervisor approval.

        3. SendQuoteToCustomerFromApprovalAgent(Email email)
            - Use this if the approval agent asks to send a finalized quote to the customer.
            - If the body contains the text "Please send this quote to customer. It no longer requires approval." select this option

        4. SendQuoteToCustomerFromPartPicker(Email email)
            - Use this if the part picker sends a quote directly to the customer with NO substitutions.

        5. SendCustomerApprovalOfQuoteToApprovalAgent(Email email)
            - Use this if the customer confirms approval of a quote.
            - This must be a candidate if "is sent from the ai agent?" is false 
            - Can only be a choice if is sent from the ai agent?: is false

        6. SendOrderConfirmationToCustomer(Email email)
            - Use this when the BuilderAgent wants to confirm an order to the customer.

        7. SendCustomerServiceRequest()
            - Only selectable if "is sent from the ai agent?" is false otherwise it is NOT an option
            - Use this if the message doesn’t match any other category and needs a human.
            - Select this one if the customer does not explicitly state that they want to purchase something

        ---
        Respond with ONLY the function name.
        - If choosing option 1, respond like:  
        `SendToPartPicker(String parts)$Motor 3, Propeller 2`
        - Do NOT include explanations or extra text. 
                """;
        if (subject.contains("[APPROVED]") && email.getisSentFromAgent()) {
            System.out.println("Skipping already approved email from agent");
            return;
        }
        else if(subject.contains("[REQUEST]") && email.getisSentFromAgent()){
            System.out.println("Skipping request email sent by agent");
            return;
        }

        String response=GeminiService.getResponse(message+email.getUser().getId().toString());
        System.out.println(response);

        if(response.contains("SendToPartPicker")){
            int index = response.indexOf('$');
            response = response.substring(index + 1);
            Email newEmail = new Email();
            newEmail.setBody(response);
            newEmail.setEmailAddress("Email Agent");
            newEmail.setSubject(email.getUser().getId().toString());
            newEmail.setUser(user.findById(Long.parseLong("2")).orElseThrow());
            newEmail.setisSentFromAgent(true);
            partPickerAgent.pickParts(newEmail);
        }

        else if(response.contains("SendSubstitutionsToApprovalAgent")){
            Email newEmail = new Email();
            System.out.println("THIS IS THE EMAIL BODY INSIDE OF HERE "+ email.getBody());
            newEmail.setBody(email.getBody()+" Please send this substitution request to the supervisor");
            newEmail.setEmailAddress("Email Agent");
            newEmail.setSubject(email.getSubject());
            newEmail.setUser(user.findById(Long.parseLong("2")).orElseThrow());
            newEmail.setisSentFromAgent(true);
            approvalAgent.processApproval(newEmail);
        }

        else if(response.contains("SendCustomerServiceRequest")){
            String id=email.getUser().getId().toString();
            Email newEmail = new Email();
            newEmail.setBody("I cannot answer that question at this time please contact customer support at 555-555-5555 or email at support@AirRuler.com");
            newEmail.setEmailAddress("Email Agent");
            newEmail.setSubject("Customer Support");
            newEmail.setUser(email.getUser());
            newEmail.setisSentFromAgent(true);
            System.out.println("THIS IS THE NEW EMAIL "+newEmail);
            WebClient.Builder builder= WebClient.builder();
            builder.build()
                .post()
                .uri("http://localhost:8080/email?userId="+id)
                .bodyValue(newEmail)
                .retrieve()
                .bodyToMono(Email.class)
                .block();
        }
        
        else if(response.contains("SendQuoteToCustomerFromApprovalAgent")||response.contains("SendQuoteToCustomerFromPartPicker")){
            String id=email.getSubject();
            response=GeminiService.getResponse(body+" Clean this email up so that it only includes the necessary information for the user to read as a quote and nothing else, return ONLY THE QUOTE INFORMATION DO NOT SAY ANY SORT OF \"i understand...\" message");
            String cleanedBody = response
            .replace("Please send the following quote to supervisor for substitution approval:", "")
            .replaceAll("Substitutions are now approved.*", "")
            .trim();
            Email newEmail = new Email();
            newEmail.setBody(cleanedBody);
            newEmail.setEmailAddress("Email Agent");
            newEmail.setSubject("We are offering the following quote, please advise for approval, [REQUEST]");
            newEmail.setUser(email.getUser());
            newEmail.setisSentFromAgent(true);
            System.out.println("THIS IS THE NEW EMAIL "+newEmail);
            WebClient.Builder builder= WebClient.builder();
            builder.build()
                .post()
                .uri("http://localhost:8080/email?userId="+id)
                .bodyValue(newEmail)
                .retrieve()
                .bodyToMono(Email.class)
                .block();
            
        }

        else if(response.contains("SendCustomerApprovalOfQuoteToApprovalAgent")){
            String id=email.getUser().getId().toString();
            Email newEmail = new Email();
            newEmail.setBody("Subject: "+email.getSubject()+" Body: "+email.getBody());
            newEmail.setEmailAddress("Email Agent");
            newEmail.setSubject(id);
            newEmail.setUser(email.getUser());
            newEmail.setisSentFromAgent(true);
            approvalAgent.processApproval(newEmail);
        }

        else if(response.contains("SendOrderConfirmationToCustomer")){
            System.out.println("IT ENTERED IN HERE ITS IN HERE RIGHT NOW");
            String id=email.getSubject();
            Email newEmail = new Email();
            newEmail.setBody(email.getBody());
            newEmail.setEmailAddress("Email Agent");
            newEmail.setSubject("Order Confirmation [APPROVED]");
            newEmail.setUser(email.getUser());
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


