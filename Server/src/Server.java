import java.net.*;
import java.io.*;
import java.nio.file.*;
import javax.xml.*;
import javax.xml.parsers.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import javax.xml.validation.*;
import org.w3c.dom.*;

public class Server {
    
  private static int n = 0;
  
    // 01
    public static void main(String[] args) {
        try ( ServerSocket servs = new ServerSocket(8080, 10) ){
          while(true) { 
            Socket s = servs.accept();
            Thread t = new Thread() {
              public void run() {
                try (ObjectInputStream oin = new ObjectInputStream(s.getInputStream())){
                    String x = (String) oin.readObject();
                    System.out.println(++n + ") ricevuto: \n" + (String) x);

                    Files.write(Paths.get("./MessaggioDiLog.txt"), x.getBytes());
                    if(validaXML()){
                        Files.write(Paths.get("./FileLogs.txt"), x.getBytes(), 
                                                 StandardOpenOption.APPEND);
                      }
                    else{
                        System.out.println("Errore validazione");
                    }
                } catch (Exception e) {System.out.println("Attenzione, errore: " + e.getMessage());}
              } 
            };
            t.start(); 
          }
        } catch (IOException e) {e.getMessage();}
    }
  
    // 02
    public static boolean validaXML(){
        try{
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Document d = (Document) db.parse(new File("./MessaggioDiLog.txt"));
            Schema s = sf.newSchema(new StreamSource(new File("./validaLog.xsd")));
            s.newValidator().validate(new DOMSource(d));
        } catch (Exception e) {
            System.out.println("Errore di validazione: " + e.getMessage()+"!");
            return false;
        }
        System.out.println("Validazione avvenuta con successo");
        return true;
    }
}


/*

Note:
    01) Crea un TCP server socket che sta in ascolto sulla porta 8080. E' in grado
        di ricevere richieste di connessione e restituire un socket, il quale genera 
        un ObjectInputStream
    02) Valida il file onfigurazione.xml secondo la grammatica definita dal 
        file Configurazione.xsd

*/