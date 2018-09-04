/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EloPisteLaskuriController;

import EloPisteLaskuriModel.PlayAgainstSelfException;

/**
 *
 * @author Lauri
 * 30.1.-12.4.2018
 */
public interface IEPLController {
    public String haePelaajat();
    public int tarkistaPelaajat(String voittaja, String haviaja);
    public void lisaaPelaaja(String nimi);
    public String suoritaOttelu(String voittaja, String haviaja)throws PlayAgainstSelfException;
}
