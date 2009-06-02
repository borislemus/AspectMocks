/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.logicdriven.testing.example;

/**
 *
 * @author Boris Lemus
 */
public class Collaborator2 {

    /**Just to exemplify how to mock a parameterized method.*/
    public String someMethod(int value) {
        System.out.println(value);
        return String.valueOf(value);
    }
}
