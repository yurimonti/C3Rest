package it.unicam.cs.ids.papcteam.c3Rest.controller;

import it.unicam.cs.ids.papcteam.c3Rest.entity.LockerEntity;
import it.unicam.cs.ids.papcteam.c3Rest.repository.LockerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/lockers")
public class LockerRestController {
    @Autowired
    private LockerRepository lockerRepository;

    public LockerRestController() {
    }

    @GetMapping
    public List<LockerEntity> getLockers(){
        return this.lockerRepository.findAll();
    }
}
