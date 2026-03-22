package command;

import exceptions.CommandException;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public class Context {

    private final Deque<Double> stack = new ArrayDeque<>();
    private final Map<String, Double> variables = new HashMap<>();

    //stack
    public void push(double value) {
        stack.push(value);
    }

    public double pop() throws CommandException {
        if (stack.isEmpty()) {
            throw new CommandException("Stack is empty");
        }
        return stack.pop();
    }

    public double peek() throws CommandException {
        if (stack.isEmpty()) {
            throw new CommandException("Stack is empty");
        }

        return stack.peek();
    }

    //variables
    public void setVariables(String name, double value) {
        variables.put(name, value);
    }

    public double getVariable(String name) throws CommandException{
        Double value = variables.get(name);
        if (value == null) {
            throw new CommandException("Unknown command: " + name);
        }
        return value;
    }

}
