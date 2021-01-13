package it.unicam.cs.ids.papcteam.c3Rest.controller;


import it.unicam.cs.ids.papcteam.c3Rest.repository.ChiamataRepository;
import it.unicam.cs.ids.papcteam.c3Rest.entity.ChiamataEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chiamate")
@CrossOrigin(origins = "*")
public class ChiamataRestController {
    @Autowired
    private ChiamataRepository chiamataRepository;

    public ChiamataRestController() {
    }

    @GetMapping
    public List<ChiamataEntity> getChiamate(){
        return this.chiamataRepository.findAll();
    }

    @PostMapping
    public void addChiamata(@RequestBody ChiamataEntity chiamata){
        this.chiamataRepository.save(chiamata);
    }

    @GetMapping("/{id}")
    public ChiamataEntity getChiamataById(@PathVariable long id) {
        return this.chiamataRepository.findById(id).orElseThrow(NullPointerException::new);
    }
    @DeleteMapping("/{id}")
    public void deleteChiamataById(@PathVariable long id){
        this.chiamataRepository.deleteById(id);
    }
}
