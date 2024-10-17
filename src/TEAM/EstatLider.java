/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TEAM;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import robocode.MessageEvent;
import robocode.ScannedRobotEvent;

/**
 *
 * @author llucc
 */
public class EstatLider extends EstatTeam{

    @Override
    void torn() {
        sendCoords();
    }

    
    
    @Override
    void onScannedRobot(ScannedRobotEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    void onMessageReceived(MessageEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    void sendCoords(){
        Message m = new Message(r.getName(), "GoTo");
        m.setCoords(r.getX(), r.getY());
        try {
            r.sendMessage(inf.sendCoords, m);
        } catch (IOException ex) {
            Logger.getLogger(EstatBot.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
