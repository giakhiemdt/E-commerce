// import React, { useEffect, useState } from 'react';
// import { useNavigate } from 'react-router-dom';
// import { Client } from '@stomp/stompjs';

// const WebSocketComponent: React.FC = () => {
//     const [messages, setMessages] = useState<string[]>([]);
//     const [client, setClient] = useState<Client | null>(null);

//     useEffect(() => {
//         const stompClient = new Client({
//             brokerURL: 'ws://localhost:8080/websocket',
//             connectHeaders: {
//                 Authorization: `Bearer ${token}` // Nếu bạn dùng JWT hoặc các cơ chế xác thực khác
//             },
//             onConnect: () => {
//                 console.log('Connected to STOMP');
//                 stompClient.subscribe(`/user/${username}/queue/notifications`, (message) => {
//                     if (message.body) {
//                         setNotifications((prev) => [...prev, message.body]);
//                     }
//                 });
//             },
//         });
        

//         stompClient.activate();
//         setClient(stompClient);

//         return () => {
//             stompClient.deactivate();
//         };
//     }, []);
//     return null;
// };

// export default WebSocketComponent;

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
        console.log('Ditconme');
            const stompClient = new Client({
                brokerURL: 'ws://localhost:8080/websocket', // Đúng URL WebSocket backend
                connectHeaders: {
                    Authorization: `Bearer ${token}`,
                },
                onConnect: () => {
                    console.log('Connected to STOMP');
                    stompClient.subscribe(`/user/${username}/updateRole/`, (message) => {
                        console.log('Received: ' + message); // Thêm log này

                        if (message.body) {
                            console.log('Received message: ' + message.body); // Thêm log này
                            setNotifications((prev) => [...prev, message.body]);
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
            
    }, [token, username, notifications]); // Đưa notifications vào dependency để kiểm tra sự thay đổi

    return null
};

export default WebSocketComponent;
