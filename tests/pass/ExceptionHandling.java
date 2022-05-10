package pass;

import java.lang.System;
import java.lang.Exception;
import java.lang.ArithmeticException;

public class ExceptionHandling  {
    public static void main(String[] args) throws Exception  {
        //testThrows();
        testTryCatch();
        testTryCatchNested();
        testTryCatchTwo();
        testTryFinally();
        testTryCatchFinally();
    }
    public static void testThrows() throws ArithmeticException{
        throw new ArithmeticException();
    }
    public static void testTryCatch(){
        try{
            System.out.println(1/0);
        }
        catch (Exception e){
            System.out.println("catch");
        }
    }
    public static void testTryCatchTwo(){
        try{
            System.out.println(1/0);
        }
        catch (ArithmeticException e){
            System.out.println("catch");
        }
        catch (Exception e){
            System.out.println("catch");
        }
    }
    public static void testTryCatchNested(){
        try{
            int x = 3;
            try{
                System.out.println(1/0);
            }
            catch (Exception e){
                System.out.println("catch1");
            }
        }
        catch (Exception e){
            System.out.println("catch");
        }
    }
    public static void testTryFinally(){
        try{
            System.out.println("try");
        }

        finally{
            System.out.println("finally");
        }
    }
    public static void testTryCatchFinally(){
        try{
            System.out.println(1/0);
        }
        catch (Exception e){
            int x = 2;
            System.out.println(e.getMessage());
        }
        catch (ArithmeticException e){
            int x = 2;
            System.out.println(e.getMessage());
        }
        finally{
            System.out.println("finally");
        }
    }
}
