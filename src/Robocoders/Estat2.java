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
    @Override
    void torn() {
      
            r.turnGunRight(10);
        
      /*  switch (fase) {
            case 0 -> {
                r.setTurnGunRight(20);
                r.setTurnRadarRight(20);
                if(inf.closestEnemy != null) fase = 1;

            }
            case 1 -> {
                r.setTurnRight(inf.closestEnemy.getBearing());
                double gunTurnAmt = normalRelativeAngle(inf.closestEnemy.getBearing() + (r.getHeading() - r.getRadarHeading()));
                r.setTurnGunRight(gunTurnAmt);
                 if (Math.abs(r.getTurnRemaining()) < 10) {
                    if(inf.closestEnemy.getDistance() > 200 || r.getEnergy() < 15){
                    r.setFire(1);
                    }else {
                        r.setFire(3);
                    }
                }
            }
        }*/
        
    }

    @Override
    void onScannedRobot(ScannedRobotEvent e) {
        		// Calculate exact location of the robot
		double absoluteBearing = r.getHeading() + e.getBearing();
		double bearingFromGun = normalRelativeAngleDegrees(absoluteBearing - r.getGunHeading());

		// If it's close enough, fire!
		//if (Math.abs(bearingFromGun) <= 3) {
			r.turnGunRight(bearingFromGun);
			// We check gun heat here, because calling fire()
			// uses a turn, which could cause us to lose track
			// of the other robot.
			if (r.getGunHeat() == 0) {
				r.setFire(Math.min(3 - Math.abs(bearingFromGun), r.getEnergy() - .1));
			}
                        r.setTurnRadarRight(r.getHeading() - r.getRadarHeading() + e.getBearing());

		//} // otherwise just set the gun to turn.
		// Note:  This will have no effect until we call scan()
		//else {
		//	r.turnGunRight(bearingFromGun);
		//}
		// Generates another scan event if we see a robot.
		// We only need to call this if the gun (and therefore radar)
		// are not turning.  Otherwise, scan is called automatically.
		if (bearingFromGun == 0) {
			r.scan();
		}

    }
    

    @Override
    void onHitRobot(HitRobotEvent e) {

    }
    
    private void disparo(){
        if (inf.closestEnemy == null) return;
        //
        //gunTurnAmt = (r.getHeading() - r.getRadarHeading() + e.getBearing());

        //Ejemplar de codigo sacado de la funcion de smartFire de robot Corners

       // r.setTurnRadarRight(r.getHeading() - r.getRadarHeading() + inf.closestEnemy.getBearing());
        

    }
    
    
}
