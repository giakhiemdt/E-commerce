import React, { useEffect, useState } from 'react';
import { Client } from '@stomp/stompjs';

const WebSocketComponent: React.FC = () => {
    const [notifications, setNotifications] = useState<string[]>([]);
    const [token, setToken] = useState<string | null>(null);
    const [username, setUsername] = useState<string>('');

    useEffect(() => {
        const storedToken = localStorage.getItem('token');
        const storedUsername = localStorage.getItem('username');

        if (storedToken) setToken(storedToken);
        if (storedUsername) setUsername(storedUsername);
    }, []);

    useEffect(() => {
        const username = sessionStorage.getItem("username");
        const stompClient = new Client({
            brokerURL: 'ws://localhost:8080/websocket', 
            connectHeaders: {
                AuthenticationToken: `Bearer ${token}`
            },
            debug: (str) => {
                console.log('Debug: ' + str);
            },
            onConnect: () => {
                console.log('Connected to STOMP');

                stompClient.subscribe('/user/message', (message) => {
                    console.log('Received message: ' + message.body);
                    
                });

                stompClient.subscribe(`/user/${username}/updateRole`, (message) => {
                    console.log('Received updateRole message: ' + message.body);
        
                    if (message.body) {
                        alert('Admin đã cấp quyền seller cho bạn, xin mời bạn đăng nhập lại và nhập thông tin cá nhân!');
                        sessionStorage.removeItem("token");
                        sessionStorage.removeItem("username");
                        window.location.href = '/login';
                    }
                });

                stompClient.subscribe(`/user/${username}/sellerNeedInfo`, (message) => {
                    console.log('Received sellerNeedInfo message: ' + message.body);

                    if (message.body) {
                        alert('Mời bạn nhập thông tin cá nhân theo chính sách bán hàng của web!')
                        sessionStorage.setItem("SellerInfoStatus", "Created");
                        window.location.href = '/account-profile'
                    }
                });
            },
            onStompError: (frame) => {
                console.error('Broker reported error: ' + frame.headers['message']);
                console.error('Additional details: ' + frame.body);
            },
            onWebSocketError: (error) => {
                console.error('WebSocket error:', error);
            },
        });
    
        stompClient.activate();
    
        return () => {
            if (stompClient) {
                stompClient.deactivate();
            }
        };
    }, [token, username, notifications]);
    
    

    return null
};

export default WebSocketComponent;
