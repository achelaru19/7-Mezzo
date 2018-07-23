
public class ConfigurazioniXML { // 01
    
    public ConfigurazioniStylingFinestraPrincipaleXML configurazioniStylingFinestraPrincipale;
    public ConfigurazioniDatabaseXML configurazioniDatabase;
    public ConfigurazioniClassificaXML configurazioniClassifica;
    public ConfigurazioniTemporaliXML configurazioniTemporali;
    public ConfigurazioniServerXML configurazioniServer;
    
    public ConfigurazioniXML(ConfigurazioniStylingFinestraPrincipaleXML csfp,ConfigurazioniDatabaseXML cdb,
                                    ConfigurazioniClassificaXML cc,ConfigurazioniTemporaliXML ct, ConfigurazioniServerXML cs ){
        configurazioniStylingFinestraPrincipale = csfp;
        configurazioniDatabase = cdb;
        configurazioniClassifica = cc;
        configurazioniTemporali = ct;
        configurazioniServer = cs;
    }
    
    
    class ConfigurazioniStylingFinestraPrincipaleXML{
    /* Configurazioni Styling FinestraPrincipale */
    public int dimensioneX;
    public int dimensioneY;
    public String backgroundColor;
    
        public ConfigurazioniStylingFinestraPrincipaleXML(int dimX, int dimY, String color){
            dimensioneX = dimX;
            dimensioneY = dimY;
            backgroundColor = color;
        }
    }
    
    class ConfigurazioniDatabaseXML{
    /* Configurazioni Database */
    public String nomeDB;
    public int porta;
    public String username;
    public String password;
    
        public ConfigurazioniDatabaseXML(String name, int port, String user, String pass){
            nomeDB = name;
            porta = port;
            username = user;
            password = pass;
        }
    }
    
    class ConfigurazioniClassificaXML{
    /* Configurazioni Classifica */
    public int layoutX;
    public int layoutY;
    public int massimoNumeroMigliorGiocatori;
    
        public ConfigurazioniClassificaXML(int X, int Y, int maxNum){
            layoutX = X;
            layoutY = Y;
            massimoNumeroMigliorGiocatori = maxNum;
        }
    }
    
    class ConfigurazioniTemporaliXML{
    /* Configurazioni Temporali */
    public int oreIndietroCache;
    public int giorniIndietroPieChart;
    
        public ConfigurazioniTemporaliXML(int ore, int giorni){
            oreIndietroCache = ore;
            giorniIndietroPieChart = giorni;
        }
    }
    
    class ConfigurazioniServerXML{
    /* Configurazioni Server */
    public String ip;
    public int porta;
           
        public ConfigurazioniServerXML(String i, int p){
            ip = i;
            porta = p;
        }
    }
}

/*
    Note:
        01) Un'istanza di questa classe viene inizializzata all'avvio e conterrà
            i parametri di sistema contenuto nel file Configurazioni.xml
*/
