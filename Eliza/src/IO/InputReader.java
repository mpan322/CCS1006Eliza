

import java.util.Arrays;
import java.util.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class InputReader {

    public static BufferedReader createReader(){
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);
        return br;
    }


    public static String getInput(BufferedReader br){
        String input = "";
        try{
            input = br.readLine();
        } catch(IOException e){
            e.getMessage();
        }
        return input;
    }

    public static void main(String[] args){
        BufferedReader br = createReader();
        getInput(br);
    }
}
