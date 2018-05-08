
public class ConfigurazioniXML {
    
    public ConfigurazioniStylingFinestraPrincipaleXML configurazioniStylingFinestraPrincipale;
    public ConfigurazioniDatabaseXML configurazioniDatabase;
    public ConfigurazioniClassificaXML configurazioniClassifica;
    public ConfigurazioniTemporaliXML configurazioniTemporali;
    public ConfigurazioniServer configurazioniServer;
    
    public ConfigurazioniXML(ConfigurazioniStylingFinestraPrincipaleXML csfp,ConfigurazioniDatabaseXML cdb,
                                    ConfigurazioniClassificaXML cc,ConfigurazioniTemporaliXML ct ){
        configurazioniStylingFinestraPrincipale = csfp;
        configurazioniDatabase = cdb;
        configurazioniClassifica = cc;
        configurazioniTemporali = ct;
        
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
    
    class ConfigurazioniServer{
    /* Configurazioni Server */
           public String ip;
           public int porta;
           
           public ConfigurazioniServer(String i, int p){
               ip = i;
               porta = p;
           }
    }
}

