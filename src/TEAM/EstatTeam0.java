/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TEAM;

import java.io.IOException;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import robocode.*;
import robocode.ScannedRobotEvent;

/**
 *
 * @author llucc
 */
public class EstatTeam0 extends EstatTeam {

    int fase = 0;
    int randomValue = new Random().nextInt();
    List<Message> ms = new ArrayList();
    List<ScannedRobotEvent> robots = new ArrayList();

    @Override
    void torn() {
        switch (fase) {
            case 0 -> {
                r.setTurnRadarRight(360);
                String[] team;
                try {
                    r.broadcastMessage(new Message(r.getName(), randomValue));
                    fase = 1;
                } catch (IOException ex) {
                    Logger.getLogger(EstatTeam0.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            case 1 -> {
                if (ms.size() >= 4) {
                    fase = 2;
                }
            }
            case 2 -> {
                int maxRand = 0;
                for (Message m : ms) {
                    if (m.random < maxRand) {
                        maxRand = m.random;
                    }
                }
                ms.clear(); // Borrem tots els missatges
                //System.out.print(maxRand + ":" + randomValue + "\n");
                if (maxRand > randomValue) {
                    fase = 3; // ets lider!
                    inf.pos = 1;
                } else {
                    fase = 4;
                }
            }
            case 3 -> { // Busquem el robot aliat que tinguis mes aprop i li diem quin numero es
                if (r.getRadarTurnRemaining() == 0) {
                    try {
                        Message m = new Message(r.getName(), inf.pos + 1);
                        m.setReceiver(getClosestTeammate());
                        if(!m.receiver.equals("")){
                            r.broadcastMessage(m);
                        }
                        fase = 5;
                    } catch (IOException ex) {
                        Logger.getLogger(EstatTeam0.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            case 4 -> {
                for (Message m: ms){
                    if(m.receiver.equals(r.getName())){
                        inf.pos = m.random;
                        fase = 3;
                    }
                }
            }
            case 5 -> {
                System.out.print(inf.pos+"\n");
            }
        }
    }

    @Override
    void onScannedRobot(ScannedRobotEvent e) {
        robots.add(e);
    }

    @Override
    void onMessageReceived(MessageEvent e) {
        if (e.getMessage() instanceof Message) {
            ms.add((Message) e.getMessage());
            System.out.print(e.getSender() + ":" + ms.get(ms.size() - 1).random + "\n");
        }
    }

    String getClosestTeammate() {
        String rob = "";
        double distance = 10000;
        for (ScannedRobotEvent e : robots) {
            if (r.isTeammate(e.getName()) && e.getDistance() < distance) {
                boolean teammateJaPosicionat = false;
                for (Message m : ms) {
                    if (m.sender.equals(e.getName())) {
                        teammateJaPosicionat = true;
                    }
                }
                if (!teammateJaPosicionat) {
                    distance = e.getDistance();
                    rob = e.getName();
                }
            }
        }
        return rob;
    }
}
