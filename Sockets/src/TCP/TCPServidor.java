package TCP;

import java.io.DataInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;

public class TCPServidor {

	public static void main(String[] args) {
		System.out.println("Endereço maquina:");
		
		
		try {
		byte[] b = InetAddress.getByName("localhost").getAddress();
				System.out.println(b[0] + "." + b[1] + "." + b[2] + "." + b[3]);
		        System.out.println("Endereço: " + InetAddress.getByName("localhost").getHostAddress() );
		        byte[] addr = {127,0,0,1};
		        System.out.println("HostName: "+ InetAddress.getByAddress(addr).getHostName());
			
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}
	
		
		try {
			int porta = 378;
//			if (args[0].length() > 0 ) porta = Integer.parseInt(args[0]);{
				
			ServerSocket servidor = new ServerSocket(porta);
			
			System.out.println("**Servidor**");
			System.out.println("**Porta de escuta:" + porta +" **");
			
			
		
			while(true) {
				Socket cliente = servidor.accept();
				
				System.out.println("Cliente conectado: " + cliente.getInetAddress().getHostAddress());
			
				 ObjectOutputStream saida = new ObjectOutputStream(cliente.getOutputStream());
				 saida.flush();
//				 saida.writeUTF("teste");
//			     System.out.println("Saida: "+saida.toString());
//			       
				
				 DataInputStream recebido = new DataInputStream(cliente.getInputStream());
				 StringBuffer inputLine = new StringBuffer();
		            String tmp; 
		            while ((tmp = recebido.readLine()) != null) {
		                inputLine.append(tmp);
		                System.out.println(tmp);
		            }
		            //use inputLine.toString(); here it would have whole source
		            recebido.close();
			     
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		

	}

}
