package com.example;

import org.apache.commons.lang3.reflect.testbed.Annotated;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * Created by Austin on 12/1/2016.
 */

@Component
@Aspect
public class CreativeMethods {

    @create
    public static void beCreative(){
        System.out.println("This is the peak of my creativity");
    }

    @art
    public static void realityCheck(){
        System.out.println("I dont have an ounce of creativity in my body");
    }
}
