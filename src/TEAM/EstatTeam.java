/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TEAM;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import robocode.*;
import robocode.util.Utils;

/**
 *
 * @author llucc
 */
public abstract class EstatTeam {

    RoboCodersTeam r;
    TeamInfo inf;
    double eneX = -1;
    double eneY = -1;
    double goX = -1;
    double goY = -1;

    abstract void torn();

    abstract void onScannedRobot(ScannedRobotEvent e);

    abstract void onMessageReceived(MessageEvent e);

    abstract void onRobotDeath(RobotDeathEvent event);

    abstract void onPaint(Graphics2D g);

    abstract void goTo(double x, double y);

    void onCreate(RoboCodersTeam robo, TeamInfo info) {
        r = robo;
        inf = info;
    }

    double distance(double x1, double y1, double x2, double y2) {
        double distance = Point2D.distance(x1, y1, x2, y2);
        return distance;
    }

    void updateEnemy(ScannedRobotEvent e) {
        boolean trobat = false;
        double angle = Math.toRadians((r.getHeading() + e.getBearing() % 360));
        double enemyX = r.getX() + Math.sin(angle) * e.getDistance();
        double enemyY = r.getY() + Math.cos(angle) * e.getDistance();
        for (int i = 0; i < inf.enemies.size(); i++) {
            if (inf.enemies.get(i).name.equals(e.getName())) {
                trobat = true;

                inf.enemies.get(i).fill(enemyX, enemyY, e.getDistance(), e.getBearing());
            }
        }
        if (!trobat) {
            inf.enemies.add(new MapRobot(e.getName(), enemyX, enemyY, e.getDistance(), e.getBearing()));
        }
    }
}
