package com.example;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CreativeAspect {

    @Pointcut("execution(public * com.example.*.*(..))")
    public void publicMethod(){}


    @After("@annotation(create)")
    public void create(){
        System.out.println("__________________ ##\n" +
                "_________________###*\n" +
                "______________.*#####\n" +
                "_____________*######\n" +
                "___________*#######\n" +
                "__________*########.\n" +
                "_________*#########.\n" +
                "_________*#######*##*\n" +
                "________*#########*###\n" +
                "_______*##########*__*##\n" +
                "_____*###########_____*\n" +
                "____############\n" +
                "___*##*#########\n" +
                "___*_____########\n" +
                "__________#######\n" +
                "___________*######\n" +
                "____________*#####*\n" +
                "______________*####*\n" +
                "________________*####\n" +
                "__________________*##*\n" +
                "____________________*##\n" +
                "_____________________*##.\n" +
                "____________________.#####.\n" +
                "_________________.##########\n" +
                "________________.####*__*####");
    }

    @After("@annotation(art)")
    public void realityCheck(){
        System.out.println("I didnt draw that dolphin... I copied and pasted it from the internet.");
    }
}
