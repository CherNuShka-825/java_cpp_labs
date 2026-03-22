package command.commands;

import command.Command;
import command.Context;
import exceptions.CommandException;

import java.util.logging.Logger;

public class AddCommand implements Command {

    private static final Logger logger = Logger.getLogger(AddCommand.class.getName());

    @Override
    public void execute(Context context, String[] args) throws CommandException {
        if (args.length != 0) {
            logger.warning("+ expected 0 args, got " + args.length);
            throw new CommandException("+ expected 0 args, got " + args.length);
        }

        double a = context.pop();
        double b = context.pop();
        double res = b + a;
        context.push(res);
        logger.info("+: " + b + " + " + a + " = " + res);
    }

}
