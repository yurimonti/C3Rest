package it.unicam.cs.ids.papcteam.c3;

public class Main {
    public String stampaOrdine(){
        Ordine o = new Ordine();
        return o.toString();
    }
    public static void main(String[] args) {
        Main m = new Main();
        System.out.println(m.stampaOrdine());
    }
}
