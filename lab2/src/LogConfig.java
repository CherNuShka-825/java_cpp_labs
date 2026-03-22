import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.logging.*;

public class LogConfig {

    public static void setup() {
        try {
            new java.io.File("logs").mkdirs();

            Logger rootLogger = Logger.getLogger("");

            for (Handler h : rootLogger.getHandlers()) {
                rootLogger.removeHandler(h);
            }

            FileHandler fileHandler = new FileHandler(createFileName(), true);
            fileHandler.setFormatter(new SimpleFormatter());

            ConsoleHandler consoleHandler = new ConsoleHandler();

            rootLogger.addHandler(fileHandler);
            rootLogger.addHandler(consoleHandler);

            rootLogger.setLevel(Level.ALL);
        } catch (IOException e) {
            System.err.println("Logger init failed: " + e.getMessage());
        }
    }

    private static String createFileName() {
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_H-mm-ss"));
        return "logs/calc_" + time + ".log";
    }
}
