/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.comunicazione_c_s;

/**
 *
 * @author Gaming
 */
public class Comunicazione_c_s {
     public static void main(String[] args) {
      Server server=new Server(1789);
      server.attendi();
      server.leggi();
      server.scrivi();
      server.chiudi();
      
    }

  
}
