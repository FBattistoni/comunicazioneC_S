/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.comunicazione_c_s;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author FBattistoni
 */
public class MultiServer {
    private int porta;
    private ServerSocket serverSocket;

    public MultiServer(int porta) {
        this.porta = porta;
        
        try {
            this.serverSocket = new ServerSocket(porta);
        } catch (IOException ex) {
            Logger.getLogger(MultiServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     public void attendi() {
         Socket clientSocket;
         System.out.println("server in ascolto sulla porta " + this.porta   );
         
                
       for(;;){ 
            try {
                if(serverSocket != null){
                    //accept,istaura una connessione
                clientSocket= serverSocket.accept();
                System.out.println("Connessione stabilita con il Client");
                Server server = new Server(clientSocket);
                server.start();
                }

            }catch(BindException ex){
                System.err.println("Porta già in uso");
            }
            catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                System.err.println("Errore nella fase di connessione con il client");
            }
        }
     }
     
     
}
