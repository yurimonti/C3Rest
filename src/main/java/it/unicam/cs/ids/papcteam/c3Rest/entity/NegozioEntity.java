package it.unicam.cs.ids.papcteam.c3Rest.entity;

import it.unicam.cs.ids.papcteam.c3Rest.PuntoRitiro;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "negozi")
public class NegozioEntity implements PuntoRitiro {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "nome")
    private String nome;
    @Column(name = "descrizione")
    private String descrizione;
    @Column(name = "indirizzo")
    private String indirizzo;
    @Column(name = "orario")
    private String orario;
    @OneToMany(cascade = CascadeType.ALL)
    private List<ProdottoEntity> prodotti;
    @OneToMany(cascade = CascadeType.ALL)
    private List<OrdineEntity> ordini;

    public NegozioEntity() {
        this.prodotti = new ArrayList<>();
        this.ordini = new ArrayList<>();
    }

    public NegozioEntity(String nome, String descrizione, String indirizzo, String orario) {
        this();
        this.nome = nome;
        this.descrizione = descrizione;
        this.indirizzo = indirizzo;
        this.orario = orario;
    }

    @Override
    public List<OrdineEntity> getOrdini() {
        return ordini;
    }

    /*public void addProdotto(Prodotto prodotto){
        addProdotto(prodotto,1);
    }*/

    /*public void addProdotto(Prodotto prodotto, int numero){
        Predicate<Prodotto> predicate = prodotto1 -> prodotto1.getId()==prodotto.getId();
        Stream<Prodotto> stream = getProdotti().stream().filter(predicate);
        if (stream.findFirst().isPresent()){
            Prodotto prod = stream.findFirst().get();
            prod.setNumero(numero+prod.getNumero());
        }else{
            prodotto.setNumero(numero);
            getProdotti().add(prodotto);
        }
    }*/

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String name) {
        this.nome = name;
    }

    public void setDescrizione(String description) {
        this.descrizione = description;
    }

    public long getId() {
        return id;
    }

    @Override
    public String getNome() {
        return nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public List<ProdottoEntity> getProdotti() {
        return prodotti;
    }

    @Override
    public String getIndirizzo() {
        return indirizzo;
    }

    @Override
    public String getOrario() {
        return orario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NegozioEntity negozio = (NegozioEntity) o;
        return id == negozio.id && nome.equals(negozio.nome) && descrizione.equals(negozio.descrizione) && indirizzo.equals(negozio.indirizzo) && orario.equals(negozio.orario) && prodotti.equals(negozio.prodotti);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, descrizione, indirizzo, orario, prodotti);
    }

    @Override
    public String toString() {
        return "Negozio{" +
                "id=" + getId() +
                ", nome='" + getNome() + '\'' +
                ", descrizione='" + getDescrizione() + '\'' +
                ", indirizzo='" + getIndirizzo() + '\'' +
                ", orario='" + getOrario() + '\'' +
                ", prodotti=" + getProdotti() + '\'' +
                ", ordini=" + getOrdini() +
                '}';
    }

    /*public void addProdotto(Prodotto prodotto){
        prodotto.setNegozio(this);
        getProdotti().add(prodotto);
    }*/

}
