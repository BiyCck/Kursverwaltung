package com.hsba.bi.fitnessstudio.Fitnessstudio;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/weekplan/showWeekplan").permitAll()
                .antMatchers("/courses/showCourses").permitAll()
                .antMatchers("/user/showTrainer").permitAll()
                .antMatchers("/user/showTrainerSite").hasRole("TRAINER")
                .antMatchers("/user/editTrainer").hasRole("TRAINER")
                .antMatchers("/user/showUsers").hasRole("ADMIN")
                .antMatchers("/user/addUser").hasRole("ADMIN")
                .antMatchers("/weekplan/addAppointment").hasRole("ADMIN")
                .antMatchers("/weekplan/editAppointment").hasRole("ADMIN")
                .antMatchers("/weekplan/selectTrainer").hasRole("ADMIN")
                .antMatchers("/courses/addCourse").hasRole("ADMIN")
                .antMatchers("/courses/editCourse").hasRole("ADMIN")
                .antMatchers("/").permitAll()
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
            .logout()
                .logoutSuccessUrl("/weekplan/")
                .permitAll();
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/h2-console/**");
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
