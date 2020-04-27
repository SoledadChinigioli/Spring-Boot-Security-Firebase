package ml.corp.main.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.firebase.auth.ExportedUserRecord;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.ListUsersPage;

import ml.corp.main.model.User;

@RestController
@RequestMapping(path = "users")
@CrossOrigin(origins = "*")
public class UsersController {

	@GetMapping(path = "")
	public ResponseEntity<List<User>> getAllUsers() throws FirebaseAuthException {
		ListUsersPage page = FirebaseAuth.getInstance().listUsers(null);
		List<User> users = new ArrayList<User>();
		while (page != null) {
		  for (ExportedUserRecord user : page.getValues()) {
		    User userLocal = new User();
		    userLocal.setUid(user.getUid());
		    userLocal.setEmail(user.getEmail());
		    userLocal.setName(user.getDisplayName());
		    users.add(userLocal);
		  }
		  page = page.getNextPage();
		}
		
		return ResponseEntity.status(200).body(users);
		
	}
	
}
