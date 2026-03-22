package command.commands;

import command.Command;
import command.Context;
import exceptions.CommandException;

public class PrintCommand implements Command {

    @Override
    public void execute(Context context, String[] args) throws CommandException {
        if (args.length != 0) {
            throw new CommandException("PRINT expected 0 args, got " + args.length);
        }

        double a = context.peek();
        System.out.println(a);
    }

}
