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
public class RoboCodersTeam extends TeamRobot {

    private EstatTeam estat;
    private TeamInfo info = new TeamInfo();

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
    public void onMessageReceived(MessageEvent e) {
        estat.onMessageReceived(e);
    }

    public void setEstat() {
        if (estat == null) {
            estat = new EstatTeam0();
            estat.onCreate(this, info);
        } else if (estat.inf.estat0Acabat) {
            if (estat.inf.pos == 1) {
                estat = new EstatLider();
                ///PARA DIFERENCIAR LIDER DE LOS DEMAS MAS FACILMENTE
                setBodyColor(Color.RED);
                setGunColor(Color.BLACK);
                setRadarColor(Color.RED);
            } else {
                estat = new EstatBot();
                ///PARA DIFERENCIAR LIDER DE LOS DEMAS MAS FACILMENTE
                setBodyColor(Color.BLUE);
                setGunColor(Color.WHITE);
                setRadarColor(Color.BLUE);
            }
            estat.onCreate(this, info);
            estat.inf.estat0Acabat = false;
        }
    }

    @Override
    public void onPaint(Graphics2D g) {
        estat.onPaint(g);
    }

    @Override
    public void onRobotDeath(RobotDeathEvent event) {
        estat.onRobotDeath(event);
    }
}
