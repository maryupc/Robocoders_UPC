/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Robocoders;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import robocode.*;

/**
 * Classe principal del Robot timidin, aqui s'agafen tots els events i es gestionen, i hi ha el bucle principal del robot
 * @author maryx
 */
public class Robocoders extends AdvancedRobot {

    private Estat estat;
    RobotInfo info = new RobotInfo();

    @Override
    public void run() {
        while (true) {
            setEstat();
            estat.torn();
            execute();
        }
    }

    @Override
    public void onScannedRobot(ScannedRobotEvent event) {
        estat.onScannedRobot(event);
    }
    
    @Override
    public void onHitRobot(HitRobotEvent event){
        estat.onHitRobot(event);
    }
    
    @Override
    public void onRobotDeath(RobotDeathEvent event){
        estat.onRobotDeath(event);
    
    }
    
/**
 * Funcio utilitzada per canviar els estats quan s'acaba cada fase
 * -> Fase 0 quan no hi ha cap estat inicialitzat
 * -> Fase 1 quan l'estat0 ja ha calculat la cantonada ideal
 * -> Fase 2 quan l'estat1 ha acabat
 */
    public void setEstat() {
        if (estat == null) {
            estat = new Estat0();
            estat.onCreate(this, info);
        }
        if (estat.inf.x != -1) {
            estat = new Estat1();
            estat.onCreate(this, info);
        }
        
        if(estat.inf.terminado == true){
            estat = new Estat2();
            estat.onCreate(this, info);
        }
    }

}
