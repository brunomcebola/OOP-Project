package auxFunctions;

public class aux {
    public static boolean isNumber(String input){
        char[] c = input.toCharArray();
        for(char aux : c)
            if (!Character.isDigit(aux)) {
                return false;
            }
            return true;
    }
}
