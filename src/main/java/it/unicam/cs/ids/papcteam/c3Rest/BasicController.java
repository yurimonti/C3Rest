package it.unicam.cs.ids.papcteam.c3Rest;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home")
public class BasicController {

    @GetMapping
    public String hello(){
        return "hello world!";
    }
}
