package it.unicam.cs.ids.papcteam.c3Rest;
import java.util.List;

public interface PuntoRitiro {
    String getIndirizzo();
    String getOrario();
    String getNome();
    List<Ordine> getOrdini();
}
