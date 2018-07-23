
import javafx.beans.property.*;

public class MigliorGiocatore { // 01
    
    private final SimpleStringProperty username;
    private final SimpleIntegerProperty punteggio;
    
    public MigliorGiocatore(String n, int p){
        username = new SimpleStringProperty(n);
        punteggio = new SimpleIntegerProperty(p);
    }
    
    public String getUsername(){ return username.get(); }
    public int getPunteggio() { return punteggio.get(); }
}

/*
    Note:        
        01) la classe è una classe bean per la tabella della classifica dei miglior
            giocatori.
*/
