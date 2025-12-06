package com.ProgWebII.biotrack.repository; // Ajuste o pacote

import com.ProgWebII.biotrack.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  // Spring Data JPA gera a implementação automaticamente
  @Query("SELECT u FROM User u WHERE u.measures IS EMPTY")
  List<User> findUsersWithoutMeasures();

}