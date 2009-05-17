/**
 * AspectMocks - AOP-based Mock generation for JUnit tests.
 * Copyright (C) 2009 Boris Lemus
 *
 * This file AspectMocks.java is part of AspectMocks.
 *
 * AspectMocks is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * AspectMocks is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with AspectMocs.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.logicdriven.testing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * The core class to set the static test environment properties.
 * @author Boris Lemus
 */
public class AspectMocks {

    /**Maps a mocked method signature to its configured return value.*/
    private static Map<String, Object> mockedMethods = new HashMap<String, Object>();
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
        System.out.println("--> Requested sig: " + sig);
        Object val = null;
        if (!mockedMethods.containsKey(sig)) {
            System.out.println("  --> sig not found!!");
        } else {
            val = mockedMethods.get(sig);
        }
        return val;
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
                System.out.println("HIT!! isMocked -> " + obj.getClass().getName());
                return true;
            }
        }
        return false;
    }
}
