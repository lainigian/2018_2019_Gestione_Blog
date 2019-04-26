import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Classe che utilizza il metodo http POST per 
 * aggiungere un post su blog Montagna (https://gian1000.blogspot.com/) 
 * va sostituito nel codice l' opportuno valorre del token di autenticazione 
 * Il post è aggiunto al body della richiesta POST come file JSON 
 * 
 * La richiesta utilizza le Blogger API 3.0 di Google
 * @author Laini Gian Marco
 *
 */
public class CreatePost 
{

	private static int status;
	
	public CreatePost(String filename) throws IOException
	{
		BufferedWriter output;
		BufferedReader input;
		String line;
		
		URL server=new URL("https://www.googleapis.com/blogger/v3/blogs/4756282144685082102/posts/");
		HttpURLConnection service = (HttpURLConnection) server.openConnection();
		service.setDoOutput(true);
		service.setRequestProperty("Host", "blogger.com");
		
		//Inserire il token che consente la modifica:.... Bearer <token>
		service.setRequestProperty("Authorization", "Bearer ya29.Glv0BtdafP9YKLqIycCkWA2VwCWkGepqsoi1m8EbN1HDd_sP6r-Mtj9eqtJRiC5KQieULbqVweGMlwIC65x7fzk1AKHW8vRVhjztR7NLBgf2Uw-z2-Zao7Oiv8AI");
		service.setRequestProperty("Content-Type", "application/json");
		
		service.setRequestMethod("POST");
		
		
		
		//apertura stream di ricezione
		input=new BufferedReader(new FileReader(filename));
		//apertura stream scrittura su file
		output=new BufferedWriter(new OutputStreamWriter(service.getOutputStream(),"UTF-8"));
		
		//ciclo di lettura, legge dal file e scrive sul web service
		while ((line=input.readLine())!=null)
		{		
			output.write(line);		
		}
	
		input.close();
		output.close();
		
		service.connect();
		status=service.getResponseCode();
	}
	public static void main(String[] args) throws IOException 
	{
		//inserisci il nome del file
		String filename="post.json";
		CreatePost create=new CreatePost(filename);
		System.out.println("Stato della risposta: "+status);
		if (status==200)
			System.out.println("post creato");
		else
			System.out.println("Errore nell'utilizzo del servizio");
	}

}
