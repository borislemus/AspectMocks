/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.logicdriven.testing.example;

/**
 * Represents a colaborator with the Target of a test.
 * @author Boris Lemus
 */
public final class Collaborator {

    public boolean saveToFile(String value) {
        System.out.println(value + " has been saved.");
        return true;
    }

    public int getFactor() {
        return 10;
    }

    public int anotherFactor(){
        myPrivateMethod();
        return 4;
    }

    private int myPrivateMethod(){
        System.out.println("Private method invoked");
        return 0;
    }
}
