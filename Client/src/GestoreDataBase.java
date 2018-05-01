
import java.sql.*;
import javafx.collections.*;


public class GestoreDataBase {
    private final int portaDB;
    private final String usernameDB;
    private final String passwordDB;
    
    
    public GestoreDataBase(){
        //DA CAMBIARE
        portaDB = 3306;
        usernameDB = "root";
        passwordDB = "";
    }
    
    /*
    public ObservableList<MigliorGiocatore> caricaClassificaPredefiniti(){
      
        ObservableList<MigliorGiocatore> ol = FXCollections.observableArrayList( //8
        new MigliorGiocatore("jacob@london.uk", 10),
        new MigliorGiocatore("isabelle@paris.fr",20),
        new MigliorGiocatore("ethan@instanbul.tr",30),
        new MigliorGiocatore("emma@roma.it", 40),
        new MigliorGiocatore("fds@london.uk", 50),
        new MigliorGiocatore("sfdfdsf@paris.fr",60),
        new MigliorGiocatore("jhgj@instanbul.tr", 70),
        new MigliorGiocatore("bvvcb@roma.it", 80),
        new MigliorGiocatore("cvzxc@london.uk", 90),
        new MigliorGiocatore("fdsfds@paris.fr",100),
        new MigliorGiocatore("ddcvsd@instanbul.tr", 120),
        new MigliorGiocatore("jughg@roma.it", 110)
       );
    
    return ol;
    } */
    
    public ObservableList<MigliorGiocatore> caricaClassifica(){
        ObservableList<MigliorGiocatore> ol = FXCollections.observableArrayList();
        try ( 
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/setteemezzo", "root","");
            Statement query = connection.createStatement(); //10
        ) {
        ResultSet result = query.executeQuery("SELECT username, count(*) as punteggio\n" +
                                                "from partite \n" +
                                                "where esito=1\n" +
                                                "group by username\n" +
                                                "order by punteggio desc\n" +
                                                "limit 10;"); 
        while (result.next()) //12
            ol.add(new MigliorGiocatore(result.getString("username"), result.getInt("punteggio")));
        } catch (SQLException e) {System.err.println(e.getMessage());}
        return ol;
    }
    
    public void salvaPartita(){
        
    }
    
}
