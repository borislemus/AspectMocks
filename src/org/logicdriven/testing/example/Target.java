/**
 * AspectMocks - AOP-based Mock generation for JUnit tests.
 * Copyright (C) 2009 Boris Lemus
 *
 * This file Target.java is part of AspectMocks.
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
package org.logicdriven.testing.example;

/**
 * An example test target class.
 * @author Boris Lemus
 */
public class Target {

    Collaborator collab = new Collaborator();

    public int doSomething(Integer value) {
        int factor = collab.getFactor();
        collab.anotherFactor();
        Collaborator.aStaticMethod();
        new Collaborator2().someMethod(115);
        return (value * factor);
    }

    public void generateView(){
        
    }
}
