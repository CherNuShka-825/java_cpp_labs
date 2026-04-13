package command.commands;

import command.Command;
import command.Context;
import exceptions.CommandException;

import java.util.logging.Logger;

public class PrintCommand implements Command {

    private static final Logger logger = Logger.getLogger(PrintCommand.class.getName());

    @Override
    public void execute(Context context, String[] args) throws CommandException {
        if (args.length != 0) {
            logger.warning("PRINT expected 0 args, got " + args.length);
            throw new CommandException("PRINT expected 0 args, got " + args.length);
        }

        double a = context.peekStack();
        System.out.println(a);
        logger.info("Print: " + a);
    }

}
