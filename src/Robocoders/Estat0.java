/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Robocoders;

import static java.lang.Math.*;
import robocode.*;

/**
 *
 * @author maryx
 */
public class Estat0 extends Estat {

    private int fase = 0;
    private ScannedRobotEvent enemy;

    @Override
    void torn() {
        switch (fase) {
            case 0 -> {
                r.turnRadarRight(360);
            }
            case 1 -> {
                double[][] corners = {
                    {0, 0},
                    {r.getBattleFieldWidth(), 0},
                    {0, r.getBattleFieldHeight()},
                    {r.getBattleFieldWidth(), r.getBattleFieldHeight()}
                };
                        String[] cornerNames = {"Bottom-left", "Bottom-right", "Top-left", "Top-right"};

                double eX = r.getX() + (sin(r.getHeadingRadians() + enemy.getBearingRadians()) * enemy.getDistance());
                double eY = r.getY() + (cos(r.getHeadingRadians() + enemy.getBearingRadians()) * enemy.getDistance());

                double maxDistance = -1;
                int cornerIndex = -1;

                for (int i = 0; i < corners.length; i++) {
                    double cornerX = corners[i][0];
                    double cornerY = corners[i][1];
                    double distance = sqrt(pow(eX - cornerX, 2) + Math.pow(eY - cornerY, 2));

                    if (distance > maxDistance) {
                        maxDistance = distance;
                        cornerIndex = i;
                    }
                    System.out.print(cornerNames[i]+":"+distance+"\n");
                }

                inf.x = corners[cornerIndex][0];
                inf.y = corners[cornerIndex][1];
                System.out.print(cornerNames[cornerIndex] + "+" + inf.x + "\n");
            }
        }
    }

    @Override
    void onScannedRobot(ScannedRobotEvent e) {
        fase = 1;
        enemy = e;
    }
}
