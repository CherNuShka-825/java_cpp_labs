package command.commands;

import command.Command;
import command.Context;
import exceptions.CommandException;

import java.util.logging.Logger;

public class MulCommand implements Command {

    private static final Logger logger = Logger.getLogger(MulCommand.class.getName());

    @Override
    public void execute(Context context, String[] args) throws CommandException {
        if (args.length != 0) {
            logger.warning("* expected 0 args, got " + args.length);
            throw new CommandException("* expected 0 args, got " + args.length);
        }

        double a = context.popStack();
        double b = context.popStack();
        double res = b * a;
        context.pushStack(res);
        logger.info("*: " + b + " * " + a + " = " + res);
    }

}
