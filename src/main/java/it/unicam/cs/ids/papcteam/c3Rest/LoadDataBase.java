package it.unicam.cs.ids.papcteam.c3Rest;

import it.unicam.cs.ids.papcteam.c3Rest.entity.*;
import it.unicam.cs.ids.papcteam.c3Rest.repository.ClienteRepository;
import it.unicam.cs.ids.papcteam.c3Rest.repository.LockerRepository;
import it.unicam.cs.ids.papcteam.c3Rest.repository.NegozioRepository;
import it.unicam.cs.ids.papcteam.c3Rest.repository.OrdineRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDataBase {

    @Bean
    CommandLineRunner initDatabase(ClienteRepository clienteRepository,
                                   NegozioRepository negozioRepository,
                                   OrdineRepository ordineRepository,
                                   LockerRepository lockerRepository){
        return args -> {
            ClienteEntity cliente = new ClienteEntity("Mario","Rossi","mario.rossi@gmail.com","mariorossi");
            cliente.initUsername();
            clienteRepository.save(cliente);
            NegozioEntity negozio = new NegozioEntity("ferramenta","ferramenta","Via Roma 10","Chiuso Sabato e Domenica");
            lockerRepository.save(new LockerEntity("locker1","via mazzini 23","tutti i giorni dalle 8 alle 21"));
            ProdottoEntity p = new ProdottoEntity("vite","vite 5mm al pezzo",0.02,10);
            //p.setNumero(100);
            negozio.getProdotti().add(p);
            negozio.getProdotti().add(new ProdottoEntity("tubo","tubo ferro 32 mm diametro al Kg",6.55,22));
            ProdottoEntity p1 = new ProdottoEntity("carta vetrata","carta vetrata 100",1.25,105);
            negozio.getProdotti().add(p1);
            OrdineEntity ordine = new OrdineEntity(negozio);
            ordine.getProdotti().add(p);
            ordine.getProdotti().add(p1);
            ordineRepository.save(ordine);
            negozioRepository.save(negozio);
        };
    }

}
