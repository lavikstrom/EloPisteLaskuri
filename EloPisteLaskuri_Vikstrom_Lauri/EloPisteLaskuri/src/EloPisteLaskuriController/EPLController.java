/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EloPisteLaskuriController;

import EloPisteLaskuriModel.IEPLModel;
import EloPisteLaskuriModel.PlayAgainstSelfException;
import EloPisteLaskuriView.IEPLView;

/**
 *
 * @author Lauri
 * 30.1.-12.4.2018
 */
public class EPLController implements IEPLController{
    
    private IEPLModel model;
    private IEPLView view;
    
    public EPLController(){
        model = new EloPisteLaskuriModel.EPLModel();
        view = new EloPisteLaskuriView.EPLView();
        view.rekisteroiOhjain(this);
    } 

    @Override
    public String haePelaajat() {
        return model.haePelaajatModel();
    }

    @Override
    public int tarkistaPelaajat(String voittaja, String haviaja) {
        return model.tarkistaPelaajat(voittaja, haviaja);
    }

    @Override
    public void lisaaPelaaja(String nimi) {
        model.lisaaPelaaja(nimi);
    }

    @Override
    public String suoritaOttelu(String voittaja, String haviaja) throws PlayAgainstSelfException{
        String tulos;
        tulos = model.suoritaOttelu(voittaja, haviaja);
        return tulos;
    }
}
