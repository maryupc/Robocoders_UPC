/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TEAM;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import robocode.*;

/**
 *
 * @author llucc
 */
public abstract class EstatTeam {
    RoboCodersTeam r;
    TeamInfo inf;
    double eneX = -1;
    double eneY = -1;
    double goX = -1;
    double goY = -1;
    abstract void torn();
    abstract void onScannedRobot(ScannedRobotEvent e);
    abstract void onMessageReceived(MessageEvent e);
    abstract void onRobotDeath(RobotDeathEvent event);
    void onCreate(RoboCodersTeam robo, TeamInfo info){
        r = robo;
        inf = info;
    }
    void goTo(double x, double y) {
        //Codigo sacado de la pagina robocode ---- https://robowiki.net/wiki/GoTo ------
        double a;
        r.setTurnRightRadians(Math.tan(
                a = Math.atan2(x -= r.getX(), y -= r.getY())
                - r.getHeadingRadians()));
        
        r.setAhead(Math.hypot(x, y) * Math.cos(a));
        //if (Math.cos(a) > 0) direccio = 0;
    }
    double distance (double x1, double y1, double x2, double y2){
        return sqrt(pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }
}
