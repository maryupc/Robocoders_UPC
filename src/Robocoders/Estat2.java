/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Robocoders;

import robocode.HitRobotEvent;
import robocode.ScannedRobotEvent;
import robocode.util.Utils;
import static robocode.util.Utils.normalRelativeAngle;
import static robocode.util.Utils.normalRelativeAngleDegrees;

/**
 *
 * @author maryx
 */

public class Estat2 extends Estat {
    
    int fase = 0;
     int gunIncrement = 3;
    @Override
    void torn() {
        r.turnGunRight(10);
        
    }

    @Override
    void onScannedRobot(ScannedRobotEvent e) {
        double absoluteBearing = r.getHeading() + e.getBearing();
	double bearingFromGun = normalRelativeAngleDegrees(absoluteBearing - r.getGunHeading());
        r.turnGunRight(bearingFromGun);
        r.stop();
        smartFire(e.getDistance());
       // r.scan();
        r.resume();

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
    
    private void disparo(){
        if (inf.closestEnemy == null) return;
        //
        //gunTurnAmt = (r.getHeading() - r.getRadarHeading() + e.getBearing());

        //Ejemplar de codigo sacado de la funcion de smartFire de robot Corners

       // r.setTurnRadarRight(r.getHeading() - r.getRadarHeading() + inf.closestEnemy.getBearing());
        

    }
    
    
}
