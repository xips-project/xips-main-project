package trastu.dev.xips.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import trastu.dev.xips.entities.Permission;

@Configuration
@EnableWebSecurity
public class HttpSecurityConfig {

    private final AuthenticationProvider authenticationProvider;

    public HttpSecurityConfig(AuthenticationProvider authenticationProvider) {
        this.authenticationProvider = authenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sessionManagerConfig -> sessionManagerConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .authorizeHttpRequests(authConfig -> {
                    authConfig.requestMatchers(HttpMethod.POST, "/api/v1/products/create").hasAuthority(Permission.SAVE_ONE_PRODUCT.name());
                   authConfig.requestMatchers(HttpMethod.POST, "/auth/login").permitAll();
                   authConfig.requestMatchers(HttpMethod.GET, "/api/v1/products/list").permitAll();
                   authConfig.requestMatchers(HttpMethod.POST, "/api/v1/users/signup").permitAll();
                   authConfig.requestMatchers("/error").permitAll();

                   authConfig.requestMatchers(HttpMethod.GET, "/api/v1/products/list").hasAuthority(Permission.READ_ALL_PRODUCTS.name());

                });


        return httpSecurity.build();
    }
}
