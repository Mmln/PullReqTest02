package org.javapro3;

import org.javapro3.annotations.MyClasses;
import org.javapro3.entities.*;

import java.util.List;

import static org.javapro3.entities.TestRunner.runTests;

public class Main {
    public static void main(String[] args) {
        System.out.println("JavaPro3Mmln01 started... WorkFinished ");

        List<MyClasses> clList = List.of(new MyClassA(4,2), new MyClassB(4,2), new MyClassC(4,2),
                new MyClassD(4,2), new MyClassE(4,2), new MyClassF(4,2)  );

        for(MyClasses myClass : clList){
            System.out.println("\nChecking " + myClass + " started...");
            try{
                runTests(myClass.getClass());
            } catch(Exception e){
                System.out.println("Не прокатило с " + myClass + "\nпричина=" + e);
            }
            System.out.println("Checking " + myClass + " finished...");

        }

        System.out.println("\nJavaPro3Mmln01 finished...");
    }
}