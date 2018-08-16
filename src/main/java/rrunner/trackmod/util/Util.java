package rrunner.trackmod.util;

public class Util
{
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
