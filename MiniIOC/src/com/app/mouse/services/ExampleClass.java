package com.app.mouse.services;

import com.app.mouse.annocations.Check;
import com.app.mouse.annocations.Injectable;

@Injectable
public class ExampleClass {
    // TODO: 解决只能使用默认构造函数的问题
    public ExampleClass() {}

    public ExampleClass(String name) {
        this.name = name;
    }

    public void exampleMethod() {
        System.out.println("Example Method");
    }

    @Check(isNotEmpty = false, value = "Nice")
    public String name;
}
