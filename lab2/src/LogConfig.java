import java.io.IOException;
import java.util.logging.*;

public class LogConfig {

    public static void setup() throws IOException {
        Logger logger = Logger.getLogger("");

        FileHandler fh = new FileHandler("logs/calc.log", true);
        fh.setFormatter(new SimpleFormatter());

        logger.addHandler(fh);
        logger.setLevel(Level.ALL);
    }

}
