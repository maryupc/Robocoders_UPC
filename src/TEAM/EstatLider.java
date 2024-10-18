/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TEAM;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.IOException;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import java.util.logging.Level;
import java.util.logging.Logger;
import robocode.MessageEvent;
import robocode.RobotDeathEvent;
import robocode.ScannedRobotEvent;
import robocode.util.Utils;

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
    boolean girRadar = false;
    String currEne = "";

    @Override
    void torn() {
        ///PARA DIFERENCIAR LIDER DE LOS DEMAS MAS FACILMENTE
        r.setBodyColor(Color.RED);
        r.setGunColor(Color.BLACK);
        r.setRadarColor(Color.RED);
        //////////////////////////////////////////////////////7777
        gestioMorts();
        chooseCorner();
        sendCoords();
      //  esquiva();
        girarRadar();
        goTo(corners[goingTo][0], corners[goingTo][1]);
        
    }

    @Override
    void onScannedRobot(ScannedRobotEvent e) {
        if((inf.closestEnemy==null || (e.getDistance() < inf.closestEnemy.getDistance() )) && !r.isTeammate(e.getName())){        
            inf.closestEnemy = e;  
            //r.setAdjustRadarForRobotTurn(true);
            inf.encontrado = true;
        }
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

    @Override
    void onPaint(Graphics2D g) {
        // Check if this robot is the leader
        if (inf.pos == 1) {
            // Set the color for the circle
            g.setColor(Color.YELLOW);

            // Get the robot's position
            double x = r.getX();
            double y = r.getY();

            // Draw the circle around the robot
            double radius = 60; // Set the radius of the circle (adjust as needed)
            g.drawOval((int) (x - radius / 2), (int) (y - radius / 2), (int) radius, (int) radius);
        }
    }

    @Override
    void goTo(double x, double y) {
       //Codigo sacado de la pagina robocode ---- https://robowiki.net/wiki/GoTo ------
        double a;
        x -= r.getX();
	y -= r.getY();
	
	/* Calculate the angle to the target position */
	double angleToTarget = Math.atan2(x, y);
	
	/* Calculate the turn required get there */
	double targetAngle = Utils.normalRelativeAngle(angleToTarget - r.getHeadingRadians());
	
	        
        r.turnRightRadians(targetAngle);
        r.setAhead(Math.hypot(x, y));
    }
    
    void esquiva(){
            if(inf.encontrado == true){
            System.out.print("ENCONTRE UN ROBOT EN MI CAMINO"+  "\n");
            if (inf.closestEnemy.getDistance() < 100)
            {
                r.back(55);
                if (inf.closestEnemy.getBearing() >= 0) {
                    r.setTurnLeft(Utils.normalRelativeAngleDegrees(45 + (r.getGunHeading() - r.getRadarHeading())));  // Si el enemigo está a la derecha, gira a la izquierda
                } else {
                    r.setTurnRight(Utils.normalRelativeAngleDegrees(45 + (r.getGunHeading()  - r.getRadarHeading()))); 
                }
               r.ahead(30);
            }
        } 
    
    }
    
    void girarRadar() {
        double angleRadar = Utils.normalRelativeAngle(r.getHeadingRadians() - r.getRadarHeadingRadians());
        r.setTurnRadarRightRadians(angleRadar);
        if (Math.abs(angleRadar) <= 0.01){
           if(girRadar){
                r.setTurnRadarRightRadians(0);
                girRadar = false;
            }else{
                girRadar = true;
                r.setTurnRadarLeftRadians(10);
            } 
            
        }
    }
}
