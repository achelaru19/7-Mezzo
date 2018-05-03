
import javax.xml.parsers.*;
import javax.xml.validation.*;
import javax.xml.transform.stream.*;
import javax.xml.transform.dom.*;
import javax.xml.*;
import org.w3c.dom.*;
import java.io.*;
import java.nio.file.*;
import com.thoughtworks.xstream.*;
import org.xml.sax.*;

public class ManagerParametriConfigurazioni {

    private final String pathConfigurazioniXML;
    private final String pathConfigurazioniXSD;


    public ManagerParametriConfigurazioni(String pathConfigXML, String pathConfigXSD) {
        pathConfigurazioniXML = pathConfigXML;
        pathConfigurazioniXSD = pathConfigXSD;
    }

    public ConfigurazioniXML inizializzaParametriConfigurazione(){
        ConfigurazioniXML parametri;
        try{
            if (validaFileDiConfigurazione()) {
                XStream xs = new XStream();
                String configurazioniLette = new String(Files.readAllBytes(Paths.get(pathConfigurazioniXML)));
                parametri = (ConfigurazioniXML) xs.fromXML(configurazioniLette);
                return parametri;
            }
        } catch(Exception e) {System.out.println("Errore lettura file di Configurazione: " + e.getMessage());}
        return null;
    }

    public boolean validaFileDiConfigurazione(){
        try{
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Document d = db.parse(new File(pathConfigurazioniXML));
            Schema s = sf.newSchema(new StreamSource(new File(pathConfigurazioniXSD)));
            s.newValidator().validate(new DOMSource(d));
        } catch (Exception e) {
            if (e instanceof SAXException)
                System.out.println("Errore di validazione: " + e.getMessage());
            else
                System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
}

