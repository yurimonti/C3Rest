package it.unicam.cs.ids.papcteam.c3Rest;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDataBase {

    @Bean
    CommandLineRunner initDatabase(ClienteRepository clienteRepository,
                                   NegozioRepository negozioRepository,
                                   OrdineRepository ordineRepository){
        return args -> {
            Cliente cliente = new Cliente("Mario","Rossi","mario.rossi@gmail.com","mariorossi");
            cliente.initUsername();
            clienteRepository.save(cliente);
            Negozio negozio = new Negozio("ferramenta","ferramenta","Via Roma 10","Chiuso Sabato e Domenica");
            Prodotto p = new Prodotto("vite","vite 5mm al pezzo",0.02);
            //p.setNumero(100);
            negozio.getProdotti().add(p);
            negozio.getProdotti().add(new Prodotto("tubo","tubo ferro 32 mm diametro al Kg",6.55));
            Prodotto p1 = new Prodotto("carta vetrata","carta vetrata 100",1.25);
            negozio.getProdotti().add(p1);
            Ordine ordine = new Ordine(negozio);
            ordine.getProdotti().add(p);
            ordine.getProdotti().add(p1);
            ordineRepository.save(ordine);
            negozioRepository.save(negozio);
        };
    }

}
