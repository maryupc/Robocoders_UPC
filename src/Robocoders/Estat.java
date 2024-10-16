/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Robocoders;
import robocode.*;
/**
 *
 * @author maryx
 */
public abstract class Estat {
    Robocoders r;
    RobotInfo inf;
    abstract void torn();
    abstract void onScannedRobot(ScannedRobotEvent e);
    abstract void onHitRobot(HitRobotEvent e);
    abstract void onRobotDeath(RobotDeathEvent e);
    void onCreate(Robocoders robo, RobotInfo info){
        r = robo;
        inf = info;
    }
}
