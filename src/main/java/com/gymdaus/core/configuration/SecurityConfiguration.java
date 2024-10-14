package com.gymdaus.core.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
	
	@Autowired
	@Qualifier("userService")
	private UserDetailsService userService;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService).passwordEncoder(new BCryptPasswordEncoder());
	}
	
	/*@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("/css/*", "/imgs/*", "/imgs/icons/*", "/imgs/icons/fonts/*", "/js/*").permitAll()
		.antMatchers("/").permitAll()
		.antMatchers("/olvidoClave").permitAll()
		.antMatchers("/nuevaClave").permitAll()
		.antMatchers("/pass-new").permitAll()
		.antMatchers("/change-pass").permitAll()
		.antMatchers("/formulario/alta").permitAll()
		.anyRequest().authenticated()
		.and()
		.formLogin().loginPage("/loginPage").loginProcessingUrl("/logincheck")
		.usernameParameter("username").passwordParameter("password")
		.defaultSuccessUrl("/principal/").permitAll()
		.and()
		.logout().logoutUrl("/logout").logoutSuccessUrl("/loginPage?logout").permitAll();
	}*/

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				.authorizeHttpRequests(authorizeRequests ->
						authorizeRequests
								/*Estas son las rutas que se permiten sin autenticación*/
								.requestMatchers("/css/*", "/imgs/*", "/js/*").permitAll()
								.requestMatchers("/", "/olvidoClave", "/nuevaClave", "/pass-new", "/change-pass", "/formulario/alta").permitAll()
								.anyRequest().authenticated()
				)
				.formLogin(formLogin ->
						/*Esta es la configuración del login personalizado*/
						formLogin
								.loginPage("/loginPage")
								.loginProcessingUrl("/logincheck")
								.usernameParameter("username")
								.passwordParameter("password")
								.defaultSuccessUrl("/principal/")
								.permitAll()
				)
				.logout(logout ->
						/*Esta es la configuración de logout personalizada*/
						logout
								.logoutUrl("/logout")
								.logoutSuccessUrl("/loginPage?logout")
								.permitAll()
				);

		return http.build();
	}

}
