package com.smart_order_system.orders_system_be.PartPickerAgent;


import com.smart_order_system.orders_system_be.Parts.PartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PartPickerAgent {

    private Map<String, Integer> requestedParts = new HashMap<>();

    @Autowired
    private PartRepository partRepository;

    public Map<String, Integer> getRequestedParts() {
        return requestedParts;
    }

    public void retrievePartsFromResponse(String response) {
        String partsString = response.substring("SendToPartPicker(String parts):".length()).trim();

        if (!partsString.isEmpty()) {
            String[] partDetails = partsString.split(",");
            for (String partDetail : partDetails) {
                String trimmedDetail = partDetail.trim();

                String[] partsInfo = trimmedDetail.split(" ");

                if (partsInfo.length >= 2) {
                    String partName = "";
                    int quantity = 0;

                    try {
                        quantity = Integer.parseInt(partsInfo[partsInfo.length - 1].trim());

                        StringBuilder nameBuilder = new StringBuilder();
                        for (int i = 0; i < partsInfo.length - 1; i++) {
                            if (!partsInfo[i].trim().isEmpty()) {
                                nameBuilder.append(partsInfo[i].trim()).append(" ");
                            }
                        }
                        partName = nameBuilder.toString().trim();

                        if (!partName.isEmpty()) {
                            requestedParts.put(partName, quantity);
                        } else {
                            System.err.println("Warning: Invalid part name for: " + trimmedDetail);
                        }
                    } catch (NumberFormatException e) {
                        System.err.println("Warning: Could not parse quantity for: " + trimmedDetail);
                    }
                } else {
                    System.err.println("Warning: Invalid part detail format: " + trimmedDetail);
                }
            }
        } else {
            System.out.println("Warning: Received response is empty or invalid.");
        }

        System.out.println("Parsed requested parts: " + requestedParts);
    }

}