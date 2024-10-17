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
public class EstatBot extends EstatTeam {

    @Override
    void torn() {
        if (inf.pos != 5) {
            sendCoords();
        }
        System.out.println(goX+":"+goY);
        if (goX != -1) {
            // Anar a la ultima posicio coneguda del robot al que seguim
            goTo(goX, goY);
        }

        if (eneX != -1) {
            // Fer preparacions per disparar i disparar a la posicio de l'enemic
        }
    }

    @Override
    void onScannedRobot(ScannedRobotEvent e) {
        // Es necessita per esquivar?
    }

    @Override
    void onMessageReceived(MessageEvent e) {
        if (e.getMessage() instanceof Message) {
            Message m = (Message) e.getMessage();
            //System.out.println(m.sender + ":" + m.type);
            
            switch (m.type) {
                case "GoTo" -> {
                    if (m.sender.equals(inf.follow)) {
                        System.out.println("a");
                        goX = m.x;
                        goY = m.y;
                    }
                }
                case "ShootTo" -> {
                    eneX = m.x;
                    eneY = m.y;
                }
            }
        }
    }

    void sendCoords() {
        Message m = new Message(r.getName(), "GoTo");
        m.setCoords(r.getX(), r.getY());
        try {
            r.sendMessage(inf.sendCoords, m);
        } catch (IOException ex) {
            Logger.getLogger(EstatBot.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
