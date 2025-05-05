package com.smart_order_system.orders_system_be.PartPickerAgent;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.mapping.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.smart_order_system.orders_system_be.AIService.GeminiService;
import com.smart_order_system.orders_system_be.EmailAgent.EmailAgent;
import com.smart_order_system.orders_system_be.Parts.Part;
import com.smart_order_system.orders_system_be.Email.Email;
import com.smart_order_system.orders_system_be.Parts.PartController;
import com.smart_order_system.orders_system_be.Parts.PartRepository;
import com.smart_order_system.orders_system_be.User.UserRepository;

import org.springframework.context.annotation.Lazy;


@Service
public class PartPickerAgent {

    @Autowired
    private EmailAgent emailAgent;

    @Autowired
    private UserRepository user;

    @Autowired
    private PartRepository partRepository;
    
        public void pickParts(Email email){
            String prompt="""
            You are an AI agent for a drone parts e-commerce store. You will receive a message that first lists the available inventory, followed by a list of customer-requested parts.

            Your task is to compare the requested parts with the available inventory and select ONE of the following actions:

            1. Quote without substitutions  
            If all requested parts are available in inventory (exact name match), or only some are available and none require substitution, respond with:  
            "Please send the following quote to customer:"  
            Then list each part with its name, quantity, and total price. Price must always be included.

            2. Quote with substitutions  
            If any requested parts are not available by name, check for substitutions using the `partType` field. A part can be substituted if it
            has the same part type as a different part. If a substitution is available, respond with:  
            "Please send the following quote to supervisor for substitution approval:"
            Then list each part with the substituted part name, original part name, quantity, and total price. Always include which part was substituted.

            3. No quote possible  
            If no parts (or acceptable substitutions) are available, respond with:  
            "Please send the following quote to customer:"  
            Then state that no parts are available and thank them for their request.

            Your response must begin with the designated message for the selected option. Always include total prices where applicable. Do not explain your decision or include any extra text beyond the selected response format.
            """;

            ArrayList<Part> listOfParts=(ArrayList<Part>) partRepository.findAll();
            String internalParts="List of parts in the store:";
            for(int i=0;i<listOfParts.size();i++){
                internalParts+="Part name:"+listOfParts.get(i).getpartName();
                internalParts+="Part price:"+listOfParts.get(i).getprice();
                internalParts+="Part stock:"+listOfParts.get(i).getStock();
                internalParts+="Part type:"+listOfParts.get(i).getpartType();
                internalParts+="\n";
            }

            prompt+=internalParts+email.getBody();

            String response=GeminiService.getResponse(prompt);

            System.out.println(response);

            Email newEmail= new Email();
            newEmail.setSubject(email.getSubject());
            newEmail.setEmailAddress("PartPickerAgent");
            newEmail.setBody("Message from part picker"+response);
            newEmail.setUser(email.getUser());
            newEmail.setisSentFromAgent(true);
            emailAgent.processEmail(newEmail);



        
    }
}
