package Tests;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

import Parser.ScriptParser;
import ScriptIO.ScriptFileIO;
import java.lang.annotation.Annotation;
import java.lang.annotation.Target;

public class Tester {

    public static void main(String[] args) {
        
        ScriptParserTester.test();

    }

    public void runTests() {

        String testClassName = this.getClass().getSimpleName();
        System.out.println("--TESTING " + testClassName + "--");
        Predicate<Method> hasTestAnnnotation = (Method method) -> method.isAnnotationPresent(Test.class);

        List<Method> methods = List.of(this.getClass().getDeclaredMethods());
        methods.stream()
                .filter(hasTestAnnnotation)
                .forEach((Method method) -> {

                    System.out.print("[Test]\t"+ method.getName() + " - ");
                    try {
                        method.invoke(this);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

        System.out.println();

    }

}
