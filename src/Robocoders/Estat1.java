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

    private boolean girRadar = false;
    private double direccio = Math.PI;
    private double a;
    
    @Override
    void torn() {
        goTo(inf.x, inf.y);
        girarRadarITorre();
        esquiva();
        inf.encontrado = false;
        if(inf.encontrado == false){
            System.out.print("NO ENCONTRE UN ROBOT EN MI CAMINO"+  "\n");
        }
        if(r.getX() == inf.x && r.getY() == inf.y){
            System.out.print("LLEGUE AL DESTINO"+  "\n");
           //r.stop();
            inf.terminado = true;
            inf.closestEnemy = null;

        }
           
    }

    @Override
    void onScannedRobot(ScannedRobotEvent e) {
        if(inf.closestEnemy==null || (e.getDistance() < inf.closestEnemy.getDistance() )){        
            inf.closestEnemy = e;  
            r.setAdjustRadarForRobotTurn(true);
            inf.encontrado = true;
        }
        
    }


    private void goTo(double x, double y) {
        //Codio sacado de la pagina robocode ---- https://robowiki.net/wiki/GoTo ------

        r.setTurnRightRadians(Math.tan(
                a = Math.atan2(x -= r.getX(), y -= r.getY())
                - r.getHeadingRadians()));
        
        r.setAhead(Math.hypot(x, y) * Math.cos(a));
        if (Math.cos(a) > 0) direccio = 0;
    }

    private void esquiva() {
        if(inf.encontrado == true){
            System.out.print("ENCONTRE UN ROBOT EN MI CAMINO"+  "\n");
            if (inf.closestEnemy.getDistance() < 150)
            {
                r.back(55);
                if (inf.closestEnemy.getBearing() >= 0) {
                    r.setTurnLeft(Utils.normalRelativeAngleDegrees(90 + (r.getGunHeading() + direccio - r.getRadarHeading())));  // Si el enemigo está a la derecha, gira a la izquierda
                } else {
                    r.setTurnRight(Utils.normalRelativeAngleDegrees(90 + (r.getGunHeading() + direccio - r.getRadarHeading()))); 
                }
               r.ahead(30);
            }
        } 
    }

    private void girarRadarITorre() {
        
        r.setTurnGunRightRadians(Utils.normalRelativeAngle(r.getHeadingRadians()+direccio - r.getGunHeadingRadians()));
        double angleRadar = Utils.normalRelativeAngle(r.getHeadingRadians() + direccio - r.getRadarHeadingRadians());
        r.setTurnRadarRightRadians(angleRadar*2);
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

    @Override
    void onHitRobot(HitRobotEvent e) {        
        r.back(40);
        
        double absoluteBearing = r.getHeading() + e.getBearing();
	double bearingFromGun = Utils.normalRelativeAngleDegrees(absoluteBearing - r.getGunHeading());
        r.setTurnGunRight(bearingFromGun);
        
	r.setFire(3);
    }
    

}
