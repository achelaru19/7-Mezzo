
import java.sql.*;
import javafx.collections.*;
import javafx.scene.chart.*;

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
    
    
    public ObservableList<MigliorGiocatore> caricaClassifica(){
        ObservableList<MigliorGiocatore> ol = FXCollections.observableArrayList();
        try ( 
            PreparedStatement ps = connection.prepareStatement(QUERY_CLASSIFICA); //10
        ) {
        ps.setInt(1, FinestraPrincipale.parametri.configurazioniClassifica.massimoNumeroMigliorGiocatori);
        ResultSet result = ps.executeQuery(); 
        while (result.next()) //12
            ol.add(new MigliorGiocatore(result.getString("username"), result.getInt("punteggio")));
        } catch (SQLException e) {System.err.println(e.getMessage());}
        return ol;
    }
    
    public void salvaPartita(String username, double punteggioGiocatore, double punteggioMazziere){
        try ( 
            PreparedStatement ps = connection.prepareStatement("INSERT INTO partite VALUES(?,?,?,?,?)"); 
        ) {
        ps.setString(1, username);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        ps.setTimestamp(2,timestamp);
        int esito = esitoGioco(punteggioGiocatore, punteggioMazziere);
        ps.setInt(3, esito);
        ps.setDouble(4, punteggioGiocatore);
        ps.setDouble(5, punteggioMazziere);
        System.out.println("rows affected: " + ps.executeUpdate());
        } catch (SQLException e) {System.err.println(e.getMessage());}
    }

    private int esitoGioco(double punteggioGiocatore, double punteggioMazziere) {
        if(punteggioGiocatore > 7.5)
             return 0;
        if(punteggioMazziere > 7.5)
            return 1;
        if(punteggioGiocatore > punteggioMazziere)
               return 1;
        return 0;
    }

    public ObservableList<PieChart.Data> caricaGiocatoriAssidui() {
        ObservableList<PieChart.Data> ol = FXCollections.observableArrayList();
         try ( 
            Statement query = connection.createStatement(); //10
        ) {
        ResultSet result = query.executeQuery(SELECT_UTENTI_ASSIDUI);
            while (result.next()) //12
                ol.add(new PieChart.Data(result.getString("username"), result.getInt("partiteGiocate")));
        } catch (SQLException e) {System.err.println(e.getMessage());}
        return ol;
    }
    private static final String SELECT_UTENTI_ASSIDUI ="SELECT username, count(*) as partiteGiocate from partite group by username limit 9;";   
    private static final String QUERY_CLASSIFICA = "SELECT username, count(*) as punteggio from partite where esito=1 group by username order by punteggio desc limit ? ;";
}
