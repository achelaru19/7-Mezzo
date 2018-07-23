
import java.sql.*;
import java.util.*;

public class GestoreDataBase { 
    private final int portaDB;
    private final String nomeDB;
    private final String usernameDB;
    private final String passwordDB;
    private Connection connection;
    
    public GestoreDataBase(){
        portaDB = FinestraPrincipale.parametri.configurazioniDatabase.porta;
        nomeDB = FinestraPrincipale.parametri.configurazioniDatabase.nomeDB;
        usernameDB = FinestraPrincipale.parametri.configurazioniDatabase.username;
        passwordDB = FinestraPrincipale.parametri.configurazioniDatabase.password;
        try{ 
            connection = DriverManager.getConnection("jdbc:mysql://localhost:" + portaDB +"/"+nomeDB, usernameDB ,passwordDB);
        } catch(Exception e) {System.out.println("Connessione al Database non riuscita."); }
            
    }
    
    
    public List<MigliorGiocatore> caricaClassifica(){ // 01
        List<MigliorGiocatore> ol = new ArrayList();
        try ( 
            PreparedStatement ps = connection.prepareStatement(QUERY_CLASSIFICA); 
        ) {
        ps.setInt(1, FinestraPrincipale.parametri.configurazioniClassifica.massimoNumeroMigliorGiocatori);
        ResultSet result = ps.executeQuery(); 
        while (result.next()) //12
            ol.add(new MigliorGiocatore(result.getString("username"), result.getInt("punteggio")));
        } catch (SQLException e) {System.err.println(e.getMessage());}
        return ol;
    }
    
    public void salvaPartita(String username, double punteggioGiocatore, double punteggioMazziere){  // 02
        try ( 
            PreparedStatement ps = connection.prepareStatement("INSERT INTO partite VALUES(?,?,null,?,?)"); 
        ) {
        ps.setString(1, username);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        ps.setTimestamp(2,timestamp);
        ps.setDouble(3, punteggioGiocatore);
        ps.setDouble(4, punteggioMazziere);
        System.out.println("rows affected: " + ps.executeUpdate());
        } catch (SQLException e) {System.err.println(e.getMessage());}
    }


    public List<GiocatoreAssiduo> caricaGiocatoriAssidui() { // 03
        List<GiocatoreAssiduo> ol = new ArrayList<>();
         try ( 
            Statement query = connection.createStatement(); 
        ) {
        ResultSet result = query.executeQuery(SELECT_UTENTI_ASSIDUI);
            while (result.next()) 
                ol.add(new GiocatoreAssiduo(result.getString("username"), result.getInt("partiteGiocate")));
        } catch (SQLException e) {System.err.println(e.getMessage());}
        return ol;
    }
    private static final String SELECT_UTENTI_ASSIDUI ="SELECT username, count(*) as partiteGiocate from partite group by username limit 9;";   
    private static final String QUERY_CLASSIFICA = "SELECT username, count(*) as punteggio from partite where esito=1 group by username order by punteggio desc limit ? ;";
}


/*
Note:
Classe che gestisce tutte le interazioni tra applicazione e database, contiene
tutti i metodi che possono essere richiamati dall'applicazione per recuperare informazioni.

    01) metodo che prende dal database i giocatori con il punteggio più alto. Il numero di giocatori da selezionare
        viene deciso tramite il parametro di sistema.
    02) metodo che salva nel database il risultato della partita appena conclusa.
    03) metodo che prende dal database i 9 giocatori più assidui e li restituisce sotto forma di ObservableList<PieChart.Data>.
*/
