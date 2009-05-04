/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
 *
 * @author aleksei
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