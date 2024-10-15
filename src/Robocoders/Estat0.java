/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Robocoders;

import java.util.*;
import static java.lang.Math.*;
import robocode.*;

/**
 *
 * @author maryx
 */
public class Estat0 extends Estat {

    private int fase = 0;
    private List<ScannedRobotEvent> enemy = new ArrayList<>();
    private int ticks = 0;

    @Override
    void torn() {
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

    @Override
    void onScannedRobot(ScannedRobotEvent e) {
        enemy.add(e);
    }

    private void bestCorner() {
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
}
