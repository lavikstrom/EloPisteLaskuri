/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EloPisteLaskuriView;

import EloPisteLaskuriController.IEPLController;
import EloPisteLaskuriModel.IEPLModel;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Label;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Lauri
 * 30.1.-12.4.2018
 */
public class EPLView implements IEPLView, ActionListener{

    private IEPLController controller;
    
    private HashMap pelaajat;
        
    private JFrame ePLFrame;
    private Label voittajaTeksti;
    private Label haviajaTeksti;
    private TextField voittajaSyotto;
    private TextField haviajaSyotto;
    private Button tallenna;
    private TextArea pelaajaLista;
    
    public final int nimiPituus = 20;
    
    private final Object[] options = {"Kyllä","Ei",};
    
    public EPLView(){
        ePLFrame = new JFrame();
        ePLFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ePLFrame.setTitle("Elo-pistelaskuri");
        ePLFrame.setSize(600,500);
        ePLFrame.setLocationRelativeTo(null);
        ePLFrame.setLayout(new FlowLayout());
        ePLFrame.setVisible(true);
        
        voittajaTeksti = new Label("Voittaja:", Label.LEFT);
        haviajaTeksti = new Label("Häviäjä:", Label.LEFT);
        
        voittajaSyotto = new TextField(nimiPituus);
        voittajaSyotto.setEditable(true);
        haviajaSyotto = new TextField(nimiPituus);
        haviajaSyotto.setEditable(true);
        
        tallenna = new Button("Tallenna");
        tallenna.addActionListener(this);
        
        pelaajaLista = new TextArea();
        
        ePLFrame.add(voittajaTeksti);
        ePLFrame.add(voittajaSyotto);
        ePLFrame.add(haviajaTeksti);
        ePLFrame.add(haviajaSyotto);
        ePLFrame.add(tallenna);
        ePLFrame.add(pelaajaLista);
    }
    
    @Override
    public void rekisteroiOhjain(IEPLController controller) {
        this.controller = controller;
        pelaajaLista.setText(this.controller.haePelaajat());
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        String voittaja, haviaja, uusiLista;
        int tarkistus;
        boolean kaikkiPelaajat = false;
        
        voittaja = voittajaSyotto.getText();
        haviaja = haviajaSyotto.getText();
        
        if(voittaja == null || haviaja == null){
            luoIlmoitus("ainakin toinen kentistä on tyhjä.");
        }else if(voittaja.trim().isEmpty() || haviaja.trim().isEmpty()){
            luoIlmoitus("ainakin toinen kentistä on tyhjä.");
        }else{

            tarkistus = controller.tarkistaPelaajat(voittaja, haviaja);

            switch (tarkistus) {
                case IEPLModel.EIKUMPIKAAN:
                    {
                        int i;
                        i = kysyLuonti(voittaja, haviaja);
                        if(i == 0){
                            controller.lisaaPelaaja(voittaja);
                            controller.lisaaPelaaja(haviaja);
                            kaikkiPelaajat = true;
                        }       break;
                    }
                case IEPLModel.VOITTAJALOYTYY:
                    {
                        int i;
                        i = kysyLuonti(haviaja);
                        if(i == 0){
                            controller.lisaaPelaaja(haviaja);
                            kaikkiPelaajat = true;
                        }       break;
                    }
                case IEPLModel.HAVIAJALOYTYY:
                    {
                        int i;
                        i = kysyLuonti(voittaja);
                        if(i == 0){
                            controller.lisaaPelaaja(voittaja);
                            kaikkiPelaajat = true;
                        }       break;
                    }
                case IEPLModel.MOLEMMAT:
                    kaikkiPelaajat = true;
                default:
                    break;
            }
            if(!kaikkiPelaajat){
                luoIlmoitus("kaikkia pelaajia ei ole tietokannassa.");
            }else{
                try{
                    uusiLista = controller.suoritaOttelu(voittaja, haviaja);
                    pelaajaLista.setText(uusiLista);
                }catch(Exception e){
                    luoIlmoitus(e.getMessage());
                }
            }
        }
    }  
    
    private int kysyLuonti(String nimi1, String nimi2){
        int i = JOptionPane.showOptionDialog(ePLFrame,
            "Pelaajia \"" + nimi1 + "\" ja \"" + nimi2 + "\" ei ole tietokannassa. Luodaanko kyseiset pelaajat?",
            "Uusi pelaaja",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[1]);
        
        return i;
    }
    
    private int kysyLuonti(String nimi){
        int i = JOptionPane.showOptionDialog(ePLFrame,
            "Pelaajaa \"" + nimi + "\" ei ole tietokannassa. Luodaanko kyseinen pelaaja?",
            "Uusi pelaaja",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[1]);
        
        return i;
    }
    
    private void luoIlmoitus(String viesti){
        JOptionPane.showMessageDialog(ePLFrame,
                "Elo-laskua ei suoritettu: " + viesti,
                "Lasku keskeytetty",
                JOptionPane.INFORMATION_MESSAGE);
    }
}
