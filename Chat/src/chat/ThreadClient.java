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
    Condivisi c;
    public ThreadClient(Condivisi c) throws SocketException, IOException {
        client = new DatagramSocket(666);
        this.c=c;
    }
    
    @Override
    public void run(){
        while(true)
        {
            if(c.CiSonoPacchettiDaInviare())
                try {
                    Invia(c.PrendiPacchettoOut());
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
