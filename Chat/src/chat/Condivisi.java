/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;

import static java.lang.Thread.sleep;
import java.net.InetAddress;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author user
 */
public class Condivisi {
    Queue<Pacchetto> in;
    Queue<Pacchetto> out;
    private Interfaccia i;

    private boolean miStoConnettendo;
    private InetAddress  connessioneAttule;
    boolean hoMandatoRichiestaDiConnessione;
    String mioNome,altroNome;
    private Object sincronizzaIn,sincronizzaOut,sincronizzaConnessione,sincronizzaRichiesta,Sincronizzagrafica;
    private static Condivisi instance=null;
    
    public static Condivisi Instance(){
        if(instance==null)
            instance=new Condivisi();
        return instance;
    }
    private Condivisi(){
        hoMandatoRichiestaDiConnessione=false;
        in= new ArrayDeque();
        out= new ArrayDeque();
        sincronizzaConnessione=new Object();
        sincronizzaIn=new Object();
        sincronizzaOut=new Object();
        sincronizzaRichiesta=new Object();
        Sincronizzagrafica=new Object();
        altroNome="";
        mioNome="";
        connessioneAttule=null;
        miStoConnettendo=false;
        i=null;
    }

    
    /*public boolean CiSonoPacchettiDaElaborare(){
        synchronized(sincronizzaIn)
        {
            return in.isEmpty();
        }
    }
    public boolean CiSonoPacchettiDaInviare(){
        synchronized(sincronizzaOut)
        {
            return out.isEmpty();
        }
    }*/
    public void MettiPacchettoOut(Pacchetto p){
        synchronized(sincronizzaOut)
        {
            out.add(p);
        }
    }
    public Pacchetto PrendiPacchettoOut(){
        synchronized(sincronizzaOut)
        {
            return out.poll();
        }
    }
    public void MettiPacchettoIn(Pacchetto p){
        synchronized(sincronizzaIn)
        {
            in.add(p);
        }
    }
    public Pacchetto PrendiPacchettoIn(){
        synchronized(sincronizzaIn)
        {
            return in.poll();
        }
    }
    public StatoConnessione getStatoConnessione(){
        synchronized(sincronizzaConnessione)
        {
            if(connessioneAttule==null)
                if(miStoConnettendo)
                    return StatoConnessione.InAttessa;  
                else
                    return StatoConnessione.DisConnesso;
            else
                return StatoConnessione.Connesso;
        }
    }
    public void SettaStatoConnessione(StatoConnessione s)
    {
        if(s==StatoConnessione.DisConnesso)
            SettaStatoConnessione(s,null);
    }
    public void SettaStatoConnessione(StatoConnessione s,InetAddress address)
    {
        synchronized(sincronizzaConnessione)
        {
            if(s==StatoConnessione.Connesso)
            {
                i.AnnullaVisibile(false);
                connessioneAttule=address;
                miStoConnettendo=false;
            }
            else if(s==StatoConnessione.InAttessa)
            {
                connessioneAttule=address;
                miStoConnettendo=true;
            }
            else if(s==StatoConnessione.DisConnesso)
            {
                i.AnnullaVisibile(false);
                connessioneAttule=null;
                miStoConnettendo=false;
            }
        } 
    }
    public InetAddress getConnessioneAttule() {
        synchronized(sincronizzaConnessione)
        {
            return connessioneAttule;
        }
    }
    public boolean isHoMandatoRichiestaDiConnessione() {
        synchronized(sincronizzaRichiesta){
            return hoMandatoRichiestaDiConnessione;
        }
    }

    public void setHoMandatoRichiestaDiConnessione(boolean hoMandatoRichiestaDiConnessione) {
        synchronized(sincronizzaRichiesta){
            this.hoMandatoRichiestaDiConnessione = hoMandatoRichiestaDiConnessione;
        }
    }
    
    public Interfaccia getI() {
        synchronized(Sincronizzagrafica)
        {
            return i;
        }
    }

    public void setI(Interfaccia i) {
        while(i==null)
            try {
                sleep(1);
            } catch (InterruptedException ex) {
                Logger.getLogger(Condivisi.class.getName()).log(Level.SEVERE, null, ex);
            }
        synchronized(Sincronizzagrafica)
        {
            this.i = i;
        }
    }
}
