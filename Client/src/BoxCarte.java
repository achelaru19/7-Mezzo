
import javafx.scene.image.*;
import javafx.scene.layout.*;


public class BoxCarte {
   
    public static void aggiungiCarta(HBox hbox, String nomeCarta){
        String mainDir = System.getProperty("user.dir");
        String url ="file:" + mainDir + "/myfiles/carte/" + nomeCarta + ".jpg";
        System.out.println(url);
        ImageView immagineCarta = new ImageView(url);
        immagineCarta.setPreserveRatio(true);
        immagineCarta.setFitWidth(80);
        hbox.getChildren().add(immagineCarta);
         
    }
    
    public static void sostituisciCartaMazziere(HBox carteMazziere, Carta carta){
        //carteMazziere.
    }
    
}
