package com.netreaders.config;

import com.netreaders.security.JwtAuthEntryPoint;
import com.netreaders.security.JwtAuthTokenFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private UserDetailsService userService;
    private JwtAuthEntryPoint unauthorizedHandler;
    private JwtAuthTokenFilter tokenFilter;
    private BCryptPasswordEncoder passwordEncoder;

    @Bean
    protected CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and()
                .authorizeRequests()
                .antMatchers("/api/users/createAdmin").hasAuthority("SUPER_ADMIN")
                .antMatchers("api/users/removeAdmin").hasAuthority("SUPER_ADMIN")
                .antMatchers("/api/users/createModerator").hasAnyAuthority("ADMIN", "SUPER_ADMIN")
                .antMatchers("api/users/removeModerator").hasAnyAuthority("ADMIN", "SUPER_ADMIN")
                .antMatchers("api/users/getRolesForModerator").hasAnyAuthority("ADMIN", "SUPER_ADMIN")
                .antMatchers("api/users/updateModerator").hasAnyAuthority("ADMIN", "SUPER_ADMIN")
                .antMatchers("/userpage").hasAuthority("USER")
                .antMatchers("/api/reviews/unpublished**").hasAuthority("REVIEW_MODERATOR")
                .antMatchers("/api/reviews/publish").hasAuthority("REVIEW_MODERATOR")
                .antMatchers("/api/books/addToLibrary").hasAuthority("USER")
                .antMatchers("/api/books/removeFromLibrary").hasAuthority("USER")
                .antMatchers("/api/books/checkInLibrary").hasAuthority("USER")
                .antMatchers("/api/books/addToFavorites").hasAuthority("USER")
                .antMatchers("/api/books/removeFromFavorites").hasAuthority("USER")
                .antMatchers("/api/books/checkInFavorites").hasAuthority("USER")
                .antMatchers("/api/books/addToFavorites").hasAuthority("USER")
                .antMatchers("/api/books/removeFromFavorites").hasAuthority("USER")
                .antMatchers("/api/books/checkInFavorites").hasAuthority("USER")
                .antMatchers("/api/books/publish").hasAuthority("OVERVIEW_MODERATOR")
                .antMatchers("/api/books/add").hasAuthority("USER")
                .antMatchers("/api/books/unpublished**").hasAuthority("OVERVIEW_MODERATOR")
                .anyRequest().permitAll()
                .and()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().csrf().disable();

        http.addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(userService)
                .passwordEncoder(passwordEncoder)
                .and()
                .authenticationProvider(authenticationProvider());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    private DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService);
        auth.setPasswordEncoder(passwordEncoder);
        auth.setHideUserNotFoundExceptions(false);
        return auth;
    }

}

