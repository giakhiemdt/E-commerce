package com.example.backend.model.response.admin;

import java.sql.Timestamp;
import com.example.backend.entity.Role;
import lombok.Data;

@Data
// Không biết đặt tên gì nhưng thằng này là phản hồi lấy danh sách tài khoản của admin
// T đã loại bỏ những thông tin không cần thiết gửi đi như password vv...
public class AdminAccountResponse {
    private long id;
    private String username;
    private String email;
    private Role role;
    private Timestamp createdDate;
    private boolean isActive;

    public AdminAccountResponse(long id, String username, String email, Role role, Timestamp createdDate, boolean active) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
        this.createdDate = createdDate;
        this.isActive = active;
    }
}
