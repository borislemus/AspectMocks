/**
 * AspectMocks - AOP-based Mock generation for JUnit tests.
 * Copyright (C) 2009 Boris Lemus
 *
 * This file TargetTest.java is part of AspectMocks.
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

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.logicdriven.testing.AspectMocks.*;

/**
 * A very simple example of the usage of AspectMocks.
 * @author Boris Lemus
 */
public class TargetTest {

    public TargetTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testDoSomething() {
        addMockClass(Collaborator.class);
        addMockMethod("Collaborator.getFactor()", 5);
        System.out.println("doSomething");
        Integer value = 10;
        Target instance = new Target();
        int expResult = 50;
        int result = instance.doSomething(value);
        assertEquals(expResult, result);
    }
}