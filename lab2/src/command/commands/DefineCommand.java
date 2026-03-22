package command.commands;

import command.Command;
import command.Context;
import exceptions.CommandException;

public class DefineCommand implements Command {

    @Override
    public void execute(Context context, String[] args) throws CommandException {
        if (args.length != 2) {
            throw new CommandException("DEFINE expected 2 args, got " + args.length);
        }

        String name = args[0];

        double value;
        try {
            value = Double.parseDouble(args[1]);
        } catch (NumberFormatException e) {
            throw new CommandException("DEFINE value must be a number: " + args[1], e);
        }

        context.setVariables(name, value);
    }

}
