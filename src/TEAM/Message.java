/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TEAM;

import java.io.Serializable;
import java.util.*;

/**
 *
 * @author llucc
 */
public class Message implements Serializable {
    String sender;
    String receiver = "";
    String type;
    int num;
    double x, y;
    
    public Message(String r, String type){
        this.sender = r;
        this.type = type;
    }
    
    public void setInt(int num){
        this.num = num;
    }
    
    public void setReceiver(String r){
        receiver = r;
    }
    
    public void setCoords(double x, double y){
        this.x = x;
        this.y = y;
    }
}
