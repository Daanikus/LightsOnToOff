package lightson;
import java.util.Random;

public class TestGenerator {
    

    public static void main(String[] args) {
        int size = Integer.parseInt(args[0]);
        int joins = Integer.parseInt(args[1]);
        Random rand = new Random();

        if (args[0] == null) {
            size = 20;
        }
        if (args[1] == null) {
            joins = 20;
        }

        for (int i = 0; i < size; i++) {
            char c = (char) (rand.nextInt() % 26) + 48;
            System.out.println(c + " ");
        }
        for (int i = 0; i < size; i++) {
            char c = (char) (rand.nextInt() % 26) + 48;
            System.out.println(c + " ");
        }
    }
        
}
