package ml.corp.main.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Token {

	@JsonProperty(value = "access_token")
	private String accessToken;
	@JsonProperty(value = "expires_on")
	private Long expiresOn;

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public Long getExpiresOn() {
		return expiresOn;
	}

	public void setExpiresOn(Long expiresOn) {
		this.expiresOn = expiresOn;
	}

}
