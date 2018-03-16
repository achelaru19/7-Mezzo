import org.apache.commons.lang3.math.NumberUtils;

public class Carta {
    private String numero;
    private String seme;
    
    public Carta(String n, String s){
        this.numero = n;
        this.seme = s;
    }
    
    public double getValore(){
        if(NumberUtils.isNumber(this.numero))
            return Integer.parseInt(this.numero);
        else
            return 0.5;
                
    }
    
    public String getNome(){
        return numero + "_" + seme;
    }
}
