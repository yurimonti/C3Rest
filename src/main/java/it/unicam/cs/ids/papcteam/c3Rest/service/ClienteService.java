package it.unicam.cs.ids.papcteam.c3Rest.service;

import it.unicam.cs.ids.papcteam.c3Rest.entity.*;
import it.unicam.cs.ids.papcteam.c3Rest.repository.*;
import it.unicam.cs.ids.papcteam.c3Rest.util.ConcreteCreatoreOrdine;
import it.unicam.cs.ids.papcteam.c3Rest.util.CreatoreOrdine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private NegozioRepository negozioRepository;
    @Autowired
    private ProdottoRepository prodottoRepository;
    @Autowired
    private LockerRepository lockerRepository;
    @Autowired
    private OrdineRepository ordiniRepository;

    private CreatoreOrdine creatoreOrdine;

    public ClienteService() {
        this.creatoreOrdine = new ConcreteCreatoreOrdine();
    }

    public List<OrdineEntity> getOrdiniCliente(long id){
        return getClienteById(id).getOrdini();
    }

    public List<OrdineEntity> getOrdiniNonCompletati(long id){
        return getOrdiniCliente(id).stream().filter(o ->o.getStatoOrdine()!=StatoOrdine.COMPLETATO).collect(Collectors.toList());
    }

    public List<OrdineEntity> getOrdiniDaRitirare(long id){
        return getOrdiniCliente(id).stream().filter(o ->o.getStatoOrdine()==StatoOrdine.CONSEGNATO||
                o.getStatoOrdine()==StatoOrdine.RITIRO_NEGOZIO).collect(Collectors.toList());
    }

    public NegozioEntity setEmittenteOrdine(long idNegozio){
        if (this.negozioRepository.findAll().stream().noneMatch(negozioEntity -> negozioEntity.getId()==idNegozio))
            throw new NullPointerException("id negozio inesistente");
        NegozioEntity n = negozioRepository.getOne(idNegozio);
        this.creatoreOrdine.setEmittente(n);
        return n;
    }

    public LockerEntity setDestinazioneOrdine(long idLocker){
        if (this.lockerRepository.findAll().stream().noneMatch(lockerEntity -> lockerEntity.getId()==idLocker))
            throw new NullPointerException("id negozio inesistente");
        LockerEntity l = lockerRepository.getOne(idLocker);
        this.creatoreOrdine.setDestinazione(l);
        return l;
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
        if(prodottoIn.getNumero()>prodotto.getNumero())throw new IllegalArgumentException("numero maggiore del disponibile");
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
        LockerEntity lockerEntity;
        creatoreOrdine.getProdotti().forEach(prodottoOrdine-> {
            negozioEntity.getProdotti().stream()
                    .filter(prodottoNegozio -> prodottoNegozio.getSerialCode()==prodottoOrdine.getSerialCode())
                    .forEach(p -> {
                        if(prodottoOrdine.getNumero()<=p.getNumero())
                        p.setNumero(p.getNumero()-prodottoOrdine.getNumero());
                        else throw new IllegalArgumentException("numero maggiore del disponibile");
                    });
        });
        this.creatoreOrdine.setEmittente(negozioEntity);
        if(creatoreOrdine.getDestinazione()!=null){
            lockerEntity = this.lockerRepository.getOne(creatoreOrdine.getDestinazione().getId());
            this.creatoreOrdine.setDestinazione(lockerEntity);
            this.lockerRepository.save(lockerEntity);
        }
        OrdineEntity ordine = this.creatoreOrdine.creaOrdine();
        clearCreatore();
        cliente.getOrdini().add(ordine);
        this.negozioRepository.save(negozioEntity);
        this.clienteRepository.save(cliente);
        return ordine;
    }

    public void clearCreatore(){
        this.creatoreOrdine.setEmittente(null);
        this.creatoreOrdine.setDestinazione(null);
        this.creatoreOrdine.getProdotti().clear();
    }
}
