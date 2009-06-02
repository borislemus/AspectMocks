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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * The core class to set the static test environment properties.
 * @author Boris Lemus
 */
public class AspectMocks {

    /**Maps ClassConfig instances to themselves.*/
    private static Map<ClassConfig, ClassConfig> mockedClasses = new HashMap<ClassConfig, ClassConfig>();

    /**Adds a method signature to be mocked whenever a matching signature is
     * found in the test's flow of execution, specifying a return value.
     * @param methodSignature the non-qualified method signature to be mocked.
     * @param returnValue the desired return value for this mocked method.
     */
    public static void addMockMethod(Class targetClass, String methodSignature, Object returnValue) {
        ClassConfig aux = new ClassConfig(targetClass);
        if (!mockedClasses.containsKey(aux)) {
            mockedClasses.put(aux, aux);
        } else {
            aux = mockedClasses.get(aux);
        }
        aux.addMockMethod(methodSignature, returnValue);
    }

    /**Obtain the return value that has been set for a particular method signature.
     * The passed <code>joinPointString</code> is parsed internally to obtain the
     * method signature.
     * @param methodSignature the JoinPoint String representation as returned by JoinPoint.toString().
     * @return the return value configured for a mocked method or <code>null</code> if that method wasn't mocked.
     */
    public static Object getMockReturnValue(Object target, String joinPointString) {
        Object val = null;
        Iterator<ClassConfig> classConfItr = mockedClasses.keySet().iterator();
        while (classConfItr.hasNext()) {
            ClassConfig ccAux = classConfItr.next();
            Class aux = ccAux.type;
            if ((target instanceof Class) ? aux.equals(target) : aux.isInstance(target)) {
                val = ccAux.getMockReturnValue(joinPointString);
                break;
            }
        }
        return val;
    }

    /**Checks wether <code>obj</code> is of a class in the list of mocked
     * classes. <code>obj.getClass()</code> doesn't have to exactly match a
     * class in the list of mocked classes, it could be a subclass of it and in
     * that case <code>true</code> will also be returned.<code>obj</code> could
     * also be an instance of <code>Class</class>, such is the case when the
     * intercepted method is static. */
    public static boolean isMocked(Object obj) {
        Iterator<ClassConfig> classItr = mockedClasses.keySet().iterator();
        while (classItr.hasNext()) {
            Class aux = classItr.next().type;
            if ((obj instanceof Class) ? aux.equals(obj) : aux.isInstance(obj)) {
                System.out.println("HIT!! isMocked -> " + obj.getClass().getName());
                return true;
            }
        }
        return false;
    }

    /**Records the mocking configuration of a particular class. It stores the
     * methods to be mocked and wether the constructors will be overriden.*/
    static class ClassConfig {

        /**The data type of the mocked class.*/
        Class type;
        /**Determines wether constructor invocations on {@link  #type} will be
         * overriden  by the default, no-args constructor.*/
        boolean mockConstructors = false;
        /**Maps a mocked method signature to its configured return value.*/
        private Map<String, Object> mockedMethods;

        public ClassConfig(Class type) {
            this.type = type;
        }

        /**Two ClassConfigs are equal iff their enclosed {@link #type}s are equal.*/
        @Override
        public boolean equals(Object obj) {
            return obj instanceof ClassConfig ? ((ClassConfig) obj).type.equals(this.type) : false;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 97 * hash + (this.type != null ? this.type.hashCode() : 0);
            return hash;
        }

        /**Adds a method signature to be mocked whenever a matching signature is
         * found in the test's flow of execution, specifying a return value.
         * @param methodSignature the non-qualified method signature to be mocked.
         * @param returnValue the desired return value for this mocked method.
         */
        void addMockMethod(String methodSignature, Object returnValue) {
            //Lazily instantiating mockedMethods as many ClassConfigs might
            //be created in a short period of time.
            if (mockedMethods == null) {
                mockedMethods = new HashMap<String, Object>();
            }
            mockedMethods.put(methodSignature, returnValue);
        }

        public Object getMockReturnValue(String joinPointString) {
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

        /**Parses an FQN JoinPoint string representation to a non-qualified string
         * where only the Class name and Method signature are returned.
         * @param the JoinPoint String representation as returned by JoinPoint.toString(). */
        private static String parseJoinPoint(String joinPointString) {
            String tokens[] = joinPointString.split("[.]");//We tokenize the string by dots
            //Returning the Class name + a dot + the method signature.
            return tokens[tokens.length - 1].substring(
                    0, tokens[tokens.length - 1].length() - 1);
        }
    }
}
