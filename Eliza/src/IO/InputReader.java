import java.util.Arrays;
import java.util.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class InputReader {


    public static void main(String[] args){
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);
        try{
            while(true){
                String input = br.readLine();
            List<String> inputList = Arrays.asList(input.split(" "));
            System.out.println(inputList);
            }
        } catch(IOException e){
            e.getMessage();
        }
    }
}
