package TCP_2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class TCPCliente {

	
	private String enderecoServer = "127.0.0.1";
	private Socket clientSocket;
	private int portaServidor = TCPServer.getPorta();
	private Scanner teclado;
	private BufferedWriter solicitalista;
	private BufferedWriter enviaVoto;
	private BufferedReader entrada;
	
	
	    //metodo Start Cliente
	     public void StartCliente() throws UnknownHostException, IOException {
		 clientSocket = new Socket(enderecoServer, portaServidor);
		 
		 //envia nome da classe
		 BufferedWriter enviaclasse = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
	     String classe = TCPCliente.class.toString() ;
	     enviaclasse.write(classe);
	     enviaclasse.newLine();
	     enviaclasse.flush();
	
		
		 System.out.println("****** Cliente ********");
	     System.out.println("Cliente conectado servidor: "+enderecoServer + portaServidor);
	     

		 //solicita lista de candidatos
	        solicitalista = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
	        String msgenviada;
			System.out.println("Solicita lista? SIM/NÂO");
			teclado = new Scanner(System.in);
			msgenviada = teclado.nextLine();
            if (msgenviada.equalsIgnoreCase("sim")) {
            	solicitalista.write(msgenviada);
            	solicitalista.newLine();
            	solicitalista.flush();
			}
	
	        //recebe lista e envia voto
	          enviaVoto = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            BufferedReader entrada = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            BufferedReader entradaConfirmação = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String msgrecebida = entrada.readLine();
            if(msgrecebida != null) {
            	 System.out.println("Recebida servidor: "+msgrecebida);
            	 System.out.println("Digite o numero do candidato");
            	 teclado = new Scanner(System.in);
            	 String voto =  teclado.nextLine();
            	 enviaVoto.write(voto);
            	 enviaVoto.newLine();
            	 enviaVoto.flush();
 			     
 			     //recebe msg confirmação
 			     String msgconfir = entradaConfirmação.readLine();
 			     System.out.println(msgconfir);    
            	 
                }
     
	     }
	
	
	
	
	public void msgLoop() throws IOException {
		  
	}
	public void receberMsg() throws IOException {
		
	}
			
    
    
	//Main ******************************
	public static void main(String[] args) {

		
		try {
			
			TCPCliente client = new TCPCliente();
			client.StartCliente();
		
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		

	}

}
