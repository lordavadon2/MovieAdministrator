package cinema.util;

public class Parser {

    public static String[] encodeDataTime(String dateTime){
        String[] newDataTime = dateTime.split(" ");
        return newDataTime;
    }

    public static String decodeDataTime(String data, String time){
        String newDataTime = data + " " + time;
        return newDataTime;
    }
}
