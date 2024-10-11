/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Robocoders;
import robocode.*;
import static robocode.util.Utils.normalRelativeAngleDegrees;
/**
 *
 * @author llucc
 */
public class Estat1 extends Estat {
    @Override
     void torn(){
         Double angle = Math.atan2(inf.y, inf.x);
         int anglegrados = (int)Math.toDegrees(angle);
         System.out.print(anglegrados);
        r.setTurnRight(normalRelativeAngleDegrees(anglegrados - r.getHeading()));
        
    }
    

    @Override
    void onScannedRobot(ScannedRobotEvent e) {
        r.setFire(1);
    }
}
