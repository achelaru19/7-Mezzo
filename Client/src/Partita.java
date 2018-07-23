
import java.util.*;


public class Partita {
    private Mazzo mazzo;
    private Giocatore mazziere;
    private Giocatore user;
    public boolean giocoFinito;
    public boolean vittoria;
    
    public Partita(String username){
        this.user = new Giocatore(username);
        this.mazziere = new Giocatore();
        this.giocoFinito = false;
        this.mazzo = new Mazzo();
        vittoria = false;
        mazzo.mescola();
    }
    
    public void inizializza(){ // 01
        this.mazzo = new Mazzo();
        mazzo.mescola();
        giocoFinito = false;
        
        mazziere.inizializza();
        user.inizializza();
        
        Carta cartaEstratta = mazzo.estraiCarta();
        System.out.println(cartaEstratta.getNome());
        mazziere.aggiungiCarta(cartaEstratta);
        
    }
    
    public Carta prendiCarta(){ // 02
        Carta cartaEstratta = mazzo.estraiCarta();
        user.aggiungiCarta(cartaEstratta);
        System.out.println("Il numero di carte rimanenti è: " + Integer.toString(mazzo.getSize()));
        if (user.getPunteggio() > 7.5){
            giocoFinito = true;
        }
        return cartaEstratta;
    }
    
    public Carta sostituisciCartaMazziere(){
        return mazziere.getPrimaCarta();
    }
    
   
    
    public Carta aggiungiCartaMazziere(){
        Carta cartaEstratta = mazzo.estraiCarta();
        mazziere.aggiungiCarta(cartaEstratta);
        return cartaEstratta;
    }
    
    public boolean stai(){ // 03
        double punteggioUser = user.getPunteggio();
        double punteggioMazziere = mazziere.getPunteggio();
        System.out.println("Il punteggio giocatore è " + Double.toString(punteggioUser));
        System.out.println("Il punteggio mazziere è " + Double.toString(punteggioMazziere));
        
        if(punteggioMazziere < punteggioUser){
            return true;
        }
        else{
            if(punteggioMazziere > 7.5){
                System.out.println("HAI VINTO");
                vittoria = true;
                System.out.println("Il punteggio mazziere è " + Double.toString(punteggioMazziere));
                giocoFinito = true;
                return false;
            }
            else{ 
                //CASO IN CUI MAZZIERE ABBIA SUPERATO GIOCATORE
                System.out.println("HA VINTO MAZZIERE");   
                System.out.println("Il punteggio mazziere è " + Double.toString(punteggioMazziere));
                giocoFinito = true;
                return false;
            }
        }   
    }   
    
    public void resetta(){
        System.out.println("TIME TO RESET"); /* For Debugging Purpose */
        inizializza();
    }
    
    public boolean getPartitaFinita(){
        return this.giocoFinito;
    }
    
    public double getPunteggioGiocatore(){
        return user.getPunteggio();
    }
    
    public double getPunteggioMazziere(){
        return mazziere.getPunteggio();
    }
    
    public List<Carta> carteMazziere(){
        return mazziere.getCarte();
    }
    
    public List<Carta> carteGiocatore(){
        return user.getCarte();
    }
    
    public void aggiungiCartaMazziere(Carta c){
        mazzo.estraiDeterminataCarta(c);
        mazziere.aggiungiCarta(c);
    }
    
    public void aggiungiCartaGiocatore(Carta c){
        mazzo.estraiDeterminataCarta(c);
        user.aggiungiCarta(c);
    }
    
    public boolean vittoria(){
        return vittoria;
    }
     
}

/*

    01) questo metodo viene separato dal costruttore perché così facendo possiamo
        inizializzare una nuova partita senza dover creare una nuova istanza
        della classe partita.
    02) questo metodo aggiunge una carta alla mano dell'utente.
    03) quando viene invocato questo metodo, il giocatore ha deciso di rimanere
        e il mazziere inizia ad estrarre tante carte fino a che non supera il giocatore
        o finché non supera il punteggio di 7.5.
*/
