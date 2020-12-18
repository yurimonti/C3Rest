package it.unicam.cs.ids.papcteam.c3Rest;


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
    public List<Chiamata> getChiamate(){
        return this.chiamataRepository.findAll();
    }

    @PostMapping
    public void addChiamata(@RequestBody Chiamata chiamata){
        this.chiamataRepository.save(chiamata);
    }

    @GetMapping("/{id}")
    public Chiamata getChiamataById(@PathVariable long id) {
        return this.chiamataRepository.findById(id).orElseThrow(NullPointerException::new);
    }
    @DeleteMapping("/{id}")
    public void deleteChiamataById(@PathVariable long id){
        this.chiamataRepository.deleteById(id);
    }
}
