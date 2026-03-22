package command.commands;

import command.Command;
import command.Context;
import exceptions.CommandException;

public class PushCommand implements Command {

    @Override
    public void execute(Context context, String[] args) throws CommandException {
        if (args.length != 1) {
            throw new CommandException("PUSH expected 1 args, got " + args.length);
        }

        double value;
        try {
            value = Double.parseDouble(args[0]);
        } catch (NumberFormatException e) {
            value = context.getVariable(args[0]);
        }

        context.push(value);
    }

}
