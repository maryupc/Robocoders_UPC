/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Robocoders;

import robocode.HitRobotEvent;
import robocode.RobotDeathEvent;
import robocode.ScannedRobotEvent;
import robocode.util.Utils;
import static robocode.util.Utils.normalRelativeAngle;
import static robocode.util.Utils.normalRelativeAngleDegrees;

/**
 *Classe per la Fase 2: Robot ja en la cantonada comença a disparar al primer enemic detectat 
 * @author maryx
 */

public class Estat2 extends Estat {
    
    @Override
    void torn() {
        r.setAdjustRadarForGunTurn(true);  // El radar puede girar independientemente del cañón
        r.setAdjustGunForRobotTurn(true);
        r.setTurnRadarRight(360);
        dispara();
    }

    @Override
    void onScannedRobot(ScannedRobotEvent e) {

       if (inf.closestEnemy == null || e.getName().equals(inf.closestEnemy.getName())) {
            inf.closestEnemy = e ;

	}

    }
    
    @Override
    void onHitRobot(HitRobotEvent e) {

    }
    
    /**
     * Funció que serveix per a disparar als enemics, quan es detecta a 1, calcula l'angle.
     * Centra el canyo cap l'enemic i comença a disparar segons la distancia que es trobi.
    */
    private void dispara(){
        if (inf.closestEnemy == null) return;
        double anguloEnemigo = r.getHeading() + inf.closestEnemy.getBearing();
        // Apuntamos con el cañon hacia el enemigo
        double anguloCañon = anguloEnemigo - r.getGunHeading();
        r.setTurnGunRight(normalRelativeAngleDegrees(anguloCañon));
        double max = Math.max(r.getBattleFieldHeight(), r.getBattleFieldWidth());
        
        double anguloRadar = anguloEnemigo - r.getRadarHeading();
        r.setTurnRadarRight(normalRelativeAngleDegrees(anguloRadar)); 

        if (Math.abs(r.getTurnRemaining()) < 10) {
            //Si estamos cerca del enemigo, disparar con maxima potenica, sino disparamos con menor potencia 
            if (inf.closestEnemy.getDistance() < max / 3) {
                r.setFire(3);
            } else {
                r.setFire(1);
            }
        }

    }

     /**
     * Event que si mor l'enemic al que s'acatava, es resteja la info del enemic.
     */
    @Override
    void onRobotDeath(RobotDeathEvent e) {
        if(e.getName().equals(inf.closestEnemy.getName())){
            System.out.print("DEATH"+  "\n");
            inf.closestEnemy = null;
        }
    }
    
    
}
