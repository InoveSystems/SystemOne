package ComRede;

import Telas.PainelPrincipal;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mertins
 */
public class Conexao {

    private Socket socket;
    private ObjectOutputStream output;

    public Socket connect() {
        try {
            this.socket = new Socket("192.168.2.101", 8888);
            this.output = new ObjectOutputStream(socket.getOutputStream());
        } catch (UnknownHostException e) {
             System.out.println("erro ao conectar 1"); 
        } catch (SocketException ex) {
             System.out.println("erro ao conectar 2"); 
        } catch (IOException ex) {
           System.out.println("erro ao conectar 3"); 
        }

        return socket;
    }

    public void send(Mensagem message) {
        try {
            output.writeObject(message);

        } catch (NullPointerException ex) {
            //Socket esta fechado. Fazer alguma coisa nessa excessão.  
        } catch (SocketException ex) {

        } catch (IOException ex) {
            Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
