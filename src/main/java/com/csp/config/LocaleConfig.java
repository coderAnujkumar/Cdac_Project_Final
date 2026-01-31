package com.csp.config;

import java.util.Locale;
//Locale represents a language + country

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;




//support multiple languages 

@Configuration // Ye class configuration ke liye hai, yahan beans define honge
public class LocaleConfig implements WebMvcConfigurer {

    @Bean //create object autometicaly
    //LocaleResolver decides which language is used it is a interface
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver(); //store selected language per user session
        slr.setDefaultLocale(Locale.ENGLISH); // default language 
        return slr;
    }

    @Bean //interceptor does: Checks URL for parameter:?lang=hi Stores it in session
    //Every request passes through LocaleChangeInterceptor
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");   // ?lang=hi or ?lang=mr
        return lci;
    }

    @Override //You must register it with Spring MVC
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }
}
