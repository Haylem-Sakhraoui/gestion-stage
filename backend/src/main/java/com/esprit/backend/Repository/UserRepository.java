package com.esprit.backend.Repository;

import com.esprit.backend.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String email);

    Optional<Object> findByFirstnameAndLastnameAndEmail(String firstname, String lastname, String email);

    @Query("SELECT COUNT(u) FROM User u WHERE u.enabled = true")
    int countActiveUsers();

}
