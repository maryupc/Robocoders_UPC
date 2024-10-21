/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Robocoders;

import java.awt.Color;
import java.util.*;
import static java.lang.Math.*;
import robocode.*;

/**
 * Classe per la Fase 0: Escull la cantonada que tingui el enemic mes lluny i la guarda a la informacio del robot
 * @author maryx
 */
public class Estat0 extends Estat {
    /**
     * fase: si es igual a 0, es gira el radar buscant els enemics
        si es igual a 1, s'executa la funcio que busca la millor cantonada
     */
    public int fase = 0;
    /**
     * Llista amb els enemics per saber les seves posicions
     */
    public List<ScannedRobotEvent> enemy = new ArrayList<>();
    /**
     * contador de torns per fer el gir del radar molt eficient
     * (si el radar i la torreta giren junts fan una volta sencera cada 5 ticks, sino, la fan cada 8)
     */
    public int ticks = 0;

    @Override
    void torn() {
        r.setBodyColor(Color.pink);
        r.setGunColor(Color.pink);
        r.setRadarColor(Color.pink);
        r.setScanColor(Color.pink);
        r.setBulletColor(Color.pink);
        switch (fase) {
            case 0 -> {
                ticks++;
                int graus = 45;
                if(ticks == 5){
                    graus = 15;
                }
                r.setTurnGunRight(20);
                r.setTurnRadarRight(graus);
                if (ticks==6){
                    fase = 1;
                }
            }
            case 1 -> {
                bestCorner();
            }
        }
    }

    /**
     * Quan s'escaneja un robot, ens guardem el event a la llista
     * @param e afegit a enemy(list) 
     */
    @Override
    public void onScannedRobot(ScannedRobotEvent e) {
        enemy.add(e);
    }

    /**
     * Definim una array amb les 4 cantonades i iterem la llista d'enemics buscant per cada cantonada a quina distancia esta el enemic que te mes proper
     * (per cada enemic iterem per cada cantonada).
     * Aleshores mirem quina es la cantonada amb la distancia a un enemic mes gran,  i guardem les coordenades d'aquesta a la informacio x i y del estat (inf)
     */
    public void bestCorner() {
        double[][] corners = {
            {18, 18},
            {r.getBattleFieldWidth() - 18, 18},
            {18, r.getBattleFieldHeight() - 18},
            {r.getBattleFieldWidth() - 18, r.getBattleFieldHeight() - 18}
        };
        double maxDistance = 200000;
        double[] cornerMin = {maxDistance, maxDistance, maxDistance, maxDistance};
        int cornerIndex = -1;

        for (int j = 0; j < enemy.size(); j++) {
            double eX = r.getX() + (sin(r.getHeadingRadians() + enemy.get(j).getBearingRadians()) * enemy.get(j).getDistance());
            double eY = r.getY() + (cos(r.getHeadingRadians() + enemy.get(j).getBearingRadians()) * enemy.get(j).getDistance());

            for (int i = 0; i < corners.length; i++) {
                double cornerX = corners[i][0];
                double cornerY = corners[i][1];
                double distance = sqrt(pow(eX - cornerX, 2) + Math.pow(eY - cornerY, 2));

                if (distance < cornerMin[i]) {
                    cornerMin[i] = distance;
                    //cornerIndex = i;
                }
                //System.out.print(cornerNames[i] + ":" + distance + "\n");
            }
        }

        maxDistance = -1;
        for (int i = 0; i < cornerMin.length; i++) {
            if (cornerMin[i] > maxDistance) {
                maxDistance = cornerMin[i];
                cornerIndex = i;
            }
        }
        inf.x = corners[cornerIndex][0];
        inf.y = corners[cornerIndex][1];
        String[] cornerNames = {"Bottom-left", "Bottom-right", "Top-left", "Top-right"};
        System.out.print(cornerNames[cornerIndex] + "+" + inf.x + "\n");
    }

    @Override
    void onHitRobot(HitRobotEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    void onRobotDeath(RobotDeathEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
