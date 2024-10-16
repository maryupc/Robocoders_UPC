/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Robocoders;

import robocode.ScannedRobotEvent;

/**
 *
 * @author llucc
 */
public class RobotInfo {
    double x = -1; //Variable que se utiliza para guardar la posición x mas alejada del enemigo
    double y = -1; //Variable que se utilitza para guardar la posicioón y mas alejada del enemigo
    boolean terminado = false; //Se pone a true, cuando hemos acabado con las fases 0 y 1
    boolean encontrado = false; //Se pone a true cuando en la fase 1, detectamos a 1 enemigo en el camino. 
    ScannedRobotEvent closestEnemy; //Variable para ir guardando los datos que se ha recogido de un enemigo en onscannedrobot
    
}
