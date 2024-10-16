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
 *
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
            double anguloEnemigo = r.getHeading() + e.getBearing();

            // Apuntamos con el cañon hacia el enemigo
           double anguloCañon = anguloEnemigo - r.getGunHeading();
           r.setTurnGunRight(normalRelativeAngleDegrees(anguloCañon));
	}

    }
    
    @Override
    void onHitRobot(HitRobotEvent e) {

    }
    
    public void smartFire(double robotDistance) {
        if (robotDistance > 200 || r.getEnergy() < 15) {
			r.fire(1);
		} else if (robotDistance > 50) {
			r.fire(2);
		} else {
			r.fire(3);
		}
	}
    
    private void dispara(){
        if (inf.closestEnemy == null) return;
        double max = Math.max(r.getBattleFieldHeight(), r.getBattleFieldWidth());

        if (Math.abs(r.getTurnRemaining()) < 10) {
            //Si estamos cerca del enemigo, disparar con maxima potenica, sino disparamos con menor potencia 
            if (inf.closestEnemy.getDistance() < max / 3) {
                r.setFire(3);
            } else {
                r.setFire(1);
            }
        }

    }

    //Si muere el enemigo al que estabamos disparando, reseteamos la info. 
    @Override
    void onRobotDeath(RobotDeathEvent e) {
        if(e.getName().equals(inf.closestEnemy.getName())){
            System.out.print("DEATH"+  "\n");
            inf.closestEnemy = null;
        }
    }
    
    
}
