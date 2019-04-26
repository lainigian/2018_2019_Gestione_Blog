import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Classe che utilizza il metodo http DELETE per 
 * eliminare un post su blog Montagna (https://gian1000.blogspot.com/) 
 * vanno sostituiti nel codice gli opportuni valori del token di autenticazione 
 * e dell' Id del post da eliminare 
 * 
 * La richiesta utilizza le Blogger API 3.0 di Google
 * @author Laini Gian Marco
 *
 */
public class DeletePost 
{
	private static int status;
	//sostituire con il token di autenticazione
	private static String token_authentication="ya29.Glv0BsFTWW_QwVGm5pi8ewJDJZ2Ov-KjwsvuVRGCdKHogphQDcejaQPJh9FKVnMJwytpYzErS09aeM6b-cvNGh_zZ81G-JbmFnzzowZjVm2sdM-QyoFRLL5HOYS6";
	//sostituire con l'ID del post da eliminare 
	private static String idPostDaEliminare="4000318432428965635";
	
	public DeletePost(String id_post) throws IOException
	{
		//Il numero 4756... è l'id del post Montagna, all'URL va concatenato l' ID del post da eliminare
		String url="https://www.googleapis.com/blogger/v3/blogs/4756282144685082102/posts/";
		url+=id_post;
		URL server=new URL(url);
		HttpURLConnection service = (HttpURLConnection) server.openConnection();
		service.setDoOutput(true);
		service.setRequestProperty("Host", "blogger.com");
		
		//Inserire il token che consente la modifica:.... Bearer <token>
		service.setRequestProperty("Authorization", "Bearer "+token_authentication);
		
		service.setRequestMethod("DELETE");
		service.connect();
		status=service.getResponseCode();
		
	}
	
	
	public static void main(String[] args) throws IOException 
	{
		
		
		DeletePost delete=new DeletePost(idPostDaEliminare);
		System.out.println("Stato della risposta: "+status);
		if (status==204)
			System.out.println("post eliminato");
		else
			System.out.println("Errore nell'utilizzo del servizio");
	}

}
