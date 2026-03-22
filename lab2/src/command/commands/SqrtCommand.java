package command.commands;

import command.Command;
import command.Context;
import exceptions.CommandException;

public class SqrtCommand implements Command {

    @Override
    public void execute(Context context, String[] args) throws CommandException {
        if (args.length != 0) {
            throw new CommandException("SQRT expected 0 args, got " + args.length);
        }

        double a = context.pop();

        if (a < 0) {
            throw new CommandException("SQRT square root of negative number: " + a);
        }

        context.push(Math.sqrt(a));
    }

}
