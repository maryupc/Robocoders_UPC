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
    private double a;
    private ScannedRobotEvent closestEnemy;

    @Override
    void torn() {
        //r.setAdjustGunForRobotTurn(true);
        //r.setAdjustRadarForGunTurn(true);
        //r.setAdjustRadarForRobotTurn(true);
        
        goTo(inf.x, inf.y);
        girarRadarITorre();
        if (hihaRobot) {
            esquiva();
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
        if(e.getDistance() < closestEnemy.getDistance() && e.getBearing() < 45 && e.getBearing() > -45){
            closestEnemy = e;
        }
    }

    private void goTo(double x, double y) {
        //Codio sacado de la pagina robocode ---- https://robowiki.net/wiki/GoTo ------
        //r.setAdjustRadarFor
        r.setTurnRightRadians(Math.tan(
                a = Math.atan2(x -= r.getX(), y -= r.getY())
                - r.getHeadingRadians()));
        //r.setTurnRadarRightRadians(Math.tan(a));
        r.setAhead(Math.hypot(x, y) * Math.cos(a));
        if (Math.cos(a) > 0) direccio = 0;
    }

    private void esquiva() {
        ScannedRobotEvent e = closestEnemy;
        double eX = r.getX() + (sin(r.getHeadingRadians() + e.getBearingRadians()) * e.getDistance());
        double eY = r.getY() + (cos(r.getHeadingRadians() + e.getBearingRadians()) * e.getDistance());
        
        if (e.getBearing()<0){
            r.setTurnRight(10);
        }
        else{
            r.setTurnRight(-10);
        }
    }

    private void girarRadarITorre() {
        r.setTurnGunRightRadians(Utils.normalRelativeAngle(r.getHeadingRadians()+direccio - r.getGunHeadingRadians()));
        double angleRadar = Utils.normalRelativeAngle(r.getHeadingRadians()+direccio - r.getRadarHeadingRadians());
        r.setTurnRadarRightRadians(angleRadar);
        if (angleRadar == 0){
            r.setTurnRadarRight(15);
        }
    }
}
