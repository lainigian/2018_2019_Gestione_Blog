import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Classe che utilizza il metodo http POST per 
 * aggiungere un post su blog Montagna (https://gian1000.blogspot.com/) 
 * va sostituito nel codice l' opportuno valorre del token di autenticazione 
 * Il post è aggiunto al body della richiesta POST come una stringa in formato JSON 
 * contenente il TITOLO  e il TESTO acquisiti dalla console
 * 
 * La richiesta utilizza le Blogger API 3.0 di Google
 * @author Laini Gian Marco
 *
 */
public class CreatePostConsole 
{
	private static int status;
	//sostituire con il token di autenticazione
	private static String token_authentication="ya29.Glv0BiXz2WNW4assIHdO-fQQOw7uL5j35bm5auE6-057IttcvFNI9hrt4UQKZ7GnS4CzL7slQcfcg9Exz_RQZxz-EDbDFeMNRH1_1Xey-xvHQbviWV8badCLXLBV";

	
	
	
	//nel body del POST invio una stringa di testo nella sintassi json contenente titolo e testo del post
	public CreatePostConsole(String post_json) throws IOException
	{
		BufferedWriter output;
		
			
		//Il numero 4756... è l'id del post Montagna
		URL server=new URL("https://www.googleapis.com/blogger/v3/blogs/4756282144685082102/posts/");
		HttpURLConnection service = (HttpURLConnection) server.openConnection();
		service.setDoOutput(true);
		service.setRequestProperty("Host", "blogger.com");
		
		//Inserire il token che consente la modifica:.... Bearer <token>
		service.setRequestProperty("Authorization", "Bearer "+token_authentication);
		service.setRequestProperty("Content-Type", "application/json");
		
		service.setRequestMethod("POST");
		
		output=new BufferedWriter(new OutputStreamWriter(service.getOutputStream(),"UTF-8"));
		
		output.write(post_json);
		output.close();
		
		service.connect();
		status=service.getResponseCode();
	}
	
	//crea una stringa di testo in formato json che contiene titolo e testo del post
	private static String creaPost(String titolo, String testo)
	{
		String stringa_json;	
		stringa_json="{\"kind\": \"blogger#post\", \"blog\": {\"id\": \"4756282144685082102\" },\"title\": \""+titolo+"\",\"content\": \""+testo+"\"}";
		return stringa_json;
	}
	public static void main(String[] args) throws IOException 
	{
		ConsoleInput tastiera= new ConsoleInput();
		String titolo;
		String testo;
		String post_json;
		System.out.println("Inserisci il titolo del post: ");
		titolo=tastiera.readLine();
		System.out.println("Inserisci il testo del post: ");
		testo=tastiera.readLine();
		post_json=creaPost(titolo,testo);
		CreatePostConsole create=new CreatePostConsole(post_json);
		System.out.println("Stato della risposta: "+status);
		if (status==200)
			System.out.println("post creato");
		else
			System.out.println("Errore nell'utilizzo del servizio");
	}
}
