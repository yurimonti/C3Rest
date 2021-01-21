package it.unicam.cs.ids.papcteam.c3Rest.service;

import it.unicam.cs.ids.papcteam.c3Rest.entity.*;
import it.unicam.cs.ids.papcteam.c3Rest.repository.ChiamataRepository;
import it.unicam.cs.ids.papcteam.c3Rest.repository.CommercianteRepository;
import it.unicam.cs.ids.papcteam.c3Rest.repository.NegozioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommercianteService {
    @Autowired
    private CommercianteRepository commercianteRepository;
    @Autowired
    private NegozioRepository negozioRepository;
    @Autowired
    private ChiamataRepository chiamataRepository;

    public CommercianteEntity getCommercianteById(long id){
        if(this.commercianteRepository.findAll().stream().noneMatch(commercianteEntity -> commercianteEntity.getId()==id))throw
                new NullPointerException("nessun Commerciante con questo Id");
        else
            return this.commercianteRepository.getOne(id);
    }

    public void addNegozio(long id,String name,String descrizione,String indirizzo,String orario){
        CommercianteEntity commercianteEntity = getCommercianteById(id);
        NegozioEntity negozioEntity = new NegozioEntity(name,descrizione,indirizzo,orario);
        negozioEntity.setId(commercianteEntity.getId());
        this.negozioRepository.save(negozioEntity);
    }

    public void setNegozioAssociato(long id){
        CommercianteEntity commerciante = getCommercianteById(id);
        commerciante.setNegozio(this.negozioRepository.getOne(id));
        this.commercianteRepository.save(commerciante);
    }

    public List<OrdineEntity> getOrdiniCommerciante(long id){
        return getCommercianteById(id).getNegozio().getOrdini();
    }

    public void effettuaChiamata(long id,long idOrdine){
        CommercianteEntity commerciante = getCommercianteById(id);
        OrdineEntity ordine = getOrdiniCommerciante(id).stream().filter(o -> o.getId()==idOrdine).findFirst().orElseThrow(NullPointerException::new);
        ChiamataEntity chiamata = new ChiamataEntity();
        chiamata.setNegozio(commerciante.getNegozio());
        chiamata.setOrdine(ordine);
        this.chiamataRepository.save(chiamata);
    }
}