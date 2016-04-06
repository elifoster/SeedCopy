package santa.seedcopy;

public class SeedCopyLogger {

    /**
     * Prints a log message if the enableLogging config option is true.
     * @param message The message to print.
     */
    public static void printLogMessage(String message) {
        if (Config.enableLogging) {
            System.out.println(message);
        }
    }
}
