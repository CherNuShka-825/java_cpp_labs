package command.commands;

import command.Command;
import command.Context;
import exceptions.CommandException;

public class DivCommand implements Command {

    @Override
    public void execute(Context context, String[] args) throws CommandException {
        if (args.length != 0) {
            throw new CommandException("/ expected 0 args, got " + args.length);
        }

        if (context.peek() == 0) {
            throw new CommandException("/ division by zero");
        }

        double a = context.pop();
        double b = context.pop();

        context.push(b / a);
    }

}
