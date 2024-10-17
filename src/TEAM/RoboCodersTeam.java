/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TEAM;
import java.awt.Color;
import java.awt.Graphics2D;
import robocode.*;
/**
 *
 * @author llucc
 */
public class RoboCodersTeam extends TeamRobot{
    private EstatTeam estat;
    private TeamInfo info = new TeamInfo();
    
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
    
    @Override
    public void onMessageReceived(MessageEvent e){
        estat.onMessageReceived(e);
    }
    
    public void setEstat() {
        if (estat == null) {
            estat = new EstatTeam0();
            estat.onCreate(this, info);
        }
    }
    
    @Override
    public void onPaint(Graphics2D g) {
        // Check if this robot is the leader
        if (estat.inf.pos == 1) {
            // Set the color for the circle
            g.setColor(Color.YELLOW);
            
            // Get the robot's position
            double x = getX();
            double y = getY();
            
            // Draw the circle around the robot
            double radius = 50; // Set the radius of the circle (adjust as needed)
            g.drawOval((int)(x - radius / 2), (int)(y - radius / 2), (int)radius, (int)radius);
        }
    }
}
