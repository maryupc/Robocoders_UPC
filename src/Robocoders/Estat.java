/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Robocoders;
import robocode.*;
/**
 * Classe base dels estats
 * @author maryx
 */
public abstract class Estat {
    /**
     * @param r -> Referencia del robot
     */
    Robocoders r;
    /**
     * @param inf -> Info del Robot
     */
    RobotInfo inf;
    abstract void torn();
    abstract void onScannedRobot(ScannedRobotEvent e);
    abstract void onHitRobot(HitRobotEvent e);
    abstract void onRobotDeath(RobotDeathEvent e);
    /**
     * Just despres d'inicialitzar una classe estat, s'ha de cridar el onCreate per tenir 
     * la referencia del robot i la informacio que volem passar de estat a estat.
     * @param robo
     * @param info 
     */
    void onCreate(Robocoders robo, RobotInfo info){
        r = robo;
        inf = info;
    }
}
