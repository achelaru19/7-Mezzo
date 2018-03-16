
import java.util.*;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.*;
import javafx.scene.layout.*;


public class Partita {
    private Mazzo mazzo;
    private Giocatore mazziere;
    private Giocatore user;
    
    
    public boolean giocoFinito;
    
    public Partita(String username){
        this.mazzo = new Mazzo();
        this.user = new Giocatore(username);
        this.mazziere = new Giocatore();
        inizializza();
    }
    
    public void inizializza(){
        mazzo.mescola();
        
        mazziere.inizializza();
        user.inizializza();
        
        Carta cartaEstratta = mazzo.estraiCarta();
        BoxCarte.aggiungiCarta(FinestraPrincipale.boxGiocatore , cartaEstratta.getNome());
        user.aggiungiCarta(cartaEstratta);
        
        cartaEstratta = mazzo.estraiCarta();
        BoxCarte.aggiungiCarta(FinestraPrincipale.boxMazziere , "retro");
        mazziere.aggiungiCarta(cartaEstratta);
        
    }
    
    public Carta prendiCarta(){
        Carta cartaEstratta = mazzo.estraiCarta();
        user.aggiungiCarta(cartaEstratta);
        if (user.getPunteggio() > 7.5){
            System.out.println("GAME OVER");
            giocoFinito = true;
        }
        return cartaEstratta;
    }
    
    public void sostituisciCartaMazziere(){
        BoxCarte.sostituisciCartaMazziere(FinestraPrincipale.boxMazziere, mazziere.getPrimaCarta());
    }
    
    public Carta aggiungiCartaMazziere(){
        Carta cartaEstratta = mazzo.estraiCarta();
        mazziere.aggiungiCarta(cartaEstratta);
        return cartaEstratta;
    }
    
    public boolean stai(){
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
        FinestraPrincipale.boxGiocatore.getChildren().clear();
        FinestraPrincipale.boxMazziere.getChildren().clear();
        inizializza();
    }
    
    public boolean getPartitaFinita(){
        return this.giocoFinito;
    }
    

    
    public void mostraCarteMazziere(){
        // Scambia carta coperta con carta effettiva
        
    }
    
    
    public static void main(String[] args){
        
        while(true){
        
            Partita p = new Partita("angel");

            while(!p.getPartitaFinita()){
                //p.mostraCarteGiocatore();
                System.out.print("Enter something : ");
                Scanner scanner = new Scanner(System.in);
                String mossa = scanner.nextLine();
                if(mossa.equals("+")){
                    p.prendiCarta();
                }
                else{
                    p.stai();
                    break;
                }
            }
            
            System.out.println("-----------------");
        }
    }
    
    
}

