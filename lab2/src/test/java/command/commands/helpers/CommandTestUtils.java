package command.commands.helpers;

import baseTest.BaseTest;
import command.Context;

public final class CommandTestUtils extends BaseTest {

    private CommandTestUtils() {
    }

    public static Context contextWithStack(double... values) {
        Context context = new Context();
        for (double value : values) {
            context.pushStack(value);
        }
        return context;
    }

    public static Context emptyContext() {
        return new Context();
    }

    public static String[] noArgs() {
        return new String[]{};
    }

}