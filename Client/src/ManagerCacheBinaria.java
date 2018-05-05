
import java.io.*;
import java.util.*;


public class ManagerCacheBinaria {
    
    public void salvaCacheBinaria(String u, List<Carta> cG, Carta cM){
        try(FileOutputStream file = new FileOutputStream("./Cache.bin");
            ObjectOutputStream oggettoScritto = new ObjectOutputStream(file);){
            CacheBinaria cache = new CacheBinaria(u, cG, cM);
            oggettoScritto.writeObject(cache);
        } catch(IOException ioe){System.out.println(ioe.getMessage());}
    }
    
    public CacheBinaria prelevaCacheBinaria() {
        CacheBinaria cache;
        try(FileInputStream file = new FileInputStream("./Cache.bin");
            ObjectInputStream oggettoLetto = new ObjectInputStream(file);){
            cache = (CacheBinaria) oggettoLetto.readObject();
            return cache;
        } catch(IOException ioe){System.out.println(ioe.getMessage());}
          catch(ClassNotFoundException cnfe){System.out.println(cnfe.getMessage());}
        return null;
    }
    
    public void cancellaCacheBinaria(){
        String filePath = new File("").getAbsolutePath();
        new File(filePath + "./Cache.bin").delete();
    }
    
}
