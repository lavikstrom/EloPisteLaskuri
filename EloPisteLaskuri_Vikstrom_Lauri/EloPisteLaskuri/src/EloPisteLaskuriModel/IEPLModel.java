/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EloPisteLaskuriModel;

/**
 *
 * @author Lauri
 * 30.1.-12.4.2018
 */
public interface IEPLModel {
    public static final int MOLEMMAT = 0;
    public static final int VOITTAJALOYTYY = 1;
    public static final int HAVIAJALOYTYY = 2;
    public static final int EIKUMPIKAAN = 3;
    
    public String haePelaajat(String erotin);
    public String haePelaajatModel();
    public String haePelaajatCSV();
    public int tarkistaPelaajat(String voittaja, String haviaja);
    public void lisaaPelaaja(String nimi);
    public void lisaaPelaaja(String nimi, int elo);
    public String suoritaOttelu(String voittaja, String haviaja)throws PlayAgainstSelfException;
}
