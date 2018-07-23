
import java.io.*;
import java.sql.*;
import java.util.*;


public class CacheBinaria implements Serializable { // 01
    public String username;
    public List<Carta> carteGiocatore;
    public Carta cartaMazziere;
    public Timestamp tempo;
    
    public CacheBinaria(String u, List<Carta> cG, Carta cM){
        username = u;
        carteGiocatore = cG;
        cartaMazziere = cM;
        tempo = new Timestamp(System.currentTimeMillis());
    }
}

/*
    Note:        
        01) la classe implementa l'interfaccia Serializable, che ne 
            permette la serializzazione per l'inserimento su un file
            in formato .bin
*/
