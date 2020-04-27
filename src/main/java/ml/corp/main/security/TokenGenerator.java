package ml.corp.main.security;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import ml.corp.main.model.Token;

public class TokenGenerator {
	
	public static Token buildTokens(String tokenId) {		
		
		Date date = new Date(System.currentTimeMillis());
		Date expiresOn = new Date(System.currentTimeMillis()+180000);
		
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils
				.commaSeparatedStringToAuthorityList("ROLE_USER");
		
		String accessToken = Jwts
				.builder()
				.setId(new UUID(12, 13).toString())
				.setSubject(tokenId)
				.claim("authorities",
						grantedAuthorities.stream()
								.map(GrantedAuthority::getAuthority)
								.collect(Collectors.toList()))
				.setIssuedAt(date)
				.setExpiration(expiresOn)
				.signWith(SignatureAlgorithm.HS512,
						TokenFilter.getSECRET_KEY().getBytes()).compact();
		accessToken = "Bearer " + accessToken;
		
		Token token = new Token();
		token.setAccessToken(accessToken);
		token.setExpiresOn(1800L);
		
		return token;
	}
	
}
