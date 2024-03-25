/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package comunicazione_client;
import com.mycompany.comunicazione_c_s.Server;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gaming
 */
public class Client {
    private String nome;
    private String colore;
    private Socket socket;
    private BufferedReader tastiera;
    private BufferedReader inDalServer;
    private DataOutputStream outVersoServer;
    private boolean chiuso;

    public boolean isChiuso() {
        return chiuso;
    }
    
    public Client(String nomeDefault, String coloreDefault) {
        this.chiuso = true;
        this.nome = nomeDefault;
        this.colore = coloreDefault;
        tastiera = new BufferedReader(new InputStreamReader(System.in));
    }

    public void connetti(String nomeServer, int portaServer) {
        System.out.println(" ______________________________\n");
        System.out.println("      Client "+nome+" in esecuzione\n");
        System.out.println(" ______________________________\n");
        try {
            this.socket = new Socket(nomeServer, portaServer); 
            System.out.println("Connessione avvenuta con il server");
            inDalServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            outVersoServer = new DataOutputStream(socket.getOutputStream());
        } 
        catch(ConnectException ex){
            System.err.println("Il server non è in ascolto!");
        }
        catch(UnknownHostException ex){ 
            System.err.println("Host sconosciuto!");
            System.err.println( ex.getMessage());
        } catch (IOException e){
            System.err.println("Errore!");
            System.err.println( e.getMessage());
        }
        
    }

    public void scrivi() {
        try {
            System.out.println("Scrivi il messaggio che vuoi inviare");
            String messaggio = tastiera.readLine();
            outVersoServer.writeBytes(messaggio + '\n');
            System.out.println("messaggio inviato");
            if(messaggio.equals("chiudi")){
                this.chiudi();
                this.chiuso = false;
            }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void leggi() {
        try {
            String messaggioRicevuto = inDalServer.readLine(); //assegna alla variabile il messaggio ricevuto dal client
            System.out.println("messaggio ricevuto: " + messaggioRicevuto); 
            if(messaggioRicevuto.equals("chiudi")){
                this.chiudi();
                this.chiuso = false;
            }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void chiudi() {
            try {
                // Chiudi il socket solo se è stato inizializzato correttamente
                this.socket.close();
                System.out.println("Connessione chiusa.");
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        
    }

    
}
