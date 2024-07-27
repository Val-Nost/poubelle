package fr.limayrac.poubelle.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig  {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests((request) ->
                        request
                                .requestMatchers(
                                        AntPathRequestMatcher.antMatcher("/css/**"),
                                        AntPathRequestMatcher.antMatcher("/js/**"),
                                        AntPathRequestMatcher.antMatcher("/image/**"),
                                        AntPathRequestMatcher.antMatcher("/favicon/**")
                                ).permitAll()
                                .anyRequest().authenticated()
                )
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.NEVER))
                .formLogin((form) ->
                        form
                                .loginPage("/").permitAll()
                                .loginProcessingUrl("/login")
                                .usernameParameter("login")
                                .passwordParameter("password")
                                .defaultSuccessUrl("/accueil", true)
                                .failureUrl("/?error=true")
                )
                .logout((logout) ->
                        logout
                                .logoutUrl("/logout").permitAll()
                                .invalidateHttpSession(true)
                                .deleteCookies("JSESSIONID")
                                .logoutSuccessUrl("/login")
                )
                .csrf(AbstractHttpConfigurer::disable);
        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
