/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TEAM;

import java.util.*;
import robocode.ScannedRobotEvent;

/**
 *
 * @author llucc
 */
public class TeamInfo {

    List<MapRobot> allies = new ArrayList();
    List<MapRobot> enemies = new ArrayList();
    int pos; // Posicio en la jerarquia
    String follow; // Nom del robot a qui seguir
    String sendCoords; // Nom del robot al qual li enviem les coordenades a seguir
    boolean estat0Acabat = false; // Bool per saber quan hem de canviar d'estat
    ScannedRobotEvent closestEnemy;
    boolean encontrado = false;

    boolean findAlly(String n) {
        boolean trobat = false;
        int i = 0;
        while (i < allies.size() && !trobat) {
            if (allies.get(i).name.equals(n)) {
                trobat = true;
            }
            i++;
        }
        return trobat;
    }
}
