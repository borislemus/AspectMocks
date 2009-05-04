/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.logicdriven.testing.example;

/**
 * Represents the class to be tested.
 * @author Boris Lemus
 */
public final class Target {

    Collaborator collab = new Collaborator();

    public int doSomething(Integer value) {
        int factor = collab.getFactor();
        collab.anotherFactor();
        return (value * factor);
    }
}
