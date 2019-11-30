package com.netreaders.security;

import com.netreaders.dao.role.RoleDao;
import com.netreaders.dao.user.UserDao;
import com.netreaders.models.Role;
import com.netreaders.models.User;
import com.netreaders.service.UserService;
import com.netreaders.service.impl.RoleServiceImpl;
import com.netreaders.service.impl.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@AllArgsConstructor
@Component
public class JwtAuthTokenFilter extends OncePerRequestFilter {

    private final JwtProvider tokenProvider;
    private final UserDao userDao;
    private final RoleDao roleDao;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {

            String jwt = getJwt(request);
            if (jwt != null && tokenProvider.validateJwtToken(jwt)) {// returns UserDetails
            	String username = tokenProvider.getUserNameFromJwtToken(jwt);
                UsernamePasswordAuthenticationToken authentication = prepareAuthentication(username);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            logger.error("Can NOT set user authentication -> Message: {}", e);
        }

        filterChain.doFilter(request, response);
    }
    
    private UsernamePasswordAuthenticationToken prepareAuthentication(String username) {
        User user = userDao.findByUsername(username);
        Collection<Role> roles = roleDao.findByUserId(user.getId());
        UserDetails userDetails = UserPrinciple.build(user, roles); // returns UserDetails
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        return authentication;
    }
    
    public UserDetails setContextAuthentication(String username) {
    	
    	UsernamePasswordAuthenticationToken authentication = prepareAuthentication(username);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return (UserDetails) authentication.getPrincipal();
    }

    private String getJwt(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.replace("Bearer ", "");
        }

        return null;
    }
}
