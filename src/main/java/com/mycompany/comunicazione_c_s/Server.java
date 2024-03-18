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
 * @author Gaming
 */
public class Server {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private int porta;
    private BufferedReader inDalClient;
    private DataOutputStream outVersoClient; //teoricamente potrebbe essere possibile con un gestore di stringhe di caratteri
    private BufferedReader tastiera; //servirà per prendere input da tastiera

    public Server(int porta) {
        this.porta=porta;
        try {
            
            this.serverSocket=new ServerSocket(this.porta);
            System.out.println("Il server è in ascolto sulla porta : "+this.porta);
            tastiera = new BufferedReader(new InputStreamReader(System.in));
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("Errore in fase di apertura e ascolto ");
        }
    }

    public void attendi() {
        
        try {
            if(serverSocket != null){
                //accept,istaura una connessione
             this.clientSocket= serverSocket.accept();
            System.out.println("Connessione stabilita con il Client");
            this.termina(); //chiudo il socket del server in attesa di connesione dal client in quanto comunicazione a un solo thread
            inDalClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            outVersoClient = new DataOutputStream(clientSocket.getOutputStream());
            }
            
        }catch(BindException ex){
            System.err.println("Porta già in uso");
        }
        catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("Errore nella fase di connessione con il client");
        }
    }

    public void scrivi() {
        try {
            System.out.println("Scrivi il messaggio che vuoi inviare");
            String messaggio = tastiera.readLine();
            outVersoClient.writeBytes(messaggio + '\n');
            System.out.println("messaggio inviato");
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void leggi() {
        try {
            String messaggioRicevuto = inDalClient.readLine(); //assegna alla variabile il messaggio ricevuto dal client
            System.out.println("messaggio ricevuto: " + messaggioRicevuto); 
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

 