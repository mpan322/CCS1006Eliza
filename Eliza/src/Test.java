import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * Test
 */
public class Test {

    public static void main(String[] args) {
        
        Pattern x = Pattern.compile("hello (.*) my name is bob");

        ArrayList<Integer> a = new ArrayList<>();

        int i = 0;
        a.add(i, 1);

        System.out.println(a);

    }

}