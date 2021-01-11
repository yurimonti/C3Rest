package it.unicam.cs.ids.papcteam.c3Rest;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@Entity
@Table(name = "ordini")
public class Ordine {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "numero_ordine")
    private long numeroOrdine;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "destinazione_id",referencedColumnName = "id")
    private Locker destinazione;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "emittente_id",referencedColumnName = "id")
    private Negozio emittente;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Prodotto> prodotti;
    @Column(name = "soldi")
    private double soldi;

    public Ordine() {
        this.numeroOrdine = new Random().nextInt(99999999);
        this.prodotti = new ArrayList<>();
        this.soldi = 0;
        this.emittente= null;
        this.destinazione =null;
    }

    public Ordine(Negozio emittente){
        this();
        this.emittente = emittente;
    }

    public Ordine(Locker destinazione, Negozio emittente) {
        this(emittente);
        this.destinazione = destinazione;
    }

    public long getId() {
        return id;
    }

    public void calcoloSoldi(){
        for (Prodotto o : this.prodotti) {
            this.soldi +=o.getNumero()*o.getPrezzo();
        }
    }

    public long getNumeroOrdine() {
        return numeroOrdine;
    }

    public void setNumeroOrdine(long numeroOrdine) {
        this.numeroOrdine = numeroOrdine;
    }

    public Locker getDestinazione() {
        return destinazione;
    }

    public void setDestinazione(Locker destinazione) {
        this.destinazione = destinazione;
    }

    public Negozio getEmittente() {
        return emittente;
    }

    public void setEmittente(Negozio emittente) {
        this.emittente = emittente;
    }

    public List<Prodotto> getProdotti() {
        return prodotti;
    }

    public void setProdotti(List<Prodotto> prodotti) {
        this.prodotti.addAll(prodotti);
    }

    public double getSoldi() {
        return soldi;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ordine ordine = (Ordine) o;
        return id == ordine.id && numeroOrdine == ordine.numeroOrdine && Double.compare(ordine.soldi, soldi) == 0 && Objects.equals(destinazione, ordine.destinazione) && Objects.equals(emittente, ordine.emittente) && Objects.equals(prodotti, ordine.prodotti);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, numeroOrdine, destinazione, emittente, prodotti, soldi);
    }

    @Override
    public String toString() {
        return "Ordine{" +
                "id=" + getId() +
                ", numeroOrdine=" + getNumeroOrdine() +
                ", destinazione=" + getDestinazione() +
                ", emittente=" + getEmittente() +
                ", prodotti=" + getProdotti() +
                ", soldi=" + getSoldi() +
                '}';
    }

    public void setId(long id) {
        this.id = id;
    }
}
