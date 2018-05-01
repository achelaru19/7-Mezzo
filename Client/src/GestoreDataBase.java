
import java.sql.*;
import javafx.collections.*;
import java.util.*;
import javafx.scene.chart.PieChart;

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
    
    public void salvaPartita(String username, double punteggioGiocatore, double punteggioMazziere){
        try ( 
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/setteemezzo", "root","");
            PreparedStatement ps = connection.prepareStatement("INSERT INTO partite VALUES(?,?,?,?,?)"); 
        ) {
        ps.setString(1, username);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        //String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
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
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/setteemezzo", "root","");
            Statement query = connection.createStatement(); //10
        ) {
        ResultSet result = query.executeQuery("SELECT username, count(*) as partiteGiocate\n" +
                                                "from partite \n" +
                                                "group by username;");    
            while (result.next()) //12
                ol.add(new PieChart.Data(result.getString("username"), result.getInt("partiteGiocate")));
        } catch (SQLException e) {System.err.println(e.getMessage());}
        return ol;
    }
    
}
