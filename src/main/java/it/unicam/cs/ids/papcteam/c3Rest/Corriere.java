package it.unicam.cs.ids.papcteam.c3Rest;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "corrieri")
public class Corriere {
    private @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) long id;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "nome")
    private String nome;

    @Column(name = "cognome")
    private String cognome;

    @Column(name = "username")
    private String username;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_corriere",referencedColumnName = "id")
    private List<Ordine> ordini;

    public Corriere() {
        this.ordini = new ArrayList<>();
    }

    public Corriere(String nome, String cognome, String email, String password) {
        this();
        this.email = email;
        this.password = password;
        this.nome = nome;
        this.cognome = cognome;
    }

    public void initUsername(){
        this.username = (this.nome+'.'+this.cognome).toLowerCase();
    }

    public List<Ordine> getOrdini() {
        return ordini;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Corriere corriere = (Corriere) o;
        return id == corriere.id && email.equals(corriere.email) && password.equals(corriere.password) && nome.equals(corriere.nome) && cognome.equals(corriere.cognome) && Objects.equals(username, corriere.username) && Objects.equals(ordini, corriere.ordini);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, password, nome, cognome, username, ordini);
    }

    @Override
    public String toString() {
        return "Corriere{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", username='" + username + '\'' +
                ", ordini=" + ordini.stream().map(Ordine::getId).toString() +
                '}';
    }
}
