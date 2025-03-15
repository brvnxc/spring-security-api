package brvnxc.com.git.spring_security_api.service;

import brvnxc.com.git.spring_security_api.exception.LoginFiledException;
import brvnxc.com.git.spring_security_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws LoginFiledException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new LoginFiledException("User not found"));
    }
}
