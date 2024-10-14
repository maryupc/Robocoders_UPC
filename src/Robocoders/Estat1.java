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
    
    private double posini = 0;

    @Override
    void torn() {

        //r.setAdjustGunForRobotTurn(true);
        //r.setAdjustRadarForGunTurn(true);
        //r.setAdjustRadarForRobotTurn(true);
        goTo(inf.x, inf.y);
        girarRadarITorre();
       if(inf.encontrado == true){
            System.out.print("ENCONTRE UN ROBOT EN MI CAMINO"+  "\n");
            if (inf.closestEnemy.getDistance() < 150)
            {
                
                r.setBack(55);
                r.setStop();
                if (inf.closestEnemy.getBearing() > 0) {
                    r.setTurnLeft(Utils.normalRelativeAngleDegrees(90 + (r.getGunHeading()- r.getRadarHeading())));  // Si el enemigo está a la derecha, gira a la derecha
                } else {
                    r.setTurnRight(Utils.normalRelativeAngleDegrees(90 + (r.getGunHeading()- r.getRadarHeading()))); 
                }
                r.setAhead(45);
            }
        } 
            inf.encontrado = false;
            if(inf.encontrado == false){
                System.out.print("NO ENCONTRE UN ROBOT EN MI CAMINO"+  "\n");
            }
           
    }

    @Override
    void onScannedRobot(ScannedRobotEvent e) {
        // calcular posEnemic
        
        if(inf.closestEnemy==null || (e.getDistance() < inf.closestEnemy.getDistance() || e.getName().equals(inf.closestEnemy.getName()) )){        
            
            inf.closestEnemy = e;  
            inf.encontrado = true;
            
            //esquiva();
        }
        
        /*if(e.getDistance() < closestEnemy.getDistance() || e.getBearing() < 45 && e.getBearing() > -45){
                closestEnemy = e;
            }*/

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
        
        ScannedRobotEvent e = inf.closestEnemy;
        
        r.stop();
        
        r.setFire(1);
        //r.setBack(e.getDistance());
        r.setTurnRight(Math.abs(90));
        
        //r.setResume();
        
        
       /* double eX = r.getX() + (sin(r.getHeadingRadians() + e.getBearingRadians()) * e.getDistance());
        double eY = r.getY() + (cos(r.getHeadingRadians() + e.getBearingRadians()) * e.getDistance());
        

        }*/
    }

    private void girarRadarITorre() {
        
        r.setTurnGunRightRadians(Utils.normalRelativeAngle(r.getHeadingRadians()+direccio - r.getGunHeadingRadians()));
        double angleRadar = Utils.normalRelativeAngle(r.getHeadingRadians()+direccio - r.getRadarHeadingRadians());
        r.setTurnRadarRightRadians(angleRadar);
        if (angleRadar == 0){
            r.setTurnRadarRight(10);
        }else r.setTurnRadarRight(-10);
        //hihaRobot = true;
    }
}
