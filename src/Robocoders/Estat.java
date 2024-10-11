/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Robocoders;

/**
 *
 * @author maryx
 */
public abstract class Estat {
    Robocoders r;
    abstract void torn();
    abstract void onScannedRobot();
    void onCreate(Robocoders robo){
        r = robo;
    }
}
