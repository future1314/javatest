package com.app.reflection;

public class T1 {

    public static void main(String[] args) {

        //第一种方法：forName
        try {
            Class<?> class1 = Class.forName("com.app.reflection.Person");

            System.out.println( class1 );
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        //第二张方法：class
        Class<?> class2 = Person.class;

        //第三种方法：getClass
        Person person = new Person();
        Class<?> class3 = person.getClass();

        System.out.println( class2 );
        System.out.println( class3 );
    }

}
