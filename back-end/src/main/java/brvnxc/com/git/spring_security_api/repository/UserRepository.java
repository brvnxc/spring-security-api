package brvnxc.com.git.spring_security_api.repository;

import brvnxc.com.git.spring_security_api.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
     Optional<User> findByUsername(String username);
}
