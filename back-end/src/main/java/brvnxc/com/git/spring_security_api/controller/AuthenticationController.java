package brvnxc.com.git.spring_security_api.controller;

import brvnxc.com.git.spring_security_api.domain.user.AuthenticationRequestDTO;
import brvnxc.com.git.spring_security_api.domain.user.AuthenticationResponseDTO;
import brvnxc.com.git.spring_security_api.domain.user.RegisterDTO;
import brvnxc.com.git.spring_security_api.domain.user.User;
import brvnxc.com.git.spring_security_api.exception.LoginFiledException;
import brvnxc.com.git.spring_security_api.repository.UserRepository;
import brvnxc.com.git.spring_security_api.service.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository repository;
    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationRequestDTO data) {
        try {
            var usernamePassword = new UsernamePasswordAuthenticationToken(data.username(), data.password());

            var auth = this.authenticationManager.authenticate(usernamePassword);

            var token = tokenService.generateToken((User) auth.getPrincipal());

            return ResponseEntity.ok(new AuthenticationResponseDTO(token));
        } catch (AuthenticationException e) {
            throw new LoginFiledException("login or password not recognized");
        }
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO data){
        if(!this.repository.findByUsername(data.username()).isEmpty())
            throw new LoginFiledException("login or password not recognized");

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());

        User newUser = new User(data.username(), data.email(), encryptedPassword, data.role());

        User userSaved = this.repository.save(newUser);

        return ResponseEntity.ok().build();
    }
}
