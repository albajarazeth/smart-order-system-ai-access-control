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
    
        public void pickParts(String parts){
            String prompt="""
            You are an ai agent that picks parts for an online ecommerce drone parts store
            the following message is going to first consist of a list of parts that are currently 
            available within the store and then you will get a list of customer requested parts.
            You will determine if the parts available in store are enough to fufill the customer's
            order. If the exact part that they ordered is not available look for a substitution
            using the partType parameter, if the customer's requested part is similar to another
            part's partType and it is available use it instead.
            If some parts are missing but others are available to be fulfilled then specify which
            parts are not able to be fulfilled.
            After going through that logic you have four options:

            1. Make a quote for the customer. The message must start with "Please send the following
            quote to customer" and then proceed with listing the parts, quantity, and total price of the parts.
            You are to only choose this option if either all parts are available and no substitutions were made
            or only one or a few parts are availble but still no substitutions were made. Remember that the names
            of the parts in inventory and the name of requested parts have to be exactly the same if the names
            of the parts are not EXACTLY THE SAME you will have to make a substitution.

            2.Make a request to send the substitutions for approval to the supervisor. The message must start with
            "Please send the following quote to supervisor for substitution approval" and then proceed with listing
            the parts, quantity, and total price of the parts, and the substituted parts next to each line item.
            You are to only choose this option if there was a need to make any substitutions.

            3.Make a notice of inventory. The message will only state the amount of parts you have of that particular
            part type, do not make substitutions here only the name. You choose this option only when the parts list
            contains the word "inquiry"

            4.Make a no quote for the customer. The message must start with "Please send the following quote to
            customer" and Simply state that there are no parts available and thank them for their request.

            You are to only respond with the message from one of the options do not respond with any sort of "I understand..."
            message ONLY what was requested in any of the three options and nothing else. Make sure to add up the totals
            for all the parts you are quoting.
            """;
            ArrayList<Part> listOfParts=(ArrayList<Part>) partRepository.findAll();
            System.out.println("list of parts "+listOfParts);
            String internalParts="List of parts in the store:";
            for(int i=0;i<listOfParts.size();i++){
                internalParts+="Part name:"+listOfParts.get(i).getpartName();
                internalParts+="Part price:"+listOfParts.get(i).getprice();
                internalParts+="Part stock:"+listOfParts.get(i).getStock();
                internalParts+="Part type:"+listOfParts.get(i).getpartType();
                internalParts+="\n";
            }

            prompt+=internalParts+parts;

            String response=GeminiService.getResponse(prompt);

            System.out.println(response);

            Email newEmail= new Email();
            newEmail.setSubject("Message from part picker");
            newEmail.setEmailAddress("PartPickerAgent");
            newEmail.setBody(response);
            newEmail.setUser(user.findById(Long.parseLong("2")).orElseThrow());
            newEmail.setisSentFromAgent(true);

            emailAgent.processEmail(newEmail);



        
    }
}
