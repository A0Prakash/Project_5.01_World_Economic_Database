import java.io.File;
import java.util.Scanner;

public class DataParser {
    public static void giveMeAClue()    {
        String[] line;
        try {
            Scanner in = new Scanner(new File("IEF_2023_data.txt"));
            line = in.nextLine().split("\t");
            for(String intem: line)
                System.out.println(intem);
        } catch (Exception except) {
            except.printStackTrace();
        }
    }

    public static void main(String[] args) {
        giveMeAClue();
    }
}
