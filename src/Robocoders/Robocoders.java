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
    @Override
    public void run(){
        while(true){
            this.setAhead(100);
            this.setTurnRight(90);
            execute();
        }
    }
    @Override
    public void onScannedRobot(ScannedRobotEvent event){
        fire(Rules.MIN_BULLET_POWER); //En eventos no hay que poner decisions, solo tomar nota y siempre hacerlo en el run
        try {
            this.broadcastMessage("VISTO!" + event.getName());
        } catch (IOException ex) {
            Logger.getLogger(Robocoders.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
