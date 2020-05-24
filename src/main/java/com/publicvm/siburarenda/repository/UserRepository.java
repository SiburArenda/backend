package com.publicvm.siburarenda.repository;

import com.publicvm.siburarenda.model.Event;
import com.publicvm.siburarenda.model.Status;
import com.publicvm.siburarenda.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * Repository interface that extends {@link JpaRepository} for class {@link User}.
 *
 * @author Valera
 * @version 1.0
 */

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String name);
    User findByEmail(String email);

    @Modifying
    @Query("update User u set u.username = ?1, u.status = ?2, u.company = ?3, u.email = ?4," +
            " u.firstName = ?5, u.lastName = ?6 where u.id = ?7")
    void setUserInfoById(String username, Status status, String company, String email,
                         String firstName, String lastName, Long id);
}
