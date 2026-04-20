//utility class for getting inputs to be used in main game loop and all the playable games
//I tested it out too and it should work i hope
import java.util.*;

public class GetInput
{
    private final Scanner reader = new Scanner(System.in);

    public int getInt()
    {
        System.out.println("Enter a positive integer:");
        String num = reader.next();
        while(!num.matches("\\d+"))
        {
            System.out.println("Invalid input: enter a positive integer");
            num = reader.next();
        }
        return Integer.parseInt(num);
    }

    public int getIntInRange(int min, int max)
    {
        System.out.println("Enter an integer between "+min+" & "+max+":");
        String num = reader.next();
        while(true){
            if(num.matches("\\d+")){
                int num2 = Integer.parseInt(num);
                if(num2>=min && num2<=max){
                    return num2;
                }
            }
            System.out.println("Invalid input: must be an integer between "+min+" & "+max+":");
            num=reader.next();
        }
    }

    public int getOddInt()
    {
        System.out.println("Enter an odd positive integer");
        String num = reader.next();
        while(true){
            if(num.matches("\\d+")){
                int num2 = Integer.parseInt(num);
                if(num2%2==1){
                    return num2;
                }
            }
            System.out.println("Invalid input: must be an odd positive integer:");
            num = reader.next();
        }
    }

    public char getChar(char[]validChars)
    {
        String chars = java.util.Arrays.toString(validChars);
        System.out.println("Enter one of these characters: "+chars);
        String myChar = reader.next();
        while(true)
        {  
            if(myChar.length()==1){
                char myChar2 = myChar.charAt(0);
                for(char c: validChars){
                    if(myChar2 == c){return myChar2;}
                }
            }
            System.out.println("Error: enter one of these characters: "+chars);
            myChar = reader.next();
        }
    }
}
