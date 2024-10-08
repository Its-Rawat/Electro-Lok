package com.rawat.electrolok.store.repositories;

import com.rawat.electrolok.store.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,String> {

    // find email
    Optional<User> findByEmail(String email);

    // find Email and Password
    Optional<User> findByEmailAndPassword(String email, String password);

    // SEARCH USER
    List<User> findByNameContaining(String keyword);


}
