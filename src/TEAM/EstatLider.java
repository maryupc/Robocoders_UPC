/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TEAM;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import static java.lang.Math.cos;
import static java.lang.Math.pow;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import robocode.HitRobotEvent;
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
    String currEne = "";
    int tornsContador = 0;
    Line2D lineDraw;
    boolean esquivant = false;
    boolean girant = false;
    boolean girRadar = false;

    @Override
    void torn() {
        gestioMorts();
        chooseCorner();
        sendCoords();
        //shotToEne();  //si es descomenta aquesta línia els bots disparen a l'enemic, pero no acaba de funcionar el tot bé per aixo esta comentat
        girarRadar();
        goTo(corners[goingTo][0], corners[goingTo][1]);
        //tornsContador++;
    }

    @Override
    void onScannedRobot(ScannedRobotEvent e) {
        if(esquivant) return;
        if(inf.closestEnemy==null || (e.getDistance() < inf.closestEnemy.getDistance() )){        
            inf.closestEnemy = e;  
            esquivant = true;
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
        if (e.getName().equals(currEne)) {
            inf.enemies.remove(currEne);
            // s'ha mort l'enemic al qual estavem disparant, s'ha de triar un altre enemic
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
            double distance1 = distance(r.getX(), r.getY(), x, y);
            if (Math.abs(distance1) <= 0.1) {
                //tornsContador = 0;
                goingTo = (goingTo + 1) % 4;
                girant = true;
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

            g.draw(lineDraw);
            for (MapRobot e : inf.enemies) {
                double left = e.x - 36;
                double top = e.y - 36;
                Rectangle2D eneDraw = new Rectangle2D.Double(left, top, 72, 72);
                // Check if the trajectory line intersects with the enemy's bounding box
                g.draw(eneDraw);
            }
            
        }
    }

    @Override
    void goTo(double x, double y) {
        //Codigo sacado de la pagina robocode ---- https://robowiki.net/wiki/GoTo ------
        double a;
        x -= r.getX();
	y -= r.getY();
	
	/* Calculate the angle to the target position*/
      double angleToTarget = Math.atan2(x, y);

	
	/* Calculate the turn required get there */
	double targetAngle = Utils.normalRelativeAngle(angleToTarget - r.getHeadingRadians());
	if(esquivant){
            if (inf.closestEnemy.getDistance() < 200 && inf.closestEnemy.getDistance() >= 100)
            {
                
                if (inf.closestEnemy.getBearing() >= 0) {
                    r.turnLeft(Utils.normalRelativeAngleDegrees(45 + (r.getGunHeading()  - r.getRadarHeading())));  // Si el enemigo está a la derecha, gira a la izquierda
                } else {
                    r.turnRight(Utils.normalRelativeAngleDegrees(45 + (r.getGunHeading() - r.getRadarHeading()))); 
                }
               r.ahead(30);
            }else if (inf.closestEnemy.getDistance() < 20) {
                r.back(55);
                r.turnRight(Utils.normalRelativeAngleDegrees(90 + (r.getGunHeading() - r.getRadarHeading()))); 
            }
            
            esquivant = false;
        }else{
            if(Math.abs(targetAngle) >= 0.1)
                r.turnRightRadians(targetAngle);
            r.setAhead(Math.hypot(x, y));
        }        

    }

    void girarRadar() {
        double angleRadar = Utils.normalRelativeAngle(r.getHeadingRadians() - r.getRadarHeadingRadians());
        r.setTurnRadarRightRadians(angleRadar);
        if (Math.abs(angleRadar) <= 0.01){
           if(girRadar){
                r.setTurnRadarRight(10);
                girRadar = false;
            }else{
                girRadar = true;
                r.setTurnRadarLeft(10);
            } 
            
        }
    }

    @Override
    void onHitRobot(HitRobotEvent event) {
        r.back(40);
        r.turnRight(90);
    }

    private void shotToEne() {
        if(inf.enemies !=null ){
            
            Message m = new Message(inf.enemies.get(0).name, "ShootTo");
            m.setCoords(inf.enemies.get(0).x, inf.enemies.get(0).y);
            currEne = inf.enemies.get(0).name;
            try {
                r.broadcastMessage(m);

            } catch (IOException ex) {
                Logger.getLogger(EstatBot.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }
}
