
import java.util.*;


public class Giocatore { // 01
    
    private String username;
    private List<Carta> carte;
    
    public Giocatore(String n){
        this.username = n;
        carte = new ArrayList();
    }
    
    public Giocatore(){
        this("mazziere");
    }
    
    public int getNumero(){
        return carte.size();
    }
    
    public double getPunteggio(){
        double punteggio = 0.0;
        for (Carta c : carte){
            punteggio += c.getValore();
        }
        return punteggio;
    }
    
    public void inizializza(){
        carte = new ArrayList();
    }
    
    public void aggiungiCarta(Carta c){
        carte.add(c);
    }
    
    public void stampa(){
        System.out.println("Lo user " + this.username + " ha le seguenti carte:");
        for(Carta c : carte){
            System.out.println(c.getNome());
        }
    }
    
    public List<Carta> getCarte(){
        return carte;
    }
    
    public Carta getPrimaCarta(){
        return carte.get(0);
    }
}

/* 
Note:
    01) Questa classe rappresenta un giocatore e contiene una lista delle carte che 
    ha estratto e il suo nome. Se non viene specificato nessun nome, la classe considera
    quel giocatore come se fosse il mazziere.
*/