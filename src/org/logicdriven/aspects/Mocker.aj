/**
 * AspectMocks - AOP-based Mock generation for JUnit tests.
 * Copyright (C) 2009 Boris Lemus
 *
 * This file Mocker.java is part of AspectMocks.
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

package org.logicdriven.aspects;

import org.logicdriven.testing.AspectMocks;
import org.aspectj.lang.Signature;


/**The main aspect, instruments all public methods.*/
public privileged aspect Mocker{
    //Some common excluded pointcuts
    pointcut exclusions(): !call(* AspectMocks.*(..)) && !within(AspectMocks) && !within(Mocker) && !call(* java.lang.*..*(..))
        && !call(* java.io.*..*(..)) && !call(* java.util.*..*(..));
    //Pointcutting all public methods except when executed from the AspectMocks class as this would cause a stack overflow
    //when calling the isMocked(Object) method which would recursively advice the ArrayList.contains() method. call(* *..*(..))
    pointcut publicCalls(Object tgt): target(tgt) && (call(public * *..*(..)) || call(private * *..*(..)))
        && exclusions();
    //Pointcutting static methods: no target available.
    pointcut staticCalls(): execution(static * *..*(..)) && exclusions();
    //Pointcutting constructors.
    pointcut constructorCalls(): call(*.new(..)) && !within(Mocker) && !within(AspectMocks) && !cflow(adviceexecution());

    Object around(Object tgt): publicCalls(tgt){
        //System.out.println(">> Object of type: " + tgt.getClass().getName());
        //System.out.println(">> Configured target: " + TestEnv.getTargetClass());
        String joinPointString = thisJoinPoint.toString();
        //System.out.print(">> Intercepting. Object class: " + tgt.getClass().getName() + "\n   - JoinPoint: " + joinPointString);

        if(!AspectMocks.isMocked(tgt)){//The default behaviour is not to override a class.
            return proceed(tgt);
        }else{
            Object rtValue=null;
            if((rtValue = AspectMocks.getMockReturnValue(joinPointString))!= null){
                System.out.print(">> Intercepting. Object class: " + tgt.getClass().getName() +
                "\n   - JoinPoint: " + joinPointString + "\n");
                System.out.println("   -> mocking with return value -> " + rtValue);
                return rtValue;
            }else{//Mock method not specified, proceeding normally.
                System.out.println("-> proceeding unmocked method.");
                return proceed(tgt);
            }
        }
    }

    Object around(): staticCalls(){
        String joinPointString = thisJoinPoint.toString();
        //System.out.println("Static JoinPoint -> " + joinPointString);
        if(!AspectMocks.isMocked(thisJoinPointStaticPart.getSourceLocation().getWithinType())){
            return proceed();
        }else{
            Object rtValue=null;
            if((rtValue = AspectMocks.getMockReturnValue(joinPointString))!= null){
                System.out.print(">> Intercepting Class: " + thisJoinPointStaticPart.getSourceLocation().getWithinType().getName() +
                "\n   - JoinPoint: " + joinPointString + "\n");
                System.out.println("   -> mocking with return value -> " + rtValue);
                return rtValue;
            }else{//Mock method not specified, proceeding normally.
                System.out.println("-> proceeding unmocked method.");
                return proceed();
           }
        }
    }
}