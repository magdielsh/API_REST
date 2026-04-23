package com.control.visitas.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity        // Activa la configuración de seguridad web de Spring
@EnableMethodSecurity     // Habilita @PreAuthorize en los controllers
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Deshabilitamos CSRF: no es necesario con JWT porque no usamos cookies de sesión
                // CSRF protege contra ataques en apps que usan cookies — nosotros usamos headers
                .csrf(AbstractHttpConfigurer::disable)

                // Configuración de autorización por ruta
                .authorizeHttpRequests(auth -> auth
                        // Estas rutas son públicas — no requieren token
                        .requestMatchers("api/auth/**").permitAll()
                        // Cualquier otra ruta requiere autenticación
                        .anyRequest().authenticated()
                )

                // STATELESS: Spring NO crea ni usa sesiones HTTP
                // Cada petición debe traer su propio JWT — no hay "memoria de sesión"
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // Le decimos qué AuthenticationProvider usar (el nuestro con BCrypt + BD)
                .authenticationProvider(authenticationProvider)

                // Registramos nuestro filtro JWT ANTES del filtro estándar de usuario/password
                // Así nuestro filtro procesa el token antes de que Spring intente autenticar
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

//    @Bean
//    SecurityFilterChain securityWebFilterChain(HttpSecurity httpSecurity) throws Exception {
//        return httpSecurity
//                .csrf(csrfConfigurer -> csrfConfigurer.disable())
//                .authorizeHttpRequests(auth -> {
//                    auth.requestMatchers("api/users/findUser").permitAll();
//                    auth.anyRequest().authenticated();
//                })
//                .sessionManagement(session -> {
//                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//                })
//                .httpBasic(hb ->
//                        hb.authenticationEntryPoint(new CustomAuthenticationEntryPoint()))
//                //.authenticationManager(authenticationManager(userDetailsService(),passwordEncoder()))
//                //                .cors(c -> {
////                    c.configurationSource(request -> {
////                        CorsConfiguration cors = new CorsConfiguration();
////                        //var cors = new org.springframework.web.cors.CorsConfiguration();
////                        cors.addAllowedOrigin("*");
////                        cors.setAllowedMethods(Arrays.asList("GET", "POST", "OPTIONS", "PUT","PATCH", "DELETE"));
////                        cors.setAllowedHeaders(List.of(HttpHeaders.AUTHORIZATION, HttpHeaders.CONTENT_TYPE));
////                        return cors.applyPermitDefaultValues();
////                    });
////                })
//                .build();
//    }
//
//    @Bean
//    UserDetailsService userDetailsService(){
//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//        manager.createUser(User.withUsername("magdielsh")
//                .password("123")
//                .roles()
//                .build());
//
//        return manager;
//    }
//
//    @Bean
//    PasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager(
//            UserDetailsService userDetailsService,
//            PasswordEncoder passwordEncoder) {
//
//        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//        authProvider.setUserDetailsService(userDetailsService);
//        authProvider.setPasswordEncoder(passwordEncoder);
//        return new ProviderManager(authProvider);
//    }



  /*  @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration cc = new CorsConfiguration();
        cc.setAllowedHeaders(Arrays.asList("Origin,Accept", "X-Requested-With", "Content-Type", "Access-Control-Allow-Origin", "Access-Control-Request-Method", "Access-Control-Request-Headers","Authorization"));
        cc.setExposedHeaders(Arrays.asList("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"));
        cc.setAllowedOrigins(Arrays.asList("/*"));
        cc.setAllowedMethods(Arrays.asList("GET", "POST", "OPTIONS", "PUT","PATCH"));
        cc.addAllowedOrigin("*");
        cc.setMaxAge(Duration.ZERO);
        cc.setAllowCredentials(Boolean.TRUE);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", cc);
        return source;
    }*/
}
