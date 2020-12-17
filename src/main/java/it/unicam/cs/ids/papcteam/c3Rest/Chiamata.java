package it.unicam.cs.ids.papcteam.c3Rest;

import javax.persistence.*;
/**
 * questa classe farà parte delle entità e definisce una chiamata da parte del commerciante verso un corriere qualsiasi:
 * Una chiamata contiene: il Negozio del commerciante che ha effettuato la richiesta del corriere, e l'ordine che quel
 * negozio ha in attesa di spedizione (ordine.destinazione !=null)
 * Flusso:
 * 1. Un commerciante richiede un corriere creando una chiamata ed inserendola nel repository delle chiamate con l'ordine;
 * 2. Un corriere quando lavora può andare a vedere la lista delle chiamate di qualsiasi commerciante associata al
 * negozio, quindi può accettare o meno le chiamate che vuole
 * 3. Una volta accettata la chiamata l'ordine della chiamata viene inserito nella lista ordini del corriere
 * accettante
 **/
@Entity
@Table(name = "chiamate")
public class Chiamata {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "negozio_id",referencedColumnName = "id")
    private Negozio negozio;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ordine_id",referencedColumnName = "id")
    private Ordine ordine;

    public Chiamata() {
    }

    public Ordine getOrdine() {
        return ordine;
    }

    public void setOrdine(Ordine ordine) {
        this.ordine = ordine;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Negozio getNegozio() {
        return negozio;
    }

    public void setNegozio(Negozio negozio) {
        this.negozio = negozio;
    }
}
