
import java.io.Serializable;


public class Carta implements Serializable{
    private String numero;
    private String seme;
    
    public Carta(String n, String s){
        this.numero = n;
        this.seme = s;
    }
    
    public double getValore(){
        try {
            return Integer.parseInt(numero); // 01
        } catch (NumberFormatException e) {  // 02
            return 0.5;
        }
                
    }
    
    public String getNome(){
        return numero + "_" + seme;
    }
}


/*
    01) Se la stringa numero è effettivamente un numero (che sarà compreso tra 1 e 7),
        allora restituiamo quel numero;
    02) Altrimenti viene lanciata una eccezione che indica che il numero rappresenta
        una figura, perciò restituiamo mezzo punto.
*/