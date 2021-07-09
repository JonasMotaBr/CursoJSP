package TCP;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;

import javax.swing.JOptionPane;

public class TCPCliente {

	public static void main(String[] args) {
		
		Socket cliente = null;
	    try {
	    	
	    	
			cliente = new Socket("127.0.0.1",378);
			DataInputStream entrada = new DataInputStream(cliente.getInputStream());
			DataOutputStream saida = new DataOutputStream(cliente.getOutputStream());
			//Saida de Stream
			saida.writeUTF("Testando");
			//Entrada de Stream
			String recebida = entrada.readUTF();

			System.out.println("Recebido do servidor:" + recebida);
			    
			    
			
			
		} catch (UnknownHostException e) {
			System.out.println(e);
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			
		}finally {
			try {
				if(cliente!= null) {
				cliente.close();}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		

	}

}
