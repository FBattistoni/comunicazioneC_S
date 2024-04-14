/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.comunicazione_c_s;
import comunicazione_client.Client;
import java.io.*;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author FBattistoni
 */
public class Server extends Thread{
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private int porta;
    private BufferedReader inDalClient;
    private DataOutputStream outVersoClient; //teoricamente potrebbe essere possibile con un gestore di stringhe di caratteri
    private boolean chiuso;
    private BufferedReader tastiera; //servirà per prendere input da tastiera

    public boolean isChiuso() { //variabile che sereve a controllare che il csnale non sia chiuso
        return chiuso;
    }
    
    public Server(Socket clientSocket) {
        try {
            this.clientSocket = clientSocket;
            this.chiuso=true;
            tastiera = new BufferedReader(new InputStreamReader(System.in));
            inDalClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            outVersoClient = new DataOutputStream(clientSocket.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
            
            
    
   
    
    @Override
    public void run() {
        while(this.isChiuso()){
        this.leggi();
        if(this.isChiuso())
            this.scrivi();
      }
    }

    public void scrivi() {
        try {
            System.out.println("Scrivi il messaggio che vuoi inviare");
            String messaggio = tastiera.readLine();
            outVersoClient.writeBytes(messaggio + '\n');
            System.out.println("messaggio inviato");
             if(messaggio.equals("chiudi")){
                this.chiudi();
                this.chiuso=false; //trasformo la variabile creata in precedenza in false in modo da uscire dal while nel main
            }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void leggi() {
        try {
            String messaggioRicevuto = inDalClient.readLine(); //assegna alla variabile il messaggio ricevuto dal client
            System.out.println("messaggio ricevuto: " + messaggioRicevuto); 
            if(messaggioRicevuto.equals("chiudi")){ //controlla che il messaggio inviato non corrisponda alla parola chiave per la chiusura della comunicazione
                this.chiudi();
                this.chiuso=false;
            }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void chiudi() {
        try {
                // Chiudo il socket 
                this.clientSocket.close();
                System.out.println("Connessione chiusa.");
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
    }

    public void termina() {
        try {
            //close
            serverSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

 