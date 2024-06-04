package ar.edu.unnoba.pdyc2024.mymusic.repository;

import ar.edu.unnoba.pdyc2024.mymusic.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findFirstByEmail(String email);
}
