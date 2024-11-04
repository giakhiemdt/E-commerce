package com.example.backend.service;

import com.example.backend.entity.Account;
import com.example.backend.entity.Users;
import com.example.backend.model.request.frontend.account.UpdateAccountProfileRequest;
import com.example.backend.model.response.account.AccountProfileResponse;
import com.example.backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser(Account account) {
        userRepository.save(new Users(account));
    }

    @Transactional
    public void updateUsersFullName(String fullName, Users users) {
        userRepository.updateUsersFullNameByUser(fullName, users);
    }

    @Transactional
    public void updateUsersPhone(String phone, Users users) {
        userRepository.updateUsersPhoneByUser(phone, users);
    }

    @Transactional
    public void updateUsersAddress(String address, Users users) {
        userRepository.updateUsersAddressByUser(address, users);
    }

    // Lấy bờ rồ file nữa nè --- mệt vl!!
    public AccountProfileResponse getUserProfile(Account account) {
        return new AccountProfileResponse(
                account.getUsers().getFullname(),
                account.getUsers().getPhone(),
                account.getUsers().getAddress()
        );
    }

    //Cập nhật thông tin mới cho profile nè!
    @Transactional
    public void updateUserProfile(UpdateAccountProfileRequest request, Account account) {
        Users user = account.getUsers();
        if (request.getFullName() != null) {
            updateUsersFullName(request.getFullName(), user);
        }
        if (request.getPhone() != null) {
            updateUsersPhone(request.getPhone(), user);
        }
        if (request.getAddress() != null) {
            updateUsersAddress(request.getAddress(), user);
        }
    }
}
