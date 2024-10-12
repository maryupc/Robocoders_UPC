/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Robocoders;

import static java.lang.Math.*;
import robocode.*;
import robocode.util.Utils;

/**
 *
 * @author llucc
 */
public class Estat1 extends Estat {

    private boolean hihaRobot = false;
    private double direccio = Math.PI;
    private double posEnemicX;
    private double posEnemicY;
    private double distEnemic;

    @Override
    void torn() {
        r.setAdjustGunForRobotTurn(true);
        r.setAdjustRadarForGunTurn(true);
        r.setAdjustRadarForRobotTurn(true);
        
        goTo(inf.x, inf.y);
        girarRadarITorre();
        if (hihaRobot) {
            esquiva(posEnemicX, posEnemicY, distEnemic);
            hihaRobot = false;
        }

        //Codio sacado de la pagina robocode ---- https://robowiki.net/wiki/GoTo ------
        /*double a;
         int x = (int) inf.x;
         int y = (int) inf.y;
         r.setTurnRightRadians(Math.tan(
        a = Math.atan2(x -= (int) r.getX(), y -= (int) r.getY()) 
              - r.getHeadingRadians()));
         r.setAhead(Math.hypot(x, y) * Math.cos(a));*/
    }

    @Override
    void onScannedRobot(ScannedRobotEvent e) {
        hihaRobot = true;
        // calcular posEnemic
        posEnemicX = r.getX() + (sin(r.getHeadingRadians() + e.getBearingRadians()) * e.getDistance());
        posEnemicY = r.getY() + (cos(r.getHeadingRadians() + e.getBearingRadians()) * e.getDistance());
        distEnemic = e.getDistance();
    }

    private void goTo(double x, double y) {
        //Codio sacado de la pagina robocode ---- https://robowiki.net/wiki/GoTo ------
        //r.setAdjustRadarFor
        double a;
        r.setTurnRightRadians(Math.tan(
                a = Math.atan2(x -= r.getX(), y -= r.getY())
                - r.getHeadingRadians()));
        //r.setTurnRadarRightRadians(Math.tan(a));
        r.setAhead(Math.hypot(x, y) * Math.cos(a));
        if (Math.cos(a) > 0) direccio = 0;
    }

    private void esquiva(double x, double y, double dist) {
        
    }

    private void girarRadarITorre() {
        r.setTurnGunRightRadians(Utils.normalRelativeAngle(r.getHeadingRadians()+direccio - r.getGunHeadingRadians()));
        r.setTurnRadarRightRadians(Utils.normalRelativeAngle(r.getHeadingRadians()+direccio - r.getRadarHeadingRadians()));
    
    }
}
