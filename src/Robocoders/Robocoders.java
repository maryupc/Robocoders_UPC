/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Robocoders;

import java.awt.Graphics2D;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import robocode.AdvancedRobot;
import robocode.MessageEvent;
import robocode.Robot;
import robocode.Rules;
import robocode.ScannedRobotEvent;
import robocode.TeamRobot;

/**
 *
 * @author maryx
 */

public class Robocoders extends TeamRobot {
    private Estat estat;
    RobotInfo info;
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
        estat.onScannedRobot();
    }
    
    public void setEstat(){
        if (estat == null){
            estat = new Estat0();
            estat.onCreate(this, info);
        }
        if (estat.inf.terminado){
            estat = new Estat1();
            info.terminado = false;
            estat.onCreate(this, info);
        }
    }
 
}
