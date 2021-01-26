package it.unicam.cs.ids.papcteam.c3Rest.service;

import it.unicam.cs.ids.papcteam.c3Rest.entity.ProdottoEntity;
import it.unicam.cs.ids.papcteam.c3Rest.repository.ProdottoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GestoreProdotti {
    @Autowired
    private ProdottoRepository prodottoRepository;

    public GestoreProdotti() {
    }

    public ProdottoEntity getProdottoById(long id){
        return this.prodottoRepository.findById(id).orElseThrow(() ->
                new NullPointerException("prodotto con questo id non esistente"));
    }

    public void deleteProdotto(long idProdotto){
        if(this.prodottoRepository.existsById(idProdotto))this.prodottoRepository.deleteById(idProdotto);
        else throw new NullPointerException("nessun prodotto con questo id");
    }
}
