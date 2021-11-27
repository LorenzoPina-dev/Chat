/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;

import java.net.InetAddress;

/**
 *
 * @author user
 */
public class Pacchetto {
    String scelta;
    String dati;
    InetAddress address;
    public Pacchetto(String scelta, String dati) {
        this.scelta = scelta;
        this.dati = dati;
    }

    public Pacchetto(String csv) {
        int index=csv.indexOf(";");
        scelta= csv.substring(0, index);
        dati=csv.substring(index+1, csv.length());
    }
    public String ToCsv(){
        return scelta+";"+dati;
    }
}
