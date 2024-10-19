/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TEAM;

/**
 *
 * @author llucc
 */
public class MapRobot {
    String name;
    double x;
    double y;
    double distance;
    double bearing;
    
    public MapRobot(String n){
        this.name = n;
    }
    
    public MapRobot(String n, double x, double y, double d, double b){
        this.name = n;
        this.x = x;
        this.y = y;
        this.distance = d;
        this.bearing = b;
    }
    
    public void fill(double x, double y, double d, double b){
        this.x = x;
        this.y = y;
        this.distance = d;
        this.bearing = b;
    }
}
