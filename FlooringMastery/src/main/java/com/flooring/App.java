package com.flooring;

import com.flooring.controller.Controller;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {
    public static void main(String[] args) { //meant to run through dependency injection
        AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext();
        appContext.scan("com.flooring");
        appContext.refresh();
        // Get your controller bean
        Controller controller = appContext.getBean("controller", Controller.class);

        // Run program
        controller.run();
    }
}
