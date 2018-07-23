
import java.io.*;
import java.util.*;


public class ManagerCacheBinaria { // 01
    
    public void salvaCacheBinaria(String u, List<Carta> cG, Carta cM){ //02
        try(FileOutputStream file = new FileOutputStream("./Cache.bin");
            ObjectOutputStream oggettoScritto = new ObjectOutputStream(file);){
            CacheBinaria cache = new CacheBinaria(u, cG, cM);
            oggettoScritto.writeObject(cache);
        } catch(IOException ioe){System.out.println(ioe.getMessage());}
    }
    
    public CacheBinaria prelevaCacheBinaria() { // 03
        CacheBinaria cache;
        try(FileInputStream file = new FileInputStream("./Cache.bin");
            ObjectInputStream oggettoLetto = new ObjectInputStream(file);){
            cache = (CacheBinaria) oggettoLetto.readObject();
            return cache;
        } catch(IOException ioe){System.out.println(ioe.getMessage());}
          catch(ClassNotFoundException cnfe){System.out.println(cnfe.getMessage());}
        return null;
    }
    
    public void cancellaCacheBinaria(){ // 04
        String filePath = new File("").getAbsolutePath();
        new File(filePath + "./Cache.bin").delete();
    }
    
}

/*
Note:
    01) Questa classe gestisce la cache binaria.
    02) Questo metodo salva su un file binario lo stato della partita in corso.
    03) Questo metodo legge da un file bianario la cache e la restituisce al chiamante.
    04) Questo metodo cancella dal file bianrio l'ultima cache salvata dopo che 
        è stata restituita dalla funzione (03).
*/