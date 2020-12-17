package it.unicam.cs.ids.papcteam.c3Rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chiamate")
@CrossOrigin(origins = "*")
public class ChiamataRestController {
    @Autowired
    private ChiamataRepository chiamataRepository;

    public ChiamataRestController() {
    }
}
