package general.logging;

public class Logger {

    public static TxtLogger logger;

    public static void log(String mesg) {
        logger.log(mesg);
    }

}
