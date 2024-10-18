/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TEAM;

import java.io.IOException;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import java.util.logging.Level;
import java.util.logging.Logger;
import robocode.MessageEvent;
import robocode.RobotDeathEvent;
import robocode.ScannedRobotEvent;

/**
 *
 * @author llucc
 */
public class EstatLider extends EstatTeam {

    int goingTo = -1;
    double width;
    double height;
    double[][] corners;
    boolean sendWhoFollow = true;
    String currEne = "";

    @Override
    void torn() {
        gestioMorts();
        sendCoords();
        chooseCorner();
        goTo(corners[goingTo][0], corners[goingTo][1]);
    }

    @Override
    void onScannedRobot(ScannedRobotEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    void onMessageReceived(MessageEvent e) {
        if (e.getMessage() instanceof Message) {
            Message m = (Message) e.getMessage();
            if (m.type.equals("AskFollow") && m.receiver.equals(inf.sendCoords)) {
                inf.sendCoords = m.sender;
                sendWhoFollow = true;
            }
        }
    }

    @Override
    void onRobotDeath(RobotDeathEvent e) {
        if(e.getName().equals(currEne)){
            //getClosestEnemy();
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

    void chooseCorner() {
        if (goingTo == -1) {
            width = r.getBattleFieldWidth() / 10;
            height = r.getBattleFieldHeight() / 10;
            double[][] c = {
                {width, height},
                {width, r.getBattleFieldHeight() - height},
                {r.getBattleFieldWidth() - width, r.getBattleFieldHeight() - height},
                {r.getBattleFieldWidth() - width, height}
            };
            corners = c;
            double minDistance = 10000;
            int cornerIndex = -1;
            for (int i = 0; i < corners.length; i++) {
                double cornerX = corners[i][0];
                double cornerY = corners[i][1];
                double distance = sqrt(pow(r.getX() - cornerX, 2) + Math.pow(r.getY() - cornerY, 2));

                if (distance < minDistance) {
                    minDistance = distance;
                    cornerIndex = i;
                }
            }
            goingTo = cornerIndex;
        } else {
            double x = corners[goingTo][0];
            double y = corners[goingTo][1];
            if (x == r.getX() && y == r.getY()) {
                goingTo = (goingTo + 1) % 4;
            }
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
    }
}
