package org.logicdriven.aspects;

import org.logicdriven.testing.AspectMocks;
import org.aspectj.lang.Signature;

public privileged aspect Mocker{
    //Pointcutting all public methods except when executed from the AspectMocks class as this would cause a stack overflow
    //when calling the classIsMocked(Class) method which would recursively advice the ArrayList.contains() method.
    pointcut publicCalls(Object tgt): target(tgt) && call(public * *..*(..)) 
        && !within(AspectMocks) && !call(* java.lang.*..*(..))
        && !call(* java.io.*..*(..));

    Object around(Object tgt): publicCalls(tgt){
        //System.out.println(">> Object of type: " + tgt.getClass().getName());
        //System.out.println(">> Configured target: " + TestEnv.getTargetClass());
        String joinPointString = thisJoinPoint.toString();
        System.out.print(">> Intercepting: " + joinPointString);
        if(!AspectMocks.classIsMocked(tgt.getClass())){//The default behaviour is not to override a class.
            System.out.println(" -> proceeding.");
            return proceed(tgt);
        }else{
            Object rtValue=null;
            if((rtValue = AspectMocks.getMockReturnValue(joinPointString))!= null){
                System.out.println("-> mocking with return value -> " + rtValue);
                return rtValue;
            }else{//Mock method not specified returning null
                System.out.println("-> proceeding unmocked method.");
                return proceed(tgt);
            }
        }
    }

    //A pointcut and advice for private methods
    //pointcut privateCalls(Object tgt): target(tgt) && call(private * com.logicdriven.*.*.*.*(..));

    //Object around(Object tgt): privateCalls(tgt){
      //  System.out.println(">> Private invocation intercepted -> " + thisJoinPoint.toString());
      //  return proceed(tgt);
    //}
}