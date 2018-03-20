
import java.util.logging.*;
import javafx.application.*;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.stage.*;



public class FinestraPrincipale extends Application{

    private ImageView mazzo_img;
    private Button prendi_bt, stai_bt, start_bt;
    private TextField username_tf; 
    private Partita partita;
    public static HBox boxGiocatore;
    public static HBox boxMazziere; 
    
    @Override
    public void start(Stage stage)  {
         
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
        
        Color backgroundColor = Color.web("#277345");
        

        
        Group root = new Group(mazzo_img, prendi_bt, stai_bt, start_bt, username_tf, boxGiocatore, boxMazziere);
        Scene scene = new Scene(root, 1200, 600, backgroundColor);
        stage.setTitle("7 e mezzo");
        stage.setScene(scene);
        stage.show();
        
    }
    
    public void prendi(){
        Carta cartaPresa = partita.prendiCarta();
        aggiungiCarta(this.boxGiocatore , cartaPresa.getNome());
        if(partita.getPartitaFinita()){
            System.out.println("Hai superato i 7.5");
            inizializzaPartita();
        }
    }
    
    public void stai(){
        /*** AGGIORNA CARTA MAZZIERE ***/
        boxMazziere.getChildren().clear();
        Carta carta = partita.sostituisciCartaMazziere();
        aggiungiCarta(boxMazziere, carta.getNome());
        
        
        try {
            Thread.sleep(7000);
        } catch (InterruptedException ex) {
            Logger.getLogger(FinestraPrincipale.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        while(!partita.getPartitaFinita()){
           if(partita.stai()){
               Carta cartaEstratta = partita.aggiungiCartaMazziere();
               aggiungiCarta(this.boxMazziere , cartaEstratta.getNome());
           }
        } 
        inizializzaPartita();
    }

    private void inizializzaPartita() {
       this.partita = new Partita(username_tf.getText());
       this.boxGiocatore.getChildren().clear();
       this.boxMazziere.getChildren().clear();
       partita.resetta();
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
