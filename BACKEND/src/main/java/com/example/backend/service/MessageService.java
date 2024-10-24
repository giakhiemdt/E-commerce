package com.example.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void sendUpdateRoleSellerMessage(String username) {
        System.out.println("Sending update role seller message to " + username);
        String message = "updateRoleSeller";
        messagingTemplate.convertAndSendToUser(username, "/updateRole", message);
    }

    public void sendSellerNeedInfoMessage(String username) {
        System.out.println("Sending seller need info message to " + username);
        String message = "sellerNeedInfo";
        messagingTemplate.convertAndSendToUser(username, "/sellerNeedInfo", message);
    }
}
