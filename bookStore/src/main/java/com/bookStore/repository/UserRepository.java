//package com.bookStore.repository;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import com.bookStore.entity.User;
//import java.util.Optional;
//
//public interface UserRepository extends JpaRepository<User, Long> {
//    Optional<User> findByEmail(String email);
//}
package com.bookStore.repository;

import com.bookStore.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);  // ✅ Add this line
}
