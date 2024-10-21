package com.example.backend.repository;

import com.example.backend.entity.Account;
import com.example.backend.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {


    Optional<Account> findById(Long id);
    Optional<Account> findByUsername(String username);
    Optional<Account> findByEmail(String email);

    List<Account> findAll();

    List<Account> findByRole(Role role);

    @Modifying
    @Query("UPDATE Account a SET a.isActive = :isActive WHERE a.id = :accountId")
    void updateAccountStatusByAccountId(@Param("accountId") Long accountId, @Param("isActive") boolean isActive);

    @Modifying
    @Query("UPDATE Account a SET a.username = :userName WHERE a.id = :accountId")
    void updateAccountUsernameByAccountId(@Param("accountId") Long accountId, @Param("userName") String userName);

    @Modifying
    @Query("UPDATE Account a SET a.email = :email WHERE a.id = :accountId")
    void updateAccountEmailByAccountId(@Param("accountId") Long accountId, @Param("email") String email);

    @Modifying
    @Query("UPDATE Account a SET a.role = :role WHERE a.id = :accountId")
    void updateAccountRoleByAccountId(@Param("accountId") Long accountId, @Param("role") Role role);

}
