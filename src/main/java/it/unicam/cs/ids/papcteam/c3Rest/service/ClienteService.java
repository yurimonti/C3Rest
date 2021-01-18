package it.unicam.cs.ids.papcteam.c3Rest.service;

import it.unicam.cs.ids.papcteam.c3Rest.entity.ClienteEntity;
import it.unicam.cs.ids.papcteam.c3Rest.entity.NegozioEntity;
import it.unicam.cs.ids.papcteam.c3Rest.entity.OrdineEntity;
import it.unicam.cs.ids.papcteam.c3Rest.entity.ProdottoEntity;
import it.unicam.cs.ids.papcteam.c3Rest.repository.ClienteRepository;
import it.unicam.cs.ids.papcteam.c3Rest.repository.NegozioRepository;
import it.unicam.cs.ids.papcteam.c3Rest.repository.ProdottoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private NegozioRepository negozioRepository;
    @Autowired
    private ProdottoRepository prodottoRepository;

    private CreatoreOrdine creatoreOrdine;

    public ClienteService() {
        this.creatoreOrdine = new ConcreteCreatoreOrdine();
    }

    public NegozioEntity setEmittenteOrdine(long idNegozio){
        if (this.negozioRepository.findAll().stream().noneMatch(negozioEntity -> negozioEntity.getId()==idNegozio))
            throw new NullPointerException("id negozio inesistente");
        NegozioEntity n = negozioRepository.getOne(idNegozio);
        this.creatoreOrdine.setEmittente(n);
        return n;
    }

    public ProdottoEntity setProdottoOrdine(long idProdotto,int number){
        if(this.creatoreOrdine.getEmittente().getProdotti().stream().noneMatch(prodottoEntity -> prodottoEntity.getId()==idProdotto))
            throw new NullPointerException("nessun Prodotto con questo Id");
        ProdottoEntity prodotto = this.prodottoRepository.getOne(idProdotto);
        ProdottoEntity prodottoIn;
        if(this.creatoreOrdine.getProdotti().stream().anyMatch(prodottoEntity -> prodottoEntity.getSerialCode()==prodotto.getSerialCode())){
            prodottoIn = this.creatoreOrdine.getProdottoBySerialCode(prodotto.getSerialCode());
            prodottoIn.setNumero(prodottoIn.getNumero()+number);
        }else {
            prodottoIn = new ProdottoEntity(prodotto.getNome(),prodotto.getDescrizione(),prodotto.getPrezzo());
            prodottoIn.setSerialCode(prodotto.getSerialCode());
            this.creatoreOrdine.addProdotto(prodottoIn,number);
        }
        return prodottoIn;
    }

    public ClienteEntity getClienteById(long id){
        if(this.clienteRepository.findAll().stream().noneMatch(clienteEntity -> clienteEntity.getId()==id))throw
        new NullPointerException("nessun Cliente con questo Id");
        else
        return this.clienteRepository.getOne(id);
    }

    public OrdineEntity addOrdineToCliente(long id){
        if (this.clienteRepository.findAll().stream().noneMatch(clienteEntity -> clienteEntity.getId()==id))
            throw new NullPointerException("cliente con questo id inesistente");
        ClienteEntity cliente = getClienteById(id);
        if(negozioRepository.findAll().stream().noneMatch(negozioEntity -> negozioEntity.getId()==creatoreOrdine.getEmittente().getId()))
            throw new NullPointerException("negozio inesistente");
        NegozioEntity negozioEntity = this.negozioRepository.getOne(creatoreOrdine.getEmittente().getId());
        creatoreOrdine.getProdotti().forEach(prodottoOrdine-> {
            negozioEntity.getProdotti().stream()
                    .filter(prodottoNegozio -> prodottoNegozio.getSerialCode()==prodottoOrdine.getSerialCode())
                    .forEach(p -> p.setNumero(p.getNumero()-prodottoOrdine.getNumero()));
        });
        this.creatoreOrdine.setEmittente(negozioEntity);
        OrdineEntity ordine = this.creatoreOrdine.creaOrdine();
        cliente.getOrdini().add(ordine);
        this.negozioRepository.save(negozioEntity);
        this.clienteRepository.save(cliente);
        return ordine;
    }



}
