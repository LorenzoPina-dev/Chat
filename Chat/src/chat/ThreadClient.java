/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;

import static chat.ThreadServer.server;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author user
 */
public class ThreadClient extends Thread{
    
    
    static DatagramSocket client;
    InetAddress address;
    public ThreadClient() throws SocketException, IOException {
        client = new DatagramSocket();
    }
    
    @Override
    public void run(){
        while(true)
        {
            try {
                Pacchetto p=Condivisi.Instance().PrendiPacchettoOut();
                if(p!=null)
                {
                    if(p.scelta.equals("c"))
                        Condivisi.Instance().setHoMandatoRichiestaDiConnessione(true);
                    Invia(p);
                }
            } catch (IOException ex) {
                Logger.getLogger(ThreadClient.class.getName()).log(Level.SEVERE, null, ex);
            }   
        }
    }
    
    public void Invia(Pacchetto m) throws IOException
    {
        byte[] buf= m.ToCsv().getBytes();
        DatagramPacket p= new DatagramPacket(buf,buf.length, m.address, 12345);
        server.send(p);
    }
}
