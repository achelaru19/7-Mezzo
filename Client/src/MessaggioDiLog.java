
import java.io.*;


public class MessaggioDiLog implements Serializable{ // 01
    
    public final String etichetta;
    public final String utente;
    public final String timestamp;
    
    public MessaggioDiLog(String e, String u, String t){
        etichetta = e;
        utente = u;
        timestamp = t;
    }
}

/*
    Note:        
        01) la classe implementa l'interfaccia Serializable, che ne 
            permette la serializzazione per la trasmissione al server.
*/
