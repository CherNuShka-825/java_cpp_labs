package baseTest;

import org.junit.jupiter.api.BeforeAll;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class BaseTest {

    @BeforeAll
    static void disableLogging() {
        Logger rootLogger = Logger.getLogger("");

        for (Handler h : rootLogger.getHandlers()) {
            rootLogger.removeHandler(h);
        }

        rootLogger.setLevel(Level.OFF);
    }
}