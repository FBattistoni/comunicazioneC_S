/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package comunicazione_client;

/**
 *
 * @author Gaming
 */
public class mainClient {
    public static void main(String[] args) {
        Client client= new Client("PROVA","Rosso");
        client.connetti("127.0.0.1",1789);
        client.scrivi();
        client.leggi();
        client.chiudi();
    }
}
