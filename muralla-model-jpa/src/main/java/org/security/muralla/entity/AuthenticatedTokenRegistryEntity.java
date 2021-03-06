package org.security.muralla.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.security.muralla.model.base.AuthenticatedTokenRegistry;

@Entity
@Table(name = "oauth_authenticated_token_registry")
public class AuthenticatedTokenRegistryEntity extends
		AuthenticatedTokenRegistry {

	public AuthenticatedTokenRegistryEntity() {
		super();
	}

	public AuthenticatedTokenRegistryEntity(AuthenticatedTokenRegistry model) {
		setConsumerKey(model.getConsumerKey());
		setUsername(model.getUsername());
		setTimestamp(model.getTimestamp());
		setNonce(model.getNonce());
		setVerifier(model.getVerifier());
		setToken(model.getToken());
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Override
	public Long getId() {
		return super.getId();
	}

	@Override
	@Column(name = "user_name")
	public String getUsername() {
		return super.getUsername();
	}

	@Override
	@Column(name = "consumer_key")
	public String getConsumerKey() {
		return super.getConsumerKey();
	}

	@Override
	public String getTimestamp() {
		return super.getTimestamp();
	}

	@Override
	public String getNonce() {
		return super.getNonce();
	}

	@Override
	public String getVerifier() {
		return super.getVerifier();
	}

	@Override
	@Column(name = "request_token")
	public String getToken() {
		return super.getToken();
	}
}
