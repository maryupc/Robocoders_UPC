/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TEAM;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import robocode.MessageEvent;
import robocode.RobotDeathEvent;
import robocode.ScannedRobotEvent;

/**
 *
 * @author llucc
 */
public class EstatBot extends EstatTeam {

    boolean askWhoFollow = false;
    boolean sendWhoFollow = false;
    
    @Override
    void torn() {
        r.setMaxVelocity(6);
        gestioMorts();
        if (inf.pos != 5) {
            sendCoords();
        }
        //System.out.println(goX + ":" + goY);
        if (goX != -1) {
            // Anar a la ultima posicio coneguda del robot al que seguim
            if (distance(r.getX(), goX, r.getY(), goY) > 50) {
                goTo(goX, goY);
            } else {
                r.stop();
            }
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
                        goX = m.x;
                        goY = m.y;
                    }
                }
                case "ShootTo" -> {
                    eneX = m.x;
                    eneY = m.y;
                }
                case "AskFollow" -> {
                    if (m.receiver.equals(inf.sendCoords)) {
                        inf.sendCoords = m.sender;
                        sendWhoFollow = true;
                        System.out.println(inf.pos + "->Follow:" + inf.follow + "->send:" + inf.sendCoords);
                    }
                }
                case "SendFollow" -> {
                    if (m.receiver.equals(r.getName())) {
                        inf.follow = m.sender;
                        inf.pos = m.num;
                        sendWhoFollow = true;
                        System.out.println(inf.pos + "->Follow:" + inf.follow + "->send:" + inf.sendCoords);
                    }
                }
            }
        }
    }

    @Override
    void onRobotDeath(RobotDeathEvent e) {
        if (e.getName().equals(inf.follow)) {
            inf.pos--;
            if (inf.pos == 1) {
                sendWhoFollow = true;
                inf.estat0Acabat = true;
            } else {
                askWhoFollow = true;

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

    void gestioMorts() {
        if (sendWhoFollow) {
            Message m = new Message(r.getName(), "SendFollow");
            m.setReceiver(inf.sendCoords);
            m.setInt(inf.pos + 1);
            sendWhoFollow = false;
            System.out.println("SendFollow:" + r.getName() + "->" + inf.sendCoords);
            try {
                r.broadcastMessage(m);
            } catch (IOException ex) {
                Logger.getLogger(EstatBot.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (askWhoFollow) {
            Message m = new Message(r.getName(), "AskFollow");
            m.setReceiver(inf.follow);
            askWhoFollow = false;
            System.out.println("askFollow:" + r.getName() + "->" + inf.follow);
            try {
                r.broadcastMessage(m);
            } catch (IOException ex) {
                Logger.getLogger(EstatBot.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
