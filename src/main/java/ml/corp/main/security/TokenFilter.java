package ml.corp.main.security;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.PrematureJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

public class TokenFilter extends OncePerRequestFilter{

	private static String SECRET_KEY = "SoiUltr4sEgur0";
	private String TOKEN_PREFIX = "Bearer ";
	private String TOKEN;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			
			if(checkJWT(request)) {
				Claims claims = validateToken();
				if(claims.get("authorities") != null) {
					setUpSpringAuth(claims);
				}
				else {
					SecurityContextHolder.clearContext();
				}
			}
			filterChain.doFilter(request, response);
			
		} catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | PrematureJwtException | SignatureException e) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
			return;
		}
		
	}
	
	public boolean checkJWT(HttpServletRequest request){
		TOKEN = request.getHeader("Authorization");
		if (TOKEN == null || !TOKEN.startsWith(TOKEN_PREFIX))
			return false;
		return true;		
	}
	
	private Claims validateToken() {		
		return Jwts.parser().setSigningKey(SECRET_KEY.getBytes()).parseClaimsJws(TOKEN.replace("Bearer ", "")).getBody();
	}
	
	private void setUpSpringAuth(Claims claims) {
		List<String> authorities = (List<String>) claims.get("authorities");
		
		UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(claims.getSubject(), null,
				authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
		SecurityContextHolder.getContext().setAuthentication(auth);		
	}

	public static String getSECRET_KEY() {
		return SECRET_KEY;
	}
	
}
