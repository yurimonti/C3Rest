package it.unicam.cs.ids.papcteam.c3Rest.service;

import it.unicam.cs.ids.papcteam.c3Rest.entity.NegozioEntity;
import it.unicam.cs.ids.papcteam.c3Rest.repository.NegozioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GestoreNegozi {
    @Autowired
    private NegozioRepository negozioRepository;

    public GestoreNegozi() {
    }

    public NegozioEntity getNegozioById(long id){
        return this.negozioRepository.findAll().stream().filter(n -> n.getId()==id).findFirst()
                .orElseThrow(()->new NullPointerException("negozio con questo id inesistente"));
    }

    public List<NegozioEntity> getNegozi(){
        return this.negozioRepository.findAll();
    }

    public void addOrUpdateNegozio(NegozioEntity negozio){
        this.negozioRepository.save(negozio);
    }


}
