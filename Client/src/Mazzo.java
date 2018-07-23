import java.util.*;


public class Mazzo {
    
    private List<Carta> mazzo;
    
    public Mazzo(){
        
        mazzo = new ArrayList<>();
        inizializza();
        
    }
    
    private void inizializza(){
        
        String[] semi = {"denari","bastoni","coppe","spade"};
        
        if(mazzo.size() != 0){
            mazzo.clear();
        }
        
        for(int i = 0; i < 4; i++){
            for(int j = 1; j <= 7; j++){
                Carta c = new Carta(Integer.toString(j), semi[i]);
                mazzo.add(c);
            }
            for(int j = 0; j < 3; j++){
                Carta c;
                switch(j) {
                    case 0: c = new Carta("fante", semi[i]);
                            mazzo.add(c);
                            break;
                    case 1: c = new Carta("cavallo", semi[i]);
                            mazzo.add(c);
                            break;
                    case 2: c = new Carta("re", semi[i]);
                            mazzo.add(c);
                            break;
                }
            }
            
        }
    }
    
    public void stampa(){
        for (Carta carta : mazzo){
            System.out.println(carta.getNome());
        }
    }
  
    public void mescola(){
        Collections.shuffle(mazzo);
    }
    
    public Carta estraiCarta(){
        Carta cartaEstratta = mazzo.get(0);
        mazzo.remove(0);
        return cartaEstratta;
    }
    
    public void estraiDeterminataCarta(Carta c){
        for(int i = 0; i < mazzo.size(); i++){
            if(mazzo.get(i).getNome().equals(c.getNome())){
                mazzo.remove(i);
                break;
            }
        }
    }
    
    public int getSize(){
        return mazzo.size();
    }
    
}

/*
    Questa classe rappresenta un mazzo di carte napoletane per implementare
    il gioco Sette e mezzo. 
    Contiene 40 carte: 4 semi (denari, bastoni, coppe, spade) di cui
    7 numeri e 3 figure(re, fante, cavallo).
*/