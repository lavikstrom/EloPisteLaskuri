/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EloPisteLaskuriModel;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Lauri
 * 30.1.-12.4.2018
 */
public class EPLModel implements IEPLModel{
    private Map<String, Integer> pelaajat;
    
    public final int alkuElo = 1000;
    public final int painoKerroin = 15;
    public final int otteluVoitto = 1;
    public final int otteluTappio = 0;
    
    public final double nimttajaMarginaali = 0.0001;
    
    public EPLModel(){
        pelaajat = new HashMap();
        pelaajat.put("Matti", 1000);
        pelaajat.put("Mikko", 1200);
        pelaajat.put("Sami", 950);
        pelaajat.put("Pekka", 850);
        pelaajat.put("Reino", 1020);
    }

    @Override
    public String haePelaajat(String erotin) {
        StringBuilder sb = new StringBuilder();
        int elo;
        
        for(String nimi : pelaajat.keySet()){
            elo = pelaajat.get(nimi);
            sb.append(nimi);
            sb.append(erotin);
            sb.append(elo);
            sb.append('\n');
        }
        
        return sb.toString();
    }
    
    //tämän avulla pelaajalista viedään näkymään
    @Override
    public String haePelaajatModel() {
        return haePelaajat("\t");
    }

    //ohjelma ei käytä tätä, mutta sen avulla tallentaminen on helppoa
    @Override
    public String haePelaajatCSV() {
        return haePelaajat(";");
    }
    
    @Override
    public int tarkistaPelaajat(String voittaja, String haviaja){
        if(pelaajat.containsKey(voittaja) && pelaajat.containsKey(haviaja)){
            return IEPLModel.MOLEMMAT;
        }else if(pelaajat.containsKey(voittaja)){
            return IEPLModel.VOITTAJALOYTYY;
        }else if(pelaajat.containsKey(haviaja)){
            return IEPLModel.HAVIAJALOYTYY;
        }else{
            return IEPLModel.EIKUMPIKAAN;
        }
    }
    
    @Override
    public void lisaaPelaaja(String nimi){
        pelaajat.put(nimi, alkuElo);
    }
    
    @Override
    public void lisaaPelaaja(String nimi, int elo){
        pelaajat.put(nimi, elo);
    }
    
    @Override
    public String suoritaOttelu(String voittaja, String haviaja) throws PlayAgainstSelfException{
        int voittajaElo, haviajaElo;
        double voittajaTN, haviajaTN;

        //itseään vastaan ei voi pelata
        if(voittaja.equals(haviaja)){
            throw new PlayAgainstSelfException();
        }

        //hankitaan pelaajien vanhat eloluvut
        voittajaElo = pelaajat.get(voittaja);
        haviajaElo = pelaajat.get(haviaja);

        //lasketaan pelaajien voittotodennäköisyydet
        voittajaTN = laskeVoittoProsentti(voittajaElo, haviajaElo);
        haviajaTN = laskeVoittoProsentti(haviajaElo, voittajaElo);

        //lasketaan pelaajien uudet eloluvut
        voittajaElo = laskeUusiElo(voittajaElo, voittajaTN, otteluVoitto, painoKerroin);
        haviajaElo = laskeUusiElo(haviajaElo, haviajaTN, otteluTappio, painoKerroin);

        //tallennetaan uudet arvot malllin taulukkoon
        pelaajat.put(voittaja, voittajaElo);
        pelaajat.put(haviaja, haviajaElo);

        //annetaan takaisin uudet arvot
        return haePelaajatModel();
    }
    
    private double laskeVoittoProsentti(int pelaaja, int vastustaja){
        return 1/(1+Math.pow(10.0, (vastustaja-pelaaja)/400));
    }
    
    private int laskeUusiElo(int pelaajaElo, double pelaajaTN, int otteluTulos, int otteluKerroin){
        return (int)Math.round(pelaajaElo + otteluKerroin*(otteluTulos - pelaajaTN));
    }
}
