package org.security.muralla.resource.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;
import org.security.muralla.model.base.AuthenticatedTokenRegistry;
import org.security.muralla.model.base.RequestTokenRegistry;
import org.security.muralla.service.TokenService;
import org.security.muralla.service.UserService;

@Path("/oauth")
public class AuthTokenResource {
	private static final Logger LOG = Logger.getLogger(AuthTokenResource.class);
	@Inject
	private UserService userService;

	@Inject
	private TokenService tokenService;

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/authorize")
	public Response authenticate(@QueryParam("oauth_token") String token) {
		try {
			RequestTokenRegistry requestTokenRegistry = tokenService
					.getRequestToken(token);
			// Generate verifier
			Random rand = new Random();
			int min = 100000000;
			int max = 999999999;
			int randomNum = rand.nextInt((max - min) + 1) + min;

			List<String> userRoles = new ArrayList<String>();
			for (String userRolName : userService.getUserRoles()) {
				userRoles.add(userRolName);
			}
			
			AuthenticatedTokenRegistry authenticatedTokenRegistry = new AuthenticatedTokenRegistry(
					requestTokenRegistry.getConsumerKey(), userService.getUserName(),
					requestTokenRegistry.getTimestamp(),
					requestTokenRegistry.getNonce(), Integer.valueOf(randomNum)
							.toString(), token, userRoles);

			tokenService.saveAuthenticatedToken(authenticatedTokenRegistry);
			return Response.ok(Integer.valueOf(randomNum).toString()).build();
		} catch (Exception e) {
			LOG.error(e.getMessage());
			return Response.status(Status.BAD_REQUEST).entity(e.getMessage())
					.build();
		}
	}
}
