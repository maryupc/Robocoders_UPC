/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Robocoders;

import java.awt.Graphics2D;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import robocode.*;

/**
 *
 * @author maryx
 */

public class Robocoders extends AdvancedRobot {
    private Estat estat;
    RobotInfo info = new RobotInfo();
    
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
    
    public void setEstat(){
        if (estat == null){
            estat = new Estat0();
            estat.onCreate(this, info);
        }
        if (estat.inf.x != -1){
            estat = new Estat1();
            estat.onCreate(this, info);
        }
    }
 
}
