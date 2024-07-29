package com.app.mouse;

import com.app.mouse.services.ExampleInjection;
import com.app.mouse.utils.Container;

public class Main {
    public static void main(String[] args) throws Exception {
//        Object exClass = new com.app.mouse.services.ExampleClass("Hello World");
//        // 通过反射获取class对象
//        Class classObject = exClass.getClass();
//
//        // 通过反射获取字段
//        Field nameField = classObject.getField("name");
//        // 获取字段值和名字
//        Object value = nameField.get(exClass);
//        String name = nameField.getName();
//        System.out.println(value);
//        System.out.println(name);
//
//        // 获取方法
//        // 获取public方法，无参数
//        Method exampleMethod = classObject.getMethod("exampleMethod");
//        exampleMethod.invoke(exClass);
//
//        // 调用构造器
//        Constructor constructor = classObject.getConstructor(String.class);
//        com.app.mouse.services.ExampleClass exampleClassObject = (com.app.mouse.services.ExampleClass) constructor.newInstance("Hello World");
//        exampleClassObject.exampleMethod();
//
//        // 动态创建接口实例并调用
//        InvocationHandler handler = new InvocationHandler() {
//            @Override
//            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//                System.out.println(method);
//                if (method.getName().equals("doSomething")) {
//                    System.out.println("Good morning！");
//                }
//                return null;
//            }
//        };
//        com.app.mouse.interfaces.ExampleInterface hello = (com.app.mouse.interfaces.ExampleInterface) Proxy.newProxyInstance(
//                com.app.mouse.interfaces.ExampleInterface.class.getClassLoader(), // 传入ClassLoader
//                new Class[] { com.app.mouse.interfaces.ExampleInterface.class }, // 传入要实现的接口
//                handler); // 传入处理调用方法的InvocationHandler
//        hello.doSomething();

        Container container = new Container();
        container.scan();
        container.injectServices();
        ExampleInjection exampleInjection = container.getService(ExampleInjection.class);
        exampleInjection.executionExampleMethod();
    }
}