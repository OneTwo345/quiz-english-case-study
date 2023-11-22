package com.example.minicase.repository;
import com.example.minicase.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import java.util.Optional;
public interface UserRepository  extends JpaRepository<User,Long> {
    Optional<User> findByUsernameIgnoreCaseOrEmailIgnoreCaseOrPhoneNumber(String username, String email, String phoneNumber);
    boolean existsByUsernameIgnoreCase(String username);
    boolean existsByEmailIgnoreCase(String email);
    boolean existsByPhoneNumber(String phoneNumber);
    User findByUsername(String username);
    @Query(value = "SELECT u from User as u where u.fullName LIKE %:search OR u.email LIKE %:search OR u.username LIKE %:search")
    Page<User> searchAllByUserName(@Param("search") String search, Pageable pageable);
    Optional<User> findUserByUsername(String username);
}