//package com.example.demo.controller;
//
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@EnableWebMvc
//@ComponentScan("org.springframework.security.samples.mvc")
//public class WebMvcConfiguration implements WebMvcConfigurer {
//
//    // ...
//
//    @Override
//    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController("/loginpage").setViewName("authenticate");
//        registry.addViewController("/error").setViewName("error");
////        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
//    }
//}