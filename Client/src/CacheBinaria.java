
import java.io.*;
import java.sql.*;
import java.util.*;


public class CacheBinaria implements Serializable { 
    public String username;
    public List<Carta> carteGiocatore;
    public List<Carta> carteMazziere;
    public Timestamp tempo;
    
    public CacheBinaria(String u, List<Carta> cG, List<Carta> cM){
        username = u;
        carteGiocatore = cG;
        carteMazziere = cM;
        tempo = new Timestamp(System.currentTimeMillis());
    }
}