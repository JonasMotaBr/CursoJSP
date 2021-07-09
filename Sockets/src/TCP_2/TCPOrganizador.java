package TCP_2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class TCPOrganizador {

	
	
	private String enderecoServer = "127.0.0.1";
	private Socket OrganizadortSocket;
	private int portaServidor = TCPServer.getPorta();
	private Scanner teclado;
	private BufferedWriter SolicitaTotalizaçãoVotos;
	private BufferedReader entrada;

	 public void StartOrganizador() throws UnknownHostException, IOException {
		 
		 OrganizadortSocket = new Socket(enderecoServer, portaServidor);
	     //envia nome da classe
	     BufferedWriter enviaclasse = new BufferedWriter(new OutputStreamWriter(OrganizadortSocket.getOutputStream()));
	     String classe = TCPOrganizador.class.toString() ;
	     enviaclasse.write(classe);
	     enviaclasse.newLine();
	     enviaclasse.flush();
	     
	     //exibe no console
		 System.out.println("****** Organizador ********");
	     System.out.println("Organizador conectado servidor: "+enderecoServer + portaServidor);
	     System.out.println("");
	     System.out.println("Solicitado lista de votos: ");
	
	     //recebe lista de votos
	     BufferedReader recebeListaVotos = new BufferedReader(new InputStreamReader(OrganizadortSocket.getInputStream()));
	     String lista = recebeListaVotos.readLine();
	     
         if(lista != null) {
         	 System.out.println(lista);
		 
	    }
	
	
	 }
	
	// ######### MAIN #############
	public static void main(String[] args) {
	  
		TCPOrganizador organizador = new TCPOrganizador();
		try {
			organizador.StartOrganizador();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
