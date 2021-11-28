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
public class ThreadLogica extends Thread{
    public ThreadLogica() {
    }
    
    @Override
    public void run(){
        while(true)
        {
            Pacchetto daElaborare=Condivisi.Instance().PrendiPacchettoIn();
            if(daElaborare!=null)
            {
                switch(daElaborare.scelta)
                {
                    case "c":
                        Condivisi.Instance().setHoMandatoRichiestaDiConnessione(false);
                        StatoConnessione s= Condivisi.Instance().getStatoConnessione();
                        if(s==StatoConnessione.InAttessa)
                        { 
                            Boolean ris=ChiediConferma(Condivisi.Instance().getConnessioneAttule(),daElaborare.address);
                            if(ris)
                            {
                                Condivisi.Instance().SettaStatoConnessione(StatoConnessione.InAttessa,daElaborare.address);
                                Condivisi.Instance().altroNome=daElaborare.dati;
                                Invia(new Pacchetto("y",Condivisi.Instance().mioNome),daElaborare.address);
                            }
                            else
                                Invia(new Pacchetto("n",""),daElaborare.address);
                        }
                        else if(s==StatoConnessione.DisConnesso)
                        {
                            Invia(new Pacchetto("y",Condivisi.Instance().mioNome),daElaborare.address);
                            Condivisi.Instance().altroNome=daElaborare.dati;
                            Condivisi.Instance().SettaStatoConnessione(StatoConnessione.InAttessa,daElaborare.address);
                        }
                        else
                            Invia(new Pacchetto("n",""),daElaborare.address);
                        break;
                    case "d":
                        Condivisi.Instance().SettaStatoConnessione(StatoConnessione.DisConnesso);
                        Condivisi.Instance().altroNome="";
                        break;
                    case "m":
                        if(Condivisi.Instance().getStatoConnessione()== StatoConnessione.Connesso  && Condivisi.Instance().getConnessioneAttule()==daElaborare.address)
                            MostraMessaggio(daElaborare.dati);
                        else
                            Invia(new Pacchetto("d",""),daElaborare.address);
                        break;
                    case "y":
                        StatoConnessione scon=Condivisi.Instance().getStatoConnessione();
                        if(Condivisi.Instance().isHoMandatoRichiestaDiConnessione())    //sono il mittente
                            if(Condivisi.Instance().getConnessioneAttule()==daElaborare.address)//sono ancora disponibile ad instaurare la connessione
                            {
                                Invia(new Pacchetto("y",""),daElaborare.address);
                                Condivisi.Instance().SettaStatoConnessione(StatoConnessione.Connesso, daElaborare.address);
                            }
                            else
                            {
                                Invia(new Pacchetto("n",""),daElaborare.address);
                                Condivisi.Instance().SettaStatoConnessione(StatoConnessione.DisConnesso);
                            }
                        else        //sono il destinatario e devo solo confermare la connessione
                            Condivisi.Instance().SettaStatoConnessione(StatoConnessione.Connesso, daElaborare.address);
                        break;
                    case "n":
                        Condivisi.Instance().SettaStatoConnessione(StatoConnessione.DisConnesso);
                }
            }
        }
    }

    private void Invia(Pacchetto p,InetAddress address){
        p.address=address;
        Condivisi.Instance().MettiPacchettoOut(p);
    }

    private void MostraMessaggio(String dati) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private Boolean ChiediConferma(InetAddress connessioneAttule, InetAddress address) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
