package santa.seedcopy;

public class SeedCopyLogger {

    public static void printLogMessage(String message) {
        if (Config.enableLogging) {
            System.out.println(message);
        }
    }
}
