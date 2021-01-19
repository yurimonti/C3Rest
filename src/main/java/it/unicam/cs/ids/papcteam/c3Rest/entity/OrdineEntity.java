package it.unicam.cs.ids.papcteam.c3Rest.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@Entity

@Table(name = "ordini")
public class OrdineEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "numero_ordine")
    private long numeroOrdine;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "destinazione_id",referencedColumnName = "id")
    private LockerEntity destinazione;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "emittente_id",referencedColumnName = "id")
    private NegozioEntity emittente;
    @OneToMany(cascade = CascadeType.ALL)
    private List<ProdottoEntity> prodotti;
    @Column(name = "soldi")
    private double soldi;
    @Column(name = "stato")
    @Enumerated
    private StatoOrdine statoOrdine;

    public OrdineEntity() {
        this.numeroOrdine = new Random().nextInt(99999999);
        this.prodotti = new ArrayList<>();
        this.soldi = 0;
        this.emittente= null;
        this.destinazione =null;
        this.statoOrdine = StatoOrdine.ESEGUITO;
    }

    public OrdineEntity(NegozioEntity emittente){
        this();
        this.emittente = emittente;
    }

    public OrdineEntity(LockerEntity destinazione, NegozioEntity emittente) {
        this(emittente);
        this.destinazione = destinazione;
    }

    public StatoOrdine getStatoOrdine() {
        return statoOrdine;
    }

    public void setSoldi(double soldi) {
        this.soldi = soldi;
    }

    public void setStatoOrdine(StatoOrdine statoOrdine) {
        this.statoOrdine = statoOrdine;
    }

    public long getId() {
        return id;
    }

    public void calcoloSoldi(){
        for (ProdottoEntity o : this.prodotti) {
            this.soldi +=o.getNumero()*o.getPrezzo();
        }
    }

    public long getNumeroOrdine() {
        return numeroOrdine;
    }

    public void setNumeroOrdine(long numeroOrdine) {
        this.numeroOrdine = numeroOrdine;
    }

    public LockerEntity getDestinazione() {
        return destinazione;
    }

    public void setDestinazione(LockerEntity destinazione) {
        this.destinazione = destinazione;
    }

    public NegozioEntity getEmittente() {
        return emittente;
    }

    public void setEmittente(NegozioEntity emittente) {
        this.emittente = emittente;
    }

    public List<ProdottoEntity> getProdotti() {
        return prodotti;
    }

    public void setProdotti(List<ProdottoEntity> prodotti) {
        this.prodotti.addAll(prodotti);
    }

    public double getSoldi() {
        return soldi;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrdineEntity ordine = (OrdineEntity) o;
        return id == ordine.id && numeroOrdine == ordine.numeroOrdine && Double.compare(ordine.soldi, soldi) == 0 && Objects.equals(destinazione, ordine.destinazione) && Objects.equals(emittente, ordine.emittente) && Objects.equals(prodotti, ordine.prodotti);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, numeroOrdine, destinazione, emittente, prodotti, soldi);
    }

    @Override
    public String toString() {
        String s;
        if (getDestinazione()== null) s = "null";
        else s = getDestinazione().toString();
        return "Ordine{" +
                "id=" + getId() +
                ", numeroOrdine=" + getNumeroOrdine() +
                ", destinazione=" + s +
                ", emittente=" + getEmittente().toString() +
                ", prodotti=" + getProdotti() +
                ", soldi=" + getSoldi() +
                ", stato=" + getStatoOrdine().toString() +
                '}';
    }

    public void setId(long id) {
        this.id = id;
    }
}
