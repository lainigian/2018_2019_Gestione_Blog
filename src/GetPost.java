import java.io.*;
import java.net.*;

import javax.net.ssl.HttpsURLConnection;

/**
 * Classe che utilizza il metodo http GET per 
 * ottenre un post (identificato da uno specifico ID) del blog Montagna (https://gian1000.blogspot.com/) 
 * La risorsa è ottenuta in formto XML e memorizzata su appositi file. 
 * La richiesta utilizza le Blogger API2.0 di Google perchè forniscono la risposta in XML anche se esse sono ormai state
 * sostituite dalle Blogger API 3.0 di Google
 * 
 * @author Laini Gian Marco
 *
 */

public class GetPost 
{
	
//	private String prefix="https://www.blogger.com/feeds/4756282144685082102/posts/default/?";
//	private String url;
	private String filename;
	private boolean saved=false;
	
	
	public GetPost(String filename)
	{
		URL server;
		HttpsURLConnection service;
		BufferedReader input;
		BufferedWriter output;
		int status;		//stato della risposta (200 OK, 400 NON OK...)
		String line;
		
		this.filename=filename;
		
		try 
		{
			//url=prefix+URLEncoder.encode(address, "UTF-8");
			server=new URL("https://www.blogger.com/feeds/4756282144685082102/posts/default/?blogID=4756282144685082102&postID=7885915365237652933");
			service=(HttpsURLConnection)server.openConnection();
			//impostazione header richiesta
			service.setRequestProperty("Host", "blogger.com");
			service.setRequestProperty("Accept", "application/atom+xml");  ///necessario atom+xml altrimenti non va
			service.setRequestProperty("Accept-Charset", "UTF-8");
			//Imposytazione del metodo della richiesta (GET per operazione Read)
			service.setRequestMethod("GET");
			//Attivo la ricezione
			service.connect();
			//Verifica lo stato della risposta;
			status=service.getResponseCode();
			System.out.println("stato risposta: "+status);
			if (status!=200)
				return;
			
			//apertura stream di ricezione
			input=new BufferedReader(new InputStreamReader(service.getInputStream(), "UTF-8"));
			//apertura stream scrittura su file
			output=new BufferedWriter(new FileWriter(filename));
			
			//ciclo di lettura, legge dal web e scrive sul file
			while ((line=input.readLine())!=null)
			{
				
				output.write(line);
				output.newLine();
			}
			input.close();
			output.close();
			saved=true;
			
		} 
		 catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public boolean isSaved()
	{
		return saved;
	}
	
	public static void main(String args[])
	{
		//String address="blogID=4756282144685082102&postID=7885915365237652933";
		String filename="post_montagna.xml";
		GetPost geocoding=new GetPost(filename);
		if (geocoding.isSaved())
			System.out.println("risposta salvata nel file "+filename);
		else
			System.out.println("Errore interrogazione serivzio");
		
	}
	
}
