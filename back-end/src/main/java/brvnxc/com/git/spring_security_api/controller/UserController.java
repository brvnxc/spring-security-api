package brvnxc.com.git.spring_security_api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {

    @GetMapping()
    public String index() {
        return "teste OK index";
    }
    @PostMapping()
    public String edit() {
        return "teste OK edit";
    }
}
