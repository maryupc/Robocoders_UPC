/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Robocoders;
import robocode.*;
import robocode.util.Utils;
import static robocode.util.Utils.normalRelativeAngle;
import static robocode.util.Utils.normalRelativeAngleDegrees;
/**
 *
 * @author llucc
 */
public class Estat1 extends Estat {
    @Override
     void torn(){
         //Codio sacado de la pagina robocode ---- https://robowiki.net/wiki/GoTo ------
         double a;
         int x = (int) inf.x;
         int y = (int) inf.y;
         r.setTurnRightRadians(Math.tan(
        a = Math.atan2(x -= (int) r.getX(), y -= (int) r.getY()) 
              - r.getHeadingRadians()));
         r.setAhead(Math.hypot(x, y) * Math.cos(a));
         
    }
    

    @Override
    void onScannedRobot(ScannedRobotEvent e) {
        r.setFire(1);
    }
}
