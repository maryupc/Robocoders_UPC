/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TEAM;
import robocode.*;
/**
 *
 * @author llucc
 */
public class RoboCodersTeam extends TeamRobot{
    private EstatTeam estat;
    private TeamInfo info;
    
    @Override
    public void run(){
        while(true){
            setEstat();
            estat.torn();
            execute();
        }
    }
    
    @Override
    public void onScannedRobot(ScannedRobotEvent event){
        estat.onScannedRobot(event);
    }
    
    public void setEstat() {
        if (estat == null) {
            estat = new EstatTeam0();
            estat.onCreate(this, info);
        }
    }
}
