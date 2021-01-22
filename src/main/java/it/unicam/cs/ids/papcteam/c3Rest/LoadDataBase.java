package it.unicam.cs.ids.papcteam.c3Rest;

import it.unicam.cs.ids.papcteam.c3Rest.entity.*;
import it.unicam.cs.ids.papcteam.c3Rest.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDataBase {

    @Bean
    CommandLineRunner initDatabase(ClienteRepository clienteRepository,
                                   LockerRepository lockerRepository,
                                   CommercianteRepository commercianteRepository){
        return args -> {
            ClienteEntity cliente = new ClienteEntity("a","a","a@gmail.com","a");
            cliente.initUsername();
            clienteRepository.save(cliente);
            /*CommercianteEntity commercianteEntity = new CommercianteEntity("matteo","minzi",
                    "mm@gmail.com","1234");
            commercianteRepository.save(commercianteEntity);
            NegozioEntity negozio = new NegozioEntity("ferramenta","ferramenta","Via Roma 10","Chiuso Sabato e Domenica");
            lockerRepository.save(new LockerEntity("locker1","via mazzini 23","tutti i giorni dalle 8 alle 21"));
            ProdottoEntity p = new ProdottoEntity("vite","vite 5mm al pezzo",0.02,10);
            negozio.getProdotti().add(p);
            negozio.getProdotti().add(new ProdottoEntity("tubo","tubo ferro 32 mm diametro al Kg",6.55,22));
            ProdottoEntity p1 = new ProdottoEntity("carta vetrata","carta vetrata 100",1.25,105);
            negozio.getProdotti().add(p1);
            negozioRepository.save(negozio);*/
            CommercianteEntity commercianteEntity = new CommercianteEntity("matteo","minzi",
                    "mm@gmail.com","1234");
            commercianteEntity.initUsername();
            NegozioEntity negozio = new NegozioEntity("ferramenta","ferramenta","Via Roma 10","Chiuso Sabato e Domenica");
            negozio.getProdotti().add(new ProdottoEntity("vite","vite 5mm al pezzo",0.02,10));
            commercianteEntity.setNegozio(negozio);
            commercianteRepository.save(commercianteEntity);
            lockerRepository.save(new LockerEntity("locker1","via mazzini 23","tutti i giorni dalle 8 alle 21"));
        };
    }

}
