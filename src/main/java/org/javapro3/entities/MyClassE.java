package org.javapro3.entities;

import org.javapro3.annotations.*;

public class MyClassE implements MyClasses {
    private int a;
    private int b;

    public MyClassE() {
    }

    public MyClassE(int a, int b) {
        this.a = a;
        this.b = b;
    }

    @BeforeSuite
    public static void additionA(){
        System.out.println("run additionA");
    }

    @AfterSuite
    public void differenceA(){
        System.out.println("run differenceA");
    }

    @Test(priority=2)
    public void multiplication2(){
        System.out.println("run multiplication2");
    }

    @Test
    public void multiplication5(){
        System.out.println("run multiplication5");
    }

    @Test(priority=4)
    public void multiplication4(){
        System.out.println("run multiplication4");
    }

    @Test(priority=3)
    public void multiplication3(){
        System.out.println("run multiplication3");
    }

    @CsvSource("10, Java, 20, true")
    public void testMethodA(int a, String b, int c, boolean d){
        System.out.println("run testMethodA a=" + a + " b=" + b + " c=" + c + " d=" + d);
    }

    @CsvSource("700, C#, 10, false")
    public void testMethodB(int a, String b, int c, boolean d){
        System.out.println("run testMethodB a=" + a + " b=" + b + " c=" + c + " d=" + d);
    }


}
