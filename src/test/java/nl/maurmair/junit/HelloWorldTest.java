package nl.maurmair.junit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class HelloWorldTest {
    @Test
    public final void testSayHello(){
        HelloWorld helloWorld = new HelloWorld();
        String answer = helloWorld.sayHello();
        Assertions.assertEquals("Hello World", answer);
    }
}
