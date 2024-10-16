/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TEAM;
import robocode.*;
import robocode.ScannedRobotEvent;

/**
 *
 * @author llucc
 */
public class EstatTeam0 extends EstatTeam{
    @Override
    void torn(){
        r.setAhead(1000);
    }

    @Override
    void onScannedRobot(ScannedRobotEvent e) {
    }
    
    @Override
    void onMessageReceived(MessageEvent e){
        
    }
}
