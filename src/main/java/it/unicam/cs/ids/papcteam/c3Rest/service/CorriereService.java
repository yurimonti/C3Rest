package it.unicam.cs.ids.papcteam.c3Rest.service;

import it.unicam.cs.ids.papcteam.c3Rest.entity.CorriereEntity;
import it.unicam.cs.ids.papcteam.c3Rest.entity.OrdineEntity;
import it.unicam.cs.ids.papcteam.c3Rest.repository.CorriereRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class CorriereService {
    @Autowired
    private CorriereRepository corriereRepository;
    @Autowired
    private GestoreOrdini gestoreOrdini;

    public CorriereService() {
    }

    public CorriereEntity getCorriereById(long id){
        return this.corriereRepository.findById(id)
                .orElseThrow(() -> new NullPointerException("corriere con questo id inesistente"));
    }

    public List<CorriereEntity> getCorrieri(){
        return this.corriereRepository.findAll();
    }

    public void addCorriere(CorriereEntity corriere){
        this.corriereRepository.save(corriere);
    }

    public List<OrdineEntity> getOrdini(long id, Predicate<OrdineEntity> predicate){
        return getCorriereById(id).getOrdini().stream().filter(predicate).collect(Collectors.toList());
    }

}
