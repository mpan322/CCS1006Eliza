package Tests;

import java.lang.reflect.Method;
import java.util.List;
import java.util.function.Predicate;

public class Tester {

    public static void main(String[] args) {
        
        ScriptParserTester.test();
        ScriptIOTester.test();
        ScriptTester.test();

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
