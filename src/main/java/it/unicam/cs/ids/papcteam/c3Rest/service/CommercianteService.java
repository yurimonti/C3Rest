package it.unicam.cs.ids.papcteam.c3Rest.service;

import it.unicam.cs.ids.papcteam.c3Rest.entity.*;
import it.unicam.cs.ids.papcteam.c3Rest.repository.ChiamataRepository;
import it.unicam.cs.ids.papcteam.c3Rest.repository.CommercianteRepository;
import it.unicam.cs.ids.papcteam.c3Rest.repository.NegozioRepository;
import it.unicam.cs.ids.papcteam.c3Rest.repository.OrdineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class CommercianteService {
    @Autowired
    private CommercianteRepository commercianteRepository;
    @Autowired
    private GestoreOrdini gestoreOrdini;
    @Autowired
    private GestoreChiamate gestoreChiamate;

    public CommercianteEntity getCommercianteById(long id){
        if(this.commercianteRepository.findAll().stream().noneMatch(commercianteEntity -> commercianteEntity.getId()==id))throw
                new NullPointerException("nessun Commerciante con questo Id");
        else
            return this.commercianteRepository.getOne(id);
    }

    public ProdottoEntity modificaNumeroProdotto(long idCommerciante,long idProdotto,int numero,boolean aggiunta){
        Predicate<ProdottoEntity> predicate = p ->p.getId()==idProdotto;
        ProdottoEntity p = getProdottiNegozio(idCommerciante).stream().filter(predicate).findFirst()
                .orElseThrow(()->new NullPointerException("prodotto con questo Id inesistente"));
        if (aggiunta) p.setNumero(p.getNumero()+numero);
        else {
            if(p.getNumero()<=numero)p.setNumero(0);
            else p.setNumero(p.getNumero()-numero);
        }
        return p;
    }

    public void deleteProdotto(long idCommerciante,long idProdotto){
        getProdottiNegozio(idCommerciante).removeIf(p-> p.getId()==idProdotto);
    }

    public List<ProdottoEntity> getProdottiNegozio(long id){
        return getCommercianteById(id).getNegozio().getProdotti();
    }

    public List<OrdineEntity> getOrdiniCommerciante(long id,Predicate<OrdineEntity> predicate){
        return this.gestoreOrdini.getOrdini().stream().filter(ordineEntity ->
                ordineEntity.getEmittente().getId()==getCommercianteById(id).getNegozio().getId())
                .filter(predicate)
                .collect(Collectors.toList());
    }

    public void effettuaChiamata(long id,long idOrdine){
        CommercianteEntity commerciante = getCommercianteById(id);
        OrdineEntity ordine = getOrdiniCommerciante(id,ordineEntity -> true).stream().filter(o -> o.getId()==idOrdine)
                .findFirst().orElseThrow(NullPointerException::new);
        this.gestoreChiamate.addChiamata(this.gestoreChiamate.createChiamata(commerciante.getNegozio(),ordine));
    }
}
