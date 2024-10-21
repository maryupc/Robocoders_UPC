/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Robocoders;

import static java.lang.Math.*;
import robocode.*;
import robocode.util.Utils;
import static robocode.util.Utils.normalRelativeAngleDegrees;

/**
 * Classe per la Fase 1: Robot es dirigeix a la cantonada escullida en fase anterior, mentres esquiva als robots que es troben al seu camí. 
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
            inf.terminado = true;
            inf.closestEnemy = null;
        }
           
    }
    
     /**
     * Quan s'escaneja un robot, i la distancia es menor a la que teniem abans 
     * @param e guardar com nou closestEnemy  
     */

    @Override
    void onScannedRobot(ScannedRobotEvent e) {
        if(inf.closestEnemy==null || (e.getDistance() < inf.closestEnemy.getDistance() )){        
            inf.closestEnemy = e;  
            r.setAdjustRadarForRobotTurn(true);
            inf.encontrado = true;
        }
        
    }
    
    /**
     * Funció que calcula l'angle al que s'hauria de orientar el robot i segons l'angle i la direcció va cap envadant o darrera
     */
    public void goTo(double x, double y) {
        //Codigo sacado de la pagina robocode ---- https://robowiki.net/wiki/GoTo ------

        r.setTurnRightRadians(Math.tan(
                a = Math.atan2(x -= r.getX(), y -= r.getY())
                - r.getHeadingRadians()));
        
        r.setAhead(Math.hypot(x, y) * Math.cos(a));
        if (Math.cos(a) > 0) direccio = 0;
    }
    
    /**
     * Funció que si s'ha trobat un enemic en el camí, esquiva al enemic per tal de poder arribar al destí 
     */ 

    public void esquiva() {
        if(inf.encontrado == true){
            System.out.print("ENCONTRE UN ROBOT EN MI CAMINO"+  "\n");
            if (inf.closestEnemy.getDistance() < 200)
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

     /**
     * Funció que s'encarrega de primer girar radar i canyo segons la posició del robot i seguidament anar girant el radar per anar escanejant 
     * robots que estiguin en el camí
     */ 
    private void girarRadarITorre() {
        
        r.setTurnGunRightRadians(Utils.normalRelativeAngle(r.getHeadingRadians()+direccio - r.getGunHeadingRadians()));
        double angleRadar = Utils.normalRelativeAngle(r.getHeadingRadians() + direccio - r.getRadarHeadingRadians());
        r.setTurnRadarRightRadians(angleRadar);
        if (Math.abs(angleRadar) <= 0.01){
           if(girRadar){
                r.setTurnRadarRightRadians(10);
                girRadar = false;
            }else{
                girRadar = true;
                r.setTurnRadarLeftRadians(10);
            } 
            
        }
    }

    @Override
    void onHitRobot(HitRobotEvent e) {        
        r.setBack(40);
        r.turnRight(90);
        double anguloEnemigo = r.getHeading() + inf.closestEnemy.getBearing();
        // Apuntamos con el cañon hacia el enemigo
        double anguloCañon = anguloEnemigo - r.getGunHeading();
        r.setTurnGunRight(normalRelativeAngleDegrees(anguloCañon));
        double anguloRadar = anguloEnemigo - r.getRadarHeading();
        r.setTurnRadarRight(normalRelativeAngleDegrees(anguloRadar)); 
      //  r.setTurnGunRight(bearingFromGun);
        r.setFire(3);
    }

    @Override
    void onRobotDeath(RobotDeathEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    

}
