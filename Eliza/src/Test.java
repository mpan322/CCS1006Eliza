import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Test
 */
public class Test {

    public static void main(String[] args) {


        String test = "([[+][*][$][\\\\]])";
        String words = "don't aren't";
        Pattern x = Pattern.compile(test);
        Matcher m = x.matcher(words);

        System.err.println("hello".replaceAll("ell", "fllo"));

        String out = words.replaceAll("([^\s]*)n't", "$1 not");

        Matcher m2 = Pattern.compile("a (.*)").matcher("a elepant");

        System.out.println(m2.matches());

        // m.results().forEach((res) -> {

        //     System.out.println(res.group());

        // });        

    }

}