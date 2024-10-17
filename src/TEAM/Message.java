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
    int random;
    
    public Message(String r, int ran){
        this.sender = r;
        this.random = ran;
    }
    
    public void setReceiver(String r){
        receiver = r;
    }
}
