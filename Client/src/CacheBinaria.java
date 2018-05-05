
import java.io.*;
import java.sql.*;
import java.util.*;


public class CacheBinaria implements Serializable { 
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