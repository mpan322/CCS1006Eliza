import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Test
 */
public class Test {

    public static void main(String[] args) {

            Random rand = new Random();
            System.out.println(rand.nextInt(100));

            List<String> list = new ArrayList<>();
            list.addAll(null);
            System.out.println(list);

    }

}