/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;

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
public class ThreadServer extends Thread{
    
    static DatagramSocket server;
    InetAddress address;
    public ThreadServer() throws SocketException, IOException {
        server = new DatagramSocket(12345);
    }
    
    @Override
    public void run(){
        while(true)
        {
            try {
                Pacchetto p=Ricevi();
                p.address=address;
                Condivisi.Instance().MettiPacchettoIn(p);
                System.out.println(p.ToCsv());
            } catch (IOException ex) {
                Logger.getLogger(ThreadServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public Pacchetto Ricevi() throws IOException
    {
        byte[] buf= new byte[1500];
        DatagramPacket p= new DatagramPacket(buf,buf.length);
        server.receive(p);
        address=p.getAddress();
        return new Pacchetto(new String(p.getData(),0,p.getLength()));
    }
}
