/**
 * This file Collaborator.java is part of AspectMocks.
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
 * Represents a collaborator with the target of a test.
 * @author Boris Lemus
 */
public final class Collaborator {

    public boolean saveToFile(String value) {
        //Do something with value
        return true;
    }

    public int getFactor() {
        return 10;
    }

    public int anotherFactor() {
        myPrivateMethod();
        return 4;
    }

    private int myPrivateMethod() {
        return 0;
    }

    public static float aStaticMethod(){
        return 0.5f;
    }
}
