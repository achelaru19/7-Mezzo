
import javafx.beans.property.*;

public class MigliorGiocatore {
    
    private final SimpleStringProperty username;
    private final SimpleIntegerProperty punteggio;
    
    public MigliorGiocatore(String n, int p){
        username = new SimpleStringProperty(n);
        punteggio = new SimpleIntegerProperty(p);
    }
    
    public String getUsername(){ return username.get(); }
    public int getPunteggio() { return punteggio.get(); }
}
