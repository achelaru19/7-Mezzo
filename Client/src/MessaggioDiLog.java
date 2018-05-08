
import java.io.*;


public class MessaggioDiLog implements Serializable{
    
    public final String etichetta;
    public final String utente;
    public final String timestamp;
    
    public MessaggioDiLog(String e, String u, String t){
        etichetta = e;
        utente = u;
        timestamp = t;
    }
}
