/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.logicdriven.testing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * The core class to set the static Test Environment properties.
 * @author Boris Lemus
 */
public class AspectMocks {

    /**Maps a mocked method signature to its configured return value.*/
    private static Map<String, Object> mockedMethods = new HashMap();
    /**The list of classes to be mocked.*/
    private static ArrayList<Class> mockedClasses = new ArrayList<Class>();

    /**Parses an FQN JoinPoint string representation to a non-qualified string
     * where only the Class name and Method signature are returned.
     * @param the JoinPoint String representation as returned by JoinPoint.toString(). */
    private static String parseJoinPoint(String joinPointString) {
        String tokens[] = joinPointString.split("[.]");//We tokenize the string by dots
        //Returning the Class name + a dot + the method signature.
        return tokens[tokens.length - 2] + "." +
                tokens[tokens.length - 1].substring(
                0, tokens[tokens.length - 1].length() - 1);
    }

    /**Adds a method signature to be mocked whenever a matching signature is
     * found in the test's flow of execution, specifying a return value.
     * @param methodSignature the non-qualified method signature to be mocked.
     * @param returnValue the desired return value for this mocked method.
     */
    public static void addMockMethod(String methodSignature, Object returnValue) {
        mockedMethods.put(methodSignature, returnValue);
    }

    /**Simply a wrapper to the internal Map of signatures to return values. So
     * the behaviour is the same as for the Map.get(Object) method. 
     * @param methodSignature the JoinPoint String representation as returned by JoinPoint.toString().
     * @return the return value configured for a mocked method or null if that method wasn't mocked.
     */
    public static Object getMockReturnValue(String joinPointString) {
        String sig = parseJoinPoint(joinPointString);
        return mockedMethods.get(sig);
    }

    /**Adds a class to be mocked.*/
    public static void addMockClass(Class mockedClass) {
        mockedClasses.add(mockedClass);
    }

    /**Checks wether a class is in the list of mocked classes.*/
    public static boolean isMocked(Object obj) {
        Iterator<Class> classItr = mockedClasses.iterator();
        while (classItr.hasNext()) {
            Class aux = classItr.next();
            if (aux.isInstance(obj)) {
                return true;
            }
        }
        return false;
    }
}
