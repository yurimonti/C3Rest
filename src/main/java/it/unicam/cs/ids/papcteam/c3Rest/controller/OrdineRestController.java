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
import it.unicam.cs.ids.papcteam.c3Rest.util.ObjectStringer;
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

    @GetMapping("/{id}/informazioni")
    public String getInformazioni(@PathVariable long id){
        String dest="";
        OrdineEntity ordineEntity = getOrdineById(id);
        if(ordineEntity.getDestinazione()==null)dest="ritiro al Negozio: "+ordineEntity.getEmittente().getNome()
                +"; inidirizzo: "+ordineEntity.getEmittente().getIndirizzo()+"; ";
        else dest="ritiro al Locker: "+ordineEntity.getDestinazione().getNome()
                +"; inidirizzo: "+ordineEntity.getDestinazione().getIndirizzo()+"; ";

        ObjectStringer<OrdineEntity> stringer = o -> "id: "+o.getId()+"; numero: "+o.getNumeroOrdine()
                +"; prezzo: "+o.getSoldi()+"; ";
        ObjectStringer<ProdottoEntity> stringer1 = o->"codice : "+o.getSerialCode()+"; nome : "+o.getNome()+
                "; prezzo al pezzo: "+o.getPrezzo()+"; quantità: "+o.getNumero()+"; ";
        return stringer.objectToString(ordineEntity)+dest+" Prodotti: "+stringer1.objectToString(ordineEntity.getProdotti());
    }

}
