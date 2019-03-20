package com.example.demo.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.repository.JwtTokenRepository;
import com.example.demo.service.UserDetailsServiceImpl;

/*In JwtAuthTokenFilter class, the doFilterInternal method will do:
1. get JWT token from header
2. validate JWT
3. parse username from validated JWT
4. load data from users table, then build an authentication object
5. set the authentication object to Security Context*/
public class JwtAuthTokenFilter extends OncePerRequestFilter {

	/*
	 * @Bean public JwtResponse jwtResponse() { return new JwtResponse(); }
	 */
	@Autowired
	JwtTokenRepository tokenRepo;
    @Autowired
    private JwtProvider tokenProvider;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthTokenFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, 
    								HttpServletResponse response, 
    								FilterChain filterChain) 
    										throws ServletException, IOException {
        try {
        	
            String jwt = getJwt(request);
            if (jwt!=null && tokenProvider.validateJwtToken(jwt)) {
            	System.out.println("-----------------jwt-------------------"+jwt);
                String username = tokenProvider.getUserNameFromJwtToken(jwt);
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication 
                		= new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
               
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            logger.error("Can NOT set user authentication -> Message: {}", e);
        }
       
        filterChain.doFilter(request, response);
    }

    private String getJwt(HttpServletRequest request) {
    	   String authHeader = request.getHeader("Authorization");
       	System.out.println("-----------out----------"+authHeader);
           if (authHeader != null && authHeader.startsWith("Bearer ")) {
        	   System.out.println("-----------in----------"+authHeader);
           	return authHeader.replace("Bearer ","");
           	
           }

           return null;
       }

    }

