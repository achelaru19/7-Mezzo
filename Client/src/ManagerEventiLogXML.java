
import com.thoughtworks.xstream.*;
import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.sql.*;


public class ManagerEventiLogXML { // 01
    
    public void inviaEventoLogXML(String ip, int porta, String etichetta, String utente){
        MessaggioDiLog logMessage= new MessaggioDiLog(etichetta, utente,
                                    new Timestamp(System.currentTimeMillis()).toString());
        
        XStream xs = new XStream();
        String x = xs.toXML(logMessage);
        try(
            Socket s = new Socket(ip, porta);
            ObjectOutputStream oout = new ObjectOutputStream(s.getOutputStream());
            )
        {   
            x = x + "\n";
            oout.writeObject(x);
            Files.write(Paths.get("./EventoLog.txt"), x.getBytes());
        } catch(Exception e){System.out.println("Scrittura non riuscita, Errore:" + e.getMessage());}
    }
}

/*
Note:
    01) Questa classe invia messaggi di Log al server.

*/