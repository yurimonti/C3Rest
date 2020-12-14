package it.unicam.cs.ids.papcteam.c3;

import javax.persistence.*;

@Entity
@Table(name = "lockers")
public class Locker implements PuntoRitiro {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "nome")
    private String nome;
    @Column(name = "indirizzo")
    private String indirizzo;
    @Column(name = "orario")
    private String orario;

    public Locker() {
    }

    public Locker(String nome, String indirizzo, String orario) {
        this();
        this.nome = nome;
        this.indirizzo = indirizzo;
        this.orario = orario;
    }

    @Override
    public String getIndirizzo() {
        return this.indirizzo;
    }

    @Override
    public String getOrario() {
        return this.orario;
    }

    @Override
    public String getNome() {
        return this.nome;
    }

}
