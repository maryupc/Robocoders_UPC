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
    //List<String> teamates = new ArrayList();
    //List<Integer> teamPos = new ArrayList();
    int pos; // Posicio en la jerarquia
    String follow; // Nom del robot a qui seguir
    String sendCoords; // Nom del robot al qual li enviem les coordenades a seguir
    boolean estat0Acabat = false;
    ScannedRobotEvent closestEnemy;
    boolean encontrado = false;
}
