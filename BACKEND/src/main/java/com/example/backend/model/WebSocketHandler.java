package com.example.backend.model;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.List;

@Component
public class WebSocketHandler extends TextWebSocketHandler {
    // Danh sách các session đang kết nối
    private static final List<WebSocketSession> sessions = new ArrayList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // Thêm session mới vào danh sách
        sessions.add(session);
        System.out.println("New session connected: " + session.getId());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // Xóa session khi kết nối bị đóng
        sessions.remove(session);
        System.out.println("Session disconnected: " + session.getId());
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // Xử lý tin nhắn từ client
        String receivedMessage = message.getPayload();
        System.out.println("Received message: " + receivedMessage);

        // Gửi lại tin nhắn cho client
        session.sendMessage(new TextMessage("Hello from backend!"));

        // Gửi tin nhắn đến tất cả các client đang kết nối
        sendMessageToAll("Broadcast message: " + receivedMessage);
    }

    // Phương thức gửi tin nhắn đến tất cả các client
    public void sendMessageToAll(String message) throws Exception {
        for (WebSocketSession session : sessions) {
            if (session.isOpen()) {
                session.sendMessage(new TextMessage(message));
            }
        }
    }
}
