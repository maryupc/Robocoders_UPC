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
    List<ScannedRobotEvent> robs = new ArrayList();
    String currEne = "";
    int tornsContador = 0;
    Line2D lineDraw;
    boolean esquivant = false;
    boolean girant = false;

    @Override
    void torn() {
        r.setTurnRadarRight(360);
        r.setTurnGunRight(360);
        gestioMorts();
        chooseCorner();
        sendCoords();
        //  esquiva();
        //girarRadar();
        goTo(corners[goingTo][0], corners[goingTo][1]);
        tornsContador++;
    }

    @Override
    void onScannedRobot(ScannedRobotEvent e) {
        robs.add(e);
        if (!r.isTeammate(e.getName())) {
            updateEnemy(e);
        }
        /*if((inf.closestEnemy==null || (e.getDistance() < inf.closestEnemy.getDistance() )) && !r.isTeammate(e.getName())){        
            inf.closestEnemy = e;  
            //r.setAdjustRadarForRobotTurn(true);
            inf.encontrado = true;
        }*/
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
            if ((x == r.getX() && y == r.getY()) || tornsContador == 150) {
                tornsContador = 0;
                goingTo = (goingTo + 1) % 4;
                girant = true;
                r.setAhead(0);
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

        if (r.getDistanceRemaining() == 0) {
            double targetAngle = Utils.normalRelativeAngle(Math.atan2(x - r.getX(), y - r.getY()) - r.getHeadingRadians());
            r.setTurnRightRadians(targetAngle);
        }

        if (esquivant || r.getTurnRemaining() == 0) {
            r.setTurnRightRadians(getActualAngle(x, y));
            r.setAhead(Math.hypot(x - r.getX(), y - r.getY()));
            esquivant = false;
        }
        if (r.getTurnRemaining() > 45) {
            r.setMaxVelocity(6);
        } else {
            r.setMaxVelocity(8);
        }
        /*if (!esquivant) {
            r.setTurnRightRadians(targetAngle);
        }
        if (r.getTurnRemaining() != 0) {
            girant = true;
        }
        if (!girant) {
            esquivant = false;
            
        }*/
 /*       
        //Codigo sacado de la pagina robocode ---- https://robowiki.net/wiki/GoTo ------
        double a;
        x -= r.getX();
	y -= r.getY();
	
	/* Calculate the angle to the target position /
	double angleToTarget = Math.atan2(x, y);
	
	/* Calculate the turn required get there /
	double targetAngle = Utils.normalRelativeAngle(angleToTarget - r.getHeadingRadians());
	
	        
        r.turnRightRadians(targetAngle);
        r.setAhead(Math.hypot(x, y));*/
    }

    double getActualAngle(double x, double y) {
        double targetAngle = Utils.normalRelativeAngle(Math.atan2(x - r.getX(), y - r.getY()) - r.getHeadingRadians());
        MapRobot rob = isEnemyOnPath(x, y);
        if (!rob.name.equals("NoXoquemTranqui")) {
            esquivant = true;
            double angle = 0.1;
            System.out.println(rob.distance+":" + angle);
            if (Math.abs(rob.bearing) - r.getHeading() >= 0) {
                return -angle;
            } else {
                return angle;
            }
        }

        /*    if(inf.encontrado == true){
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
        } */
        return targetAngle;
    }

    public MapRobot isEnemyOnPath(double x, double y) {
        // Funcio parcialment treta de chatGPT
        // Define the line representing the robot's trajectory
        Line2D trajectoryLine = new Line2D.Double(r.getX(), r.getY(), x, y);
        lineDraw = trajectoryLine;
        // Iterate through the list of detected robots
        for (MapRobot e : inf.enemies) {
            double left = e.x - 31;
            double top = e.y - 31;
            Rectangle2D eneDraw = new Rectangle2D.Double(left, top, 62, 62);
            // Check if the trajectory line intersects with the enemy's bounding box
            if (trajectoryLine.intersects(eneDraw)) {
                // Enemy is on the path
                //inf.closestEnemy = enemy;
                return e;
            }
        }
        // No enemies on the path
        return new MapRobot("NoXoquemTranqui");
    }
    /*void girarRadar() {
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
    }*/
}
