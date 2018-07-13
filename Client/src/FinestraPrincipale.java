
import java.net.*;
import java.sql.*;
import java.util.*;
import javafx.animation.*;
import javafx.application.*;
import javafx.collections.*;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.text.*;
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
    private boolean giocoInCorso;
    private final TableView<MigliorGiocatore> tabellaClassifica = new TableView<>();;
    private ObservableList<MigliorGiocatore> tuplaClassifica; 
    private VBox classifica;
    private Timeline timeline = new Timeline();
    private GestoreDataBase gestoreDB;
    private final VBox vboxGrafico = new VBox();
    private PieChart grafico;
    private ManagerParametriConfigurazioni managerConfigurazioni;
    private ManagerEventiLogXML managerLog; 
    public static ConfigurazioniXML parametri;
    private final ManagerCacheBinaria managerCache = new ManagerCacheBinaria();
    private Text  stringaVittoria = new Text ("Hai vinto!");
    private Text  stringaSconfitta = new Text ("Hai perso!");
    
    @Override
    public void start(Stage stage)  {
        
        managerConfigurazioni = new ManagerParametriConfigurazioni("Configurazioni.xml","Configurazioni.xsd");
        parametri = managerConfigurazioni.inizializzaParametriConfigurazione();
        
        gestoreDB = new GestoreDataBase();
        
        inizializzaElementi();
  
        Group root = new Group(mazzo_img, prendi_bt, stai_bt, start_bt, username_tf, stringaVittoria,
                                  stringaSconfitta, boxGiocatore, boxMazziere, classifica, vboxGrafico);
        Scene scene = new Scene(root, 1350, 650, Color.web(parametri.configurazioniStylingFinestraPrincipale.backgroundColor));
        scene.getStylesheets().add(this.getClass().getResource("stylesheet.css").toExternalForm());
        stage.setOnCloseRequest(ev ->{salvaCacheBinaria();generaEventoLogXML("CHIUSURA");});
        stage.setTitle("7 e mezzo");
        stage.setScene(scene);
        stage.show();
        
        caricaEventualeCache();
        managerLog = new ManagerEventiLogXML();
        generaEventoLogXML("APERTURA");
    }
    
    private void prendi(){
        Carta cartaPresa = partita.prendiCarta();
        aggiungiCartaGiocatore(cartaPresa.getNome());
        if(parametri != null)
            System.out.println(parametri.configurazioniStylingFinestraPrincipale.backgroundColor);
        if(partita.getPartitaFinita()){
            System.out.println("Hai superato i 7.5");
            giocataMazziere();
            giocoInCorso = false;
            System.out.println("FINE PARTITA");
            mostraSconfitta();
            gestoreDB.salvaPartita(username_tf.getText(), partita.getPunteggioGiocatore(), partita.getPunteggioMazziere());
        }
    }
    
    private void stai(){
        KeyFrame keyFrame;
        keyFrame = new KeyFrame(Duration.millis(1500),( ActionEvent event) -> {
            if(!partita.stai()) {
                if(carteDaScambiare){
                    giocataMazziere();   
                    if(partita.vittoria())
                          mostraVittoria();
                    else
                          mostraSconfitta();
                }//**************** CONTROLLA *******************//
                else{
                    if(partita.vittoria())
                          mostraVittoria();
                    else
                          mostraSconfitta();
                    System.out.println("FINE PARTITA");
                }
                gestoreDB.salvaPartita(username_tf.getText(), partita.getPunteggioGiocatore(), partita.getPunteggioMazziere());
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
            giocoInCorso = false;
        } else {
            Carta cartaEstratta = partita.aggiungiCartaMazziere();
            aggiungiCartaMazziere(cartaEstratta.getNome());
        }
    };
    
    private void inizializzaPartita() {
       partita = new Partita(username_tf.getText());
       boxGiocatore.getChildren().clear();
       boxMazziere.getChildren().clear();
       partita.resetta();
       aggiungiCartaMazziere("retro");
       aggiungiCartaGiocatore(partita.prendiCarta().getNome());
       timeline = new Timeline();
       carteDaScambiare = true;
       giocoInCorso = true;
       inizializzaTextVittoriaSconfitta();
       managerCache.cancellaCacheBinaria();
    }
    
    private void aggiungiCartaMazziere(String nomeCarta){
        aggiungiCarta(boxMazziere, nomeCarta);
    }
    
    private void aggiungiCartaGiocatore(String nomeCarta){
        aggiungiCarta(boxGiocatore, nomeCarta);
    }
    
     public void aggiungiCarta(HBox hbox, String nomeCarta){
        String appMain = System.getProperty("user.dir");
        String url ="file:" + appMain + "/myfiles/carte/" + nomeCarta + ".jpg";
        System.out.println(url);
        ImageView immagineCarta = new ImageView(url);
        immagineCarta.getStyleClass().add("cartaPescata");
        HBox imageContainer = new HBox();
        imageContainer.getStyleClass().add("cartaPescata");
        imageContainer.getChildren().add(immagineCarta);
        hbox.getChildren().add(imageContainer);
    }
     
    private void salvaCacheBinaria(){
        if(giocoInCorso){
            List<Carta> cG = partita.carteGiocatore();
            Carta cM = partita.carteMazziere().get(0);
            managerCache.salvaCacheBinaria(username_tf.getText(), cG, cM);
        }
    }
    
    private void caricaEventualeCache() {
        CacheBinaria cache = managerCache.prelevaCacheBinaria();
        if(cache != null){
            if(confrontaTimestamp(cache.tempo) <= parametri.configurazioniTemporali.oreIndietroCache){

                partita = new Partita(cache.username);
                boxGiocatore.getChildren().clear();
                boxMazziere.getChildren().clear();
                System.out.println(cache.cartaMazziere.getNome());
                partita.aggiungiCartaMazziere(cache.cartaMazziere);
                aggiungiCartaMazziere("retro");
                for(Carta c : cache.carteGiocatore){
                    partita.aggiungiCartaGiocatore(c);
                    aggiungiCartaGiocatore(c.getNome());
                }
                username_tf.setText(cache.username);
                
                timeline = new Timeline();
                carteDaScambiare = true;
                giocoInCorso = true;
                managerCache.cancellaCacheBinaria();
            }
        }
        else
            System.out.println("Non è stato possibile caricare la cache.");
    }
    
    private long confrontaTimestamp(Timestamp time){
        long milliseconds1 = time.getTime();
        long milliseconds2 = System.currentTimeMillis();

        long diff = milliseconds2 - milliseconds1;
        long differenzaOre = diff / (60 * 60 * 1000);

        return differenzaOre;
    }
    
    private void mostraVittoria(){
        stringaVittoria.setFill(Color.GOLD);
        stringaVittoria.setFont(Font.loadFont("file:../../myfiles/font/ProximaNovaSoft-Regular.ttf", 120));
        stringaVittoria.setStyle("-fx-font: 100px ");
        ScaleTransition st = new ScaleTransition(Duration.millis(2000), stringaVittoria);
        st.setByX(2.3f);
        st.setByY(2.3f);
        st.setCycleCount(Timeline.INDEFINITE);
        st.setAutoReverse(true);
 
        st.play();  
    }
    
    private void mostraSconfitta(){
        stringaSconfitta.setFill(Color.RED);
        stringaSconfitta.setFont(Font.loadFont("file:../../myfiles/font/ProximaNovaSoft-Regular.ttf", 120));
        stringaSconfitta.setStyle("-fx-font: 100px ");
        ScaleTransition st = new ScaleTransition(Duration.millis(2000), stringaSconfitta);
        st.setByX(1.5f);
        st.setByY(1.5f);
        st.setCycleCount(Timeline.INDEFINITE);
        st.setAutoReverse(true);
 
        st.play();  
    }

    private void generaEventoLogXML(String etichetta){ 
        String utente = null;
        if(giocoInCorso)
            utente = username_tf.getText();
        else{
            try(final DatagramSocket socket = new DatagramSocket()){ //(10)
                socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
                utente = (String) socket.getLocalAddress().getHostAddress();
            }catch(Exception e) {System.out.println(e.getMessage());}
        }   
        managerLog.inviaEventoLogXML(parametri.configurazioniServer.ip, parametri.configurazioniServer.porta, etichetta, utente);
    }    

        
    private void inizializzaElementi(){
         
        inizializzaBoxCarte(); 
        inizializzaBottoniEImmagini();
        inizializzaClassifica();
        inizializzaPieChart();
        inizializzaTextVittoriaSconfitta();
    
    }    
        
    private void inizializzaClassifica(){
        
        Label titoloClassifica = new Label("Classifica");
        titoloClassifica.setFont(new Font("Arial", 23));
        titoloClassifica.setTranslateX(80);
        titoloClassifica.setStyle("-fx-font-weight: bold; -fx-text-fill:white; "
                + " -fx-font-size: 23;");
        
        TableColumn usernameColumn = new TableColumn("USERNAME"); 
        TableColumn punteggioColumn = new TableColumn("PUNTEGGIO"); 
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username")); 
        punteggioColumn.setCellValueFactory(new PropertyValueFactory<>("punteggio")); 
        
        tuplaClassifica = gestoreDB.caricaClassifica();
        
        tabellaClassifica.setItems(tuplaClassifica);
        tabellaClassifica.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tabellaClassifica.getColumns().addAll(usernameColumn, punteggioColumn);
        
        classifica = new VBox();
        classifica.getChildren().add(titoloClassifica);
        classifica.getChildren().add(tabellaClassifica);
        classifica.setLayoutX(1075);
        classifica.setLayoutY(20);
        classifica.setMaxHeight(300);
        
        
    }   
    
    private void inizializzaPieChart(){
        
        ObservableList<PieChart.Data> datiGrafico; 
        datiGrafico = FXCollections.observableArrayList();
        datiGrafico.addAll(gestoreDB.caricaGiocatoriAssidui());
        grafico = new PieChart(datiGrafico);   
        grafico.setTitle("Giocatori Assidui");
        grafico.setLegendVisible(true);
        grafico.setLabelsVisible(false); 
        
        vboxGrafico.setLayoutX(1050);
        vboxGrafico.setLayoutY(350);
        vboxGrafico.setMaxSize(300, 300);
        vboxGrafico.getChildren().addAll(grafico);
        
    }
    
    private void inizializzaBottoniEImmagini(){
        
        mazzo_img = new ImageView("file:../../myfiles/other/mazzo.png"); 
        prendi_bt = new Button("✚");
        stai_bt = new Button("✓");
        start_bt = new Button("START");
        username_tf = new TextField();
        
        mazzo_img.setY(270);  
        mazzo_img.setX(340);  
        
        prendi_bt.setLayoutX(480);
        prendi_bt.setLayoutY(325);
        stai_bt.setLayoutX(520);
        stai_bt.setLayoutY(325);
        start_bt.setLayoutX(175);
        start_bt.setLayoutY(30);
        
        prendi_bt.getStyleClass().add("prendi-button");
        stai_bt.getStyleClass().add("stai-button");
        start_bt.getStyleClass().add("start-button");
        
        
        prendi_bt.setOnAction((ActionEvent ev)->{prendi();generaEventoLogXML("PRENDI");});
        stai_bt.setOnAction((ActionEvent ev)->{stai();generaEventoLogXML("STAI");});
        start_bt.setOnAction((ActionEvent ev)->{inizializzaPartita();generaEventoLogXML("START");});
        
        username_tf.setLayoutX(15);
        username_tf.setLayoutY(30);
        
    }
    
    private void inizializzaBoxCarte(){
        
        boxGiocatore = new HBox(); 
        boxGiocatore.setSpacing(10.5);
        boxGiocatore.setLayoutY(487);
        boxGiocatore.setLayoutX(100);
        boxGiocatore.setMaxWidth(250);
        
        boxMazziere = new HBox();
        boxMazziere.setSpacing(10.0);
        boxMazziere.setLayoutY(97);
        boxMazziere.setLayoutX(100);
        boxMazziere.setMaxWidth(250);
             
    }

    
    private void inizializzaTextVittoriaSconfitta(){

        stringaSconfitta.setFill(Color.TRANSPARENT);
        stringaSconfitta.setX(760);
        stringaSconfitta.setY(330);
        stringaSconfitta.setFont(Font.font("Georgia", 45));
        
        stringaVittoria.setFill(Color.TRANSPARENT);
        stringaVittoria.setX(760);
        stringaVittoria.setY(330);
        stringaVittoria.setFont(Font.font("Georgia", 45));
    }
     public static void main(String[] args){
         launch(args);
     }
}

/*

(10) Soluzione per trovare l'IP usato dall'applicazione.  
     Il codice è stato preso da https://stackoverflow.com/questions/9481865/getting-the-ip-address-of-the-current-machine-using-java



*/