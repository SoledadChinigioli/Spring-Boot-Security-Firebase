package ml.corp.main.controllers;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;

import ml.corp.main.model.AuthToken;
import ml.corp.main.security.TokenGenerator;

@RestController
@RequestMapping(path = "token")
@CrossOrigin(origins = "*")
public class AuthController {
	
	@PostMapping(path = "")
	public ResponseEntity getToken(@RequestBody AuthToken authToken) throws IOException, FirebaseAuthException {
		try {
			FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(authToken.getToken());
			String uid = decodedToken.getUid();
			return ResponseEntity.status(200).body(TokenGenerator.buildTokens(uid));
		} catch (Exception e) {
			return ResponseEntity.status(401).body("");
		}
	}
	
}
