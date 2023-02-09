import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Test
 */
public class Test {

    public static void main(String[] args) {


            Pattern p = Pattern.compile("hello (.*)");
            Matcher m = p.matcher("hello bob my name is sally");
            m.matches();

            for (int i = 1; i <= m.groupCount(); i++) {

                System.out.println(m.group(i));
                
            }

    }

}