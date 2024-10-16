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
public abstract class EstatTeam {
    RoboCodersTeam r;
    TeamInfo inf;
    abstract void torn();
    abstract void onScannedRobot(ScannedRobotEvent e);
    abstract void onMessageReceived(MessageEvent e);
    void onCreate(RoboCodersTeam robo, TeamInfo info){
        r = robo;
        inf = info;
    }
    
}
