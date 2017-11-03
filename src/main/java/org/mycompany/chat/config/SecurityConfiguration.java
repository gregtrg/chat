package org.mycompany.chat.config;

import lombok.AllArgsConstructor;
import org.mycompany.chat.security.jwt.JWTConfigurer;
import org.mycompany.chat.security.jwt.TokenProvider;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.annotation.PostConstruct;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final TokenProvider tokenProvider;

    private final UserDetailsService userDetailsService;


    @PostConstruct
    public void authenticationManagerBuilder() {
        try {
            authenticationManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
        } catch (Exception e) {
            throw new BeanInitializationException("Security configuration failed", e);
        }
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
//            .addFilterBefore(corsFilter(), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                    .authenticationEntryPoint(http403ForbiddenEntryPoint())
            .and()
                .cors()
            .and()
                .csrf()
                    .disable()
            .headers()
                .frameOptions()
                        .disable()
            .and()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
                .authorizeRequests()
//                .antMatchers("/api/register").permitAll()
//                .antMatchers("/api/activate").permitAll()
//                .antMatchers("/api/authenticate").permitAll()
//                .antMatchers("/api/account/reset-password/init").permitAll()
//                .antMatchers("/api/account/reset-password/finish").permitAll()
//                .antMatchers("/api/profile-info").permitAll()
                    .antMatchers("/chat/authenticate").permitAll()
                    .antMatchers("/socket/**").permitAll()
                    .antMatchers("/chat/**").authenticated()
//                .antMatchers("/management/health").permitAll()
//                .antMatchers("/management/**").hasAuthority(AuthoritiesConstants.ADMIN)
//                .antMatchers("/v2/api-docs/**").permitAll()
//                .antMatchers("/swagger-resources/configuration/ui").permitAll()
//                .antMatchers("/swagger-ui/index.html").hasAuthority(AuthoritiesConstants.ADMIN)
            .and()
                .apply(securityConfigurerAdapter());

    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration.applyPermitDefaultValues());
        return source;
    }

    //@Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("user").password("password").roles("USER").build());
        manager.createUser(User.withUsername("admin").password("password").roles("USER", "ADMIN").build());
        return manager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public Http403ForbiddenEntryPoint http403ForbiddenEntryPoint() {
        return new Http403ForbiddenEntryPoint();
    }

//    @Bean
//    CorsFilter corsFilter() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(Arrays.asList("*"));
//        configuration.setAllowedMethods(Arrays.asList("GET"));
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return new CorsFilter(source);
//    }

    private JWTConfigurer securityConfigurerAdapter() {
        return new JWTConfigurer(tokenProvider);
    }
}
