
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
            return Integer.parseInt(numero);
        } catch (NumberFormatException e) {
            return 0.5;
        }
                
    }
    
    public String getNome(){
        return numero + "_" + seme;
    }
}
