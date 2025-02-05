package com.project.spboard.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/auth")
    public String testPage(){
        return "authenticated";
    }
    @GetMapping("/deny")
    public String denyPage(){
        return "deny";
    }
    @GetMapping("/permit")
    public String permitPage(){
        return "permit";
    }
}
