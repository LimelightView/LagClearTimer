package rrunner.trackmod.util;

public class Util
{
    /*
        @Param hexadecimal: The string to be checked
        @Description      : Checks if a string is hexadecimal
     */
    public static boolean isHex(String hexadecimal)
    {
        try{
            long hex = Long.parseLong(hexadecimal, 16);
            return true;
        }catch(NumberFormatException e)
        {
            return false;
        }
    }
}
