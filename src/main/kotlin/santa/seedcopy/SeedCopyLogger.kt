package santa.seedcopy

object SeedCopyLogger {
    /**
     * Prints a log message if the enableLogging config option is true.
     * @param message The message to print.
     */
    @JvmStatic
    fun printLogMessage(message: String) {
        if (Config.ENABLE_LOGGING) {
            println(message)
        }
    }
}