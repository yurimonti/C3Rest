package it.unicam.cs.ids.papcteam.c3Rest.entity;

import javax.persistence.*;
import java.util.Objects;
import java.util.Random;

@Entity
@Table(name = "prodotti")
public class ProdottoEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "prezzo")
    private double prezzo;
    @Column(name = "nome")
    private String nome;
    @Column(name = "descrizione")
    private String descrizione;
    @Column(name = "numero")
    private int numero;
    @Column(name = "serialCode")
    private long serialCode;

    public ProdottoEntity(){
        this.serialCode = new Random().nextInt(999999999);
    }

    public ProdottoEntity(String nome, String descrizione, double prezzo) {
        this();
        this.prezzo = prezzo;
        this.nome = nome;
        this.descrizione = descrizione;
        this.numero = 1;
    }

    public ProdottoEntity(String nome, String descrizione, double prezzo, int numero) {
        this(nome,descrizione,prezzo);
        this.numero = numero;
    }

    public long getSerialCode(){
        return this.serialCode;
    }

    public void setSerialCode(long serialCode){
        this.serialCode = serialCode;
    }

    public int getNumero() {
        return this.numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProdottoEntity prodotto = (ProdottoEntity) o;
        return Double.compare(prodotto.prezzo, prezzo) == 0 && serialCode == prodotto.serialCode && Objects.equals(nome, prodotto.nome) && Objects.equals(descrizione, prodotto.descrizione);
    }

    @Override
    public int hashCode() {
        return Objects.hash(prezzo, nome, descrizione, serialCode);
    }

    @Override
    public String toString() {
        return "Prodotto{" +
                "id=" + id +
                ", prezzo=" + prezzo +
                ", nome='" + nome + '\'' +
                ", descrizione='" + descrizione + '\'' +
                ", numero=" + numero +
                '}';
    }
}
