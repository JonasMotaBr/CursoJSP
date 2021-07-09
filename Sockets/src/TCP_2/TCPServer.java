package TCP_2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TCPServer {

	
	private static int porta = 4020;
	private ServerSocket serverSock;
	private BufferedReader entrada;
	private BufferedWriter saida;
	private BufferedWriter confirmacao;
	private ArrayList<String> Listacanditados = new ArrayList<String>();;
	private ArrayList<Integer> ListaVotos = new ArrayList<Integer>();;
	private TCPCliente tcpcliente;
	private TCPOrganizador tcpOrganizador;
	
	
	
	//MetodoStart
	public void start() throws IOException {
		System.out.println("****SERVIDOR******");
		System.out.println("Endereço maquina:");
		byte[] b = InetAddress.getByName("localhost").getAddress();
		System.out.println(b[0] + "." + b[1] + "." + b[2] + "." + b[3]);
        System.out.println("Endereço: " + InetAddress.getByName("localhost").getHostAddress() );
        byte[] addr = {127,0,0,1};
        System.out.println("HostName: "+ InetAddress.getByAddress(addr).getHostName());
        System.out.println("Porta: "+porta);
        serverSock = new ServerSocket(porta);
        addCandidato();
        clienteConexaoLoop();
       // OrganizadorConexaoLoop();
		
	}
	//metodo loop
	public void clienteConexaoLoop() throws IOException{
		while(true) {
			//conecta a um socket
			Socket cliente = serverSock.accept();
			
			 //recebe nome classe conectada
			BufferedReader classe = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
			String stringClasse = classe.readLine();
            
            
            //inicia socket do cliente
            if(stringClasse.equalsIgnoreCase(TCPCliente.class.toString())) {
	
			   System.out.println("Cliente " + cliente.getRemoteSocketAddress() + ", conectou");
			   BufferedReader entrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
               String msg = entrada.readLine();
               System.out.println(msg);
	
              
               //caso 'SIM', envia lista para cliente
               if (msg.equalsIgnoreCase("sim")) {
				
			     saida = new BufferedWriter(new OutputStreamWriter(cliente.getOutputStream()));
			     saida.write(Listacanditados.toString());
			     saida.newLine();
			     saida.flush();
			     }
            
                 //recebe voto do cliente
                 BufferedReader entrada2 = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
                 String votorecebidoString = entrada.readLine();
                 int votorecebido = Integer.parseInt(votorecebidoString);
            
                 
                 //voto ok, envia a confirmação
                 if((entrada2 != null) && (consultarcadidato(votorecebido)) ) {
                 //add a lista
                 ListaVotos.add(votorecebido);
                 String msgConfirmacao = "Voto confirmado!";
                 confirmacao = new BufferedWriter(new OutputStreamWriter(cliente.getOutputStream()));
            	 confirmacao.write(msgConfirmacao);
            	 confirmacao.newLine();
            	 confirmacao.flush();
                  System.out.println("Lista de votos:" +ListaVotos);
            	  }
                 else {
                	 String msgConfirmacao = "Numero incorreto!";
                     confirmacao = new BufferedWriter(new OutputStreamWriter(cliente.getOutputStream()));
                	 confirmacao.write(msgConfirmacao);
                	 confirmacao.newLine();
                	 confirmacao.flush();
                	 cliente.close();
                 }
		     }
            
            //inicia soket organizador
            if (stringClasse.equalsIgnoreCase(TCPOrganizador.class.toString())) {
            	
            	int Candidato1=0;
            	int Candidato2=0;
            	
            	for(int votos : ListaVotos) {
            		if(votos == 1) {
            			Candidato1++;
            		}         		
            		else if(votos == 2) {
            			Candidato2++;
            		}
            	}
            	
            	String contagemVotos = "Candidato A: "+ Candidato1 +","
 		+ "Candidato B: "+Candidato2;
            	BufferedWriter saidaContagem = new BufferedWriter(new OutputStreamWriter(cliente.getOutputStream()));
            	saidaContagem.write(contagemVotos);
            	saidaContagem.newLine();
            	saidaContagem.flush();
            	
            	
            	
            	
            	
				
			}
            
            
            
		}//fim do while
		
		
	}
	

	
	public void OrganizadorConexaoLoop() throws IOException {
		while(true) {
		Socket organizador = serverSock.accept();
		System.out.println("Organizador " + organizador.getRemoteSocketAddress() + "conectou");
		}
		
	}

    //metodo getPorta
	public static int getPorta() {
		return porta;
	}
	
	
	public boolean consultarcadidato(int numCandidato) {
		
		if(numCandidato == 1 || numCandidato == 2) {
			return true;
		}
		
		//for (String lista : Listacanditados) {
       // 	lista.equalsIgnoreCase("");
        //	return true;
		//}
		return false;
	}
	
	public void addCandidato() {
		Listacanditados.add("1-Candidato A");
		Listacanditados.add("2-Candidato B");
	}
	


	
	//Main ***********************************
	public static void main(String[] args) {
		
		try {
			
			TCPServer server = new TCPServer();
			server.start();
				
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		
		
		
	}

}
