package command;

import exceptions.CommandException;

import java.util.*;
import java.util.logging.Logger;

public class Context {

    private static final Logger logger = Logger.getLogger(Context.class.getName());

    private final Deque<Double> stack = new ArrayDeque<>();
    private final Map<String, Double> variables = new HashMap<>();

    //stack
    public void push(double value) {
        stack.push(value);
        logger.fine("push: " + value);
    }

    public double pop() throws CommandException {
        if (stack.isEmpty()) {
            logger.warning("pop failed: stack is empty");
            throw new CommandException("Stack is empty");
        }
        double val = stack.pop();
        logger.fine("pop: " + val);
        return val;
    }

    public double peek() throws CommandException {
        if (stack.isEmpty()) {
            logger.warning("peek failed: stack is empty");
            throw new CommandException("Stack is empty");
        }

        double val = stack.peek();
        logger.fine("peek: " + val);
        return val;
    }

    //variables
    public void setVariables(String name, double value) {
        variables.put(name, value);
        logger.fine("set variables: " + name + " = " + value);
    }

    public double getVariable(String name) throws CommandException{
        Double value = variables.get(name);

        if (value == null) {
            logger.warning("get variable failed: Unknown variable" + name);
            throw new CommandException("Unknown command: " + name);
        }

        logger.fine("get variable: " + name + " -> " + value);
        return value;
    }

}
