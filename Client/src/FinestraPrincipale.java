
import javafx.animation.*;
import javafx.application.*;
import javafx.collections.*;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.stage.*;
import javafx.util.*;



public class FinestraPrincipale extends Application{

    private ImageView mazzo_img;
    private Button prendi_bt, stai_bt, start_bt;
    private TextField username_tf; 
    private Partita partita;
    private static HBox boxGiocatore;
    private static HBox boxMazziere; 
    private boolean carteDaScambiare;
    private TableView<MigliorGiocatore> tabellaClassifica = new TableView<>();;
    public ObservableList<MigliorGiocatore> tuplaClassifica; 
    private VBox classifica;
    private Timeline timeline = new Timeline();
    private GestoreDataBase gestoreDB;
    
    @Override
    public void start(Stage stage)  {
        
        gestoreDB = new GestoreDataBase();
         
        boxGiocatore = new HBox(); //440, 640
        boxGiocatore.setSpacing(10.5);
        boxGiocatore.setLayoutY(437);
        boxGiocatore.setLayoutX(100);
        boxGiocatore.setMaxWidth(250);
        
        boxMazziere = new HBox();
        boxMazziere.setSpacing(10.0);
        boxMazziere.setLayoutY(68);
        boxMazziere.setLayoutX(100);
        boxMazziere.setMaxWidth(250);
        
        mazzo_img = new ImageView("file:../../myfiles/other/mazzo2.png"); 
        prendi_bt = new Button("+");
        stai_bt = new Button("✓");
        start_bt = new Button("START");
        username_tf = new TextField();
        
        mazzo_img.setFitHeight(150);
        mazzo_img.setFitWidth(180); 
        mazzo_img.setY(225);  
        mazzo_img.setX(340);  
        
        prendi_bt.setLayoutX(480);
        prendi_bt.setLayoutY(280);
        stai_bt.setLayoutX(520);
        stai_bt.setLayoutY(280);
        start_bt.setLayoutX(175);
        start_bt.setLayoutY(30);
        
        prendi_bt.setOnAction((ActionEvent ev)->{prendi();});
        stai_bt.setOnAction((ActionEvent ev)->{stai();});
        start_bt.setOnAction((ActionEvent ev)->{inizializzaPartita();});
       
        
        username_tf.setLayoutX(15);
        username_tf.setLayoutY(30);
        
        TableColumn usernameColumn = new TableColumn("USERNAME"); 
        TableColumn punteggioColumn = new TableColumn("PUNTEGGIO"); 
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username")); 
        punteggioColumn.setCellValueFactory(new PropertyValueFactory<>("punteggio")); 
        
        tuplaClassifica = gestoreDB.caricaClassificaPredefiniti();
     
        
        tabellaClassifica.setItems(tuplaClassifica);
        tabellaClassifica.getColumns().addAll(usernameColumn, punteggioColumn);
        
        classifica = new VBox();
        
        classifica.getChildren().add(tabellaClassifica);
        classifica.setLayoutX(930);
        classifica.setLayoutY(30);
        
        Color backgroundColor = Color.web("#277345");
        

        
        Group root = new Group(mazzo_img, prendi_bt, stai_bt, start_bt, username_tf, boxGiocatore, boxMazziere, classifica);
        Scene scene = new Scene(root, 1200, 600, backgroundColor);
        stage.setTitle("7 e mezzo");
        stage.setScene(scene);
        stage.show();
        
    }
    
    public void prendi(){
        Carta cartaPresa = partita.prendiCarta();
        aggiungiCartaGiocatore(cartaPresa.getNome());
        if(partita.getPartitaFinita()){
            System.out.println("Hai superato i 7.5");
            giocataMazziere();
        }
    }
    
    public void stai(){
        KeyFrame keyFrame = new KeyFrame(Duration.millis(1500), event -> {

            if(!partita.stai()) {
                if(carteDaScambiare)
                    giocataMazziere();
                else
                    timeline.stop();
            } else {
                giocataMazziere();
            }
        });

        timeline.getKeyFrames().add(keyFrame);
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play(); 
    };

    private void giocataMazziere(){
        
        if(carteDaScambiare){
            boxMazziere.getChildren().clear();
            Carta carta = partita.sostituisciCartaMazziere();
            aggiungiCarta(boxMazziere, carta.getNome());
            carteDaScambiare = false;
        } else {
            Carta cartaEstratta = partita.aggiungiCartaMazziere();
            aggiungiCartaMazziere(cartaEstratta.getNome());
        }
    };
    
    private void inizializzaPartita() {
       this.partita = new Partita(username_tf.getText());
       this.boxGiocatore.getChildren().clear();
       this.boxMazziere.getChildren().clear();
       partita.resetta();
       aggiungiCartaMazziere("retro");
       aggiungiCartaGiocatore(partita.prendiCarta().getNome());
       timeline = new Timeline();
       carteDaScambiare = true;
    }
    
    private void aggiungiCartaMazziere(String nomeCarta){
        aggiungiCarta(this.boxMazziere, nomeCarta);
    }
    
    private void aggiungiCartaGiocatore(String nomeCarta){
        aggiungiCarta(this.boxGiocatore, nomeCarta);
    }
    
     public void aggiungiCarta(HBox hbox, String nomeCarta){
        String appMain = System.getProperty("user.dir");
        String url ="file:" + appMain + "/myfiles/carte/" + nomeCarta + ".jpg";
        System.out.println(url);
        ImageView immagineCarta = new ImageView(url);
        immagineCarta.setPreserveRatio(true);
        immagineCarta.setFitWidth(80);
        hbox.getChildren().add(immagineCarta);
         
    }
    
     public static void main(String[] args){
         launch(args);
     }


}
