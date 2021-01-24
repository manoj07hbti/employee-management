package com.csc.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration  // This is configuration class - this bean will be registered by Spring context
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;
    @Autowired
    BCryptPasswordEncoder bCryptEncoder;

   @Bean
    public PasswordEncoder getPasswordEncoder() {return NoOpPasswordEncoder.getInstance();
    }
// Database security
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .usersByUsernameQuery("select username, password, enabled "+
                        "from user_accounts where username = ?" )
                .authoritiesByUsernameQuery("select username, role "+
                        "from user_accounts where username = ?")
                .dataSource(dataSource)
                .passwordEncoder(bCryptEncoder);

    }
    // in Memory Authentication  username and password for Application
 /* @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {

        authenticationManagerBuilder.inMemoryAuthentication()
                .withUser("test")
                            .password("test")
                            .roles("USER")
                .and()
                .withUser("user")
                            .password("pass")
                            .roles("USER")
                .and().withUser("adminuser")
                            .password("admin")
                            .roles("ADMIN");
    }*/

    // Setting Authorization : what user can do
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.authorizeRequests()
                .antMatchers("/student_index").hasRole("ADMIN")
                .antMatchers("/showNewStudentForm").hasRole("ADMIN")
               // .antMatchers("/h2_consloe/**").permitAll()  // for h2 console
                .antMatchers("/", "/**").permitAll()
                .and()
                .formLogin();


        httpSecurity.csrf().disable();
        httpSecurity.headers().frameOptions().disable();

    }


 /* // with H2 database
    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {

        authenticationManagerBuilder.jdbcAuthentication().dataSource(dataSource)
                .withDefaultSchema()
                .withUser("test")
                .password("test")
                .roles("USER")
                .and()
                .withUser("user")
                .password("pass")
                .roles("USER")
                .and().withUser("adminuser")
                .password("admin")
                .roles("ADMIN");
    } */





}
