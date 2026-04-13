package command.commands;

import command.Command;
import command.Context;
import exceptions.CommandException;

import java.util.logging.Logger;

public class SqrtCommand implements Command {

    private static final Logger logger = Logger.getLogger(SqrtCommand.class.getName());

    @Override
    public void execute(Context context, String[] args) throws CommandException {
        if (args.length != 0) {
            logger.warning("SQRT expected 0 args, got " + args.length);
            throw new CommandException("SQRT expected 0 args, got " + args.length);
        }

        double a = context.popStack();

        if (a < 0) {
            logger.warning("SQRT square root of negative number: " + a);
            throw new CommandException("SQRT square root of negative number: " + a);
        }

        double res = Math.sqrt(a);
        context.pushStack(res);
        logger.info("SQRT: " + a + " = " + res);
    }

}
