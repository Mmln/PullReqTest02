package org.javapro3.entities;

import org.javapro3.annotations.AfterSuite;
import org.javapro3.annotations.BeforeSuite;
import org.javapro3.annotations.CsvSource;
import org.javapro3.annotations.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

public class TestRunner {
    public TestRunner() {
    }

    public static <c> void runTests(Class<?> c) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Map<Method,Annotation> csvSources = new HashMap<>();
        Map<Integer,Method> tests = new HashMap<>();
        Map<Integer,Method> sortedTests = new TreeMap<>(Comparator.reverseOrder());
        c invkClass = (c) c.getConstructor().newInstance();
        int beforeSuite = 0;
        int afterSuite = 0;
        Method mtBefore = null;
        Method mtAfter = null;

        Method[] mets = c.getMethods();
        for (Method mt : mets){
            Annotation[] annotations = mt.getAnnotations();
            for (Annotation an : annotations) {
                if(mt.isAnnotationPresent(BeforeSuite.class)) {
                    beforeSuite += 1;
                    if(!Modifier.isStatic(mt.getModifiers())) throw new RuntimeException("\nmetod=<" + mt + "> не статический для аннотации @BeforeSuite");
                    if(beforeSuite > 1) throw new RuntimeException("\nУ метода=<" + mt + "> повторно встретилась @BeforeSuite");
                    mtBefore = mt;
                }
                if(mt.isAnnotationPresent(AfterSuite.class)) {
                    afterSuite += 1;
                    if(!Modifier.isStatic(mt.getModifiers())) throw new RuntimeException("\nmetod=<" + mt + "> не статический для аннотации @AfterSuite");
                    if(afterSuite > 1) throw new RuntimeException("\nУ метода=<" + mt + "> повторно встретилась @AfterSuite");
                    mtAfter = mt;
                }
                if(mt.isAnnotationPresent(Test.class)) {
                    Annotation anno = mt.getAnnotation(Test.class);
                    if(((Test) anno).priority() > 10 || ((Test) anno).priority() < 1) throw new RuntimeException("metod=<" + mt +
                            ">\nприоритет аннотации <" + anno + "> не соответствует диапазону 1...10 .");
                    tests.put(((Test) anno).priority(), mt);
                }
                if(mt.isAnnotationPresent(CsvSource.class)) {
                    csvSources.put(mt ,an);
                }
            }
        }

        System.out.print("@BeforeSuite: ");
        mtBefore.invoke( invkClass);

        sortedTests.putAll(tests);
        for (Map.Entry<Integer,Method> entry : sortedTests.entrySet())
        {
            System.out.print("@Test: ");
            entry.getValue().invoke( invkClass);
        }
        for (Map.Entry<Method,Annotation> entry : csvSources.entrySet())
        {
            String[] params  = ((CsvSource) entry.getValue()).value().split(",");
            for(int i=0; i < params.length; i++) params[i] = params[i].replace(" ", "");
            int pA = Integer.parseInt(params[0]);
            String pB = params[1];
            int pC = Integer.parseInt(params[2]);
            boolean pD = Boolean.parseBoolean(params[3]);

            System.out.print("@CsvSource: ");
            entry.getKey().invoke( invkClass, pA, pB, pC, pD);
        }

        System.out.print("@AfterSuite: ");
        mtAfter.invoke( invkClass);
    }


}
