package ml.corp.main.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

@Service
public class FirebaseConfig {

	@PostConstruct
	public void initialize() throws IOException {
		try {
			FileInputStream serviceAccount =
					  new FileInputStream("./firebase.json");

					FirebaseOptions options = new FirebaseOptions.Builder()
					  .setCredentials(GoogleCredentials.fromStream(serviceAccount))
					  .setDatabaseUrl("https://api-rest-4c2f4.firebaseio.com")
					  .build();

					FirebaseApp.initializeApp(options);
		} catch (Exception e) {
			System.out.println("Firebase error: "+e.getMessage());
		}		
	}
	
}
