package com.app.mouse.services;

import com.app.mouse.annocations.Inject;
import com.app.mouse.annocations.Injectable;

@Injectable
public class ExampleInjection {

    @Inject
    public ExampleClass exampleClass;

    public void executionExampleMethod() {
        exampleClass.exampleMethod();
    }
}
