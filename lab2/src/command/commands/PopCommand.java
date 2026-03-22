package command.commands;

import command.Command;
import exceptions.CommandException;
import command.Context;

public class PopCommand implements Command {

    @Override
    public void execute(Context context, String[] args) throws CommandException {
        if (args.length != 0) {
            throw new CommandException("POP expected 0 args, got " + args.length);
        }

        context.pop();
    }

}
