/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 *
 * @author user
 */
public class Condivisi {
    Queue<Pacchetto> in= new ArrayDeque();
    Queue<Pacchetto> out= new ArrayDeque();
    public boolean CiSonoPacchettiDaElaborare(){
        return true;
    }
    public boolean CiSonoPacchettiDaInviare(){
        return true;
    }
    public void MettiPacchettoOut(Pacchetto p){
        
    }
    public Pacchetto PrendiPacchettoOut(){
        return new Pacchetto("","");
    }
    public void MettiPacchettoIn(Pacchetto p){
        
    }
    public Pacchetto PrendiPacchettoIn(){
        return new Pacchetto("","");
    }
}
