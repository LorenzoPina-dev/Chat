/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;

import java.net.InetAddress;
import java.util.ArrayDeque;
import java.util.Queue;

/**
 *
 * @author user
 */
public class Condivisi {
    Queue<Pacchetto> in;
    Queue<Pacchetto> out;
    private boolean miStoConnettendo;
    private InetAddress  connessioneAttule;
    boolean hoMandatoRichiestaDiConnessione;
    String mioNome,altroNome;
    private Object sincronizzaIn,sincronizzaOut,sincronizzaConnessione,sincronizzaRichiesta;
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
        altroNome="";
        mioNome="";
        SettaStatoConnessione(StatoConnessione.DisConnesso);
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
            if(connessioneAttule!=null)
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
}
