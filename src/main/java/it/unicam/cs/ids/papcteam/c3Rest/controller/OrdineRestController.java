package it.unicam.cs.ids.papcteam.c3Rest.controller;

import it.unicam.cs.ids.papcteam.c3Rest.entity.LockerEntity;
import it.unicam.cs.ids.papcteam.c3Rest.entity.NegozioEntity;
import it.unicam.cs.ids.papcteam.c3Rest.entity.OrdineEntity;
import it.unicam.cs.ids.papcteam.c3Rest.entity.ProdottoEntity;
import it.unicam.cs.ids.papcteam.c3Rest.repository.LockerRepository;
import it.unicam.cs.ids.papcteam.c3Rest.repository.NegozioRepository;
import it.unicam.cs.ids.papcteam.c3Rest.repository.OrdineRepository;
import it.unicam.cs.ids.papcteam.c3Rest.service.OrdineService;
import it.unicam.cs.ids.papcteam.c3Rest.util.ConcreteCreatoreOrdine;
import it.unicam.cs.ids.papcteam.c3Rest.util.CreatoreOrdine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/ordini")
public class OrdineRestController {
    @Autowired
    private OrdineService ordineService;

    public OrdineRestController() {

    }

    @GetMapping("/{id}")
    public OrdineEntity getOrdineById(@PathVariable long id){
        return this.ordineService.getOrdineById(id);
    }

}
