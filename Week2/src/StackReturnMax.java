import edu.princeton.cs.algs4.Stack;

public class StackReturnMax {
    Stack<Double> stack;
    Stack<Double> max;

    public StackReturnMax(){
        stack = new Stack();
        max = new Stack();
    }

    public void push(double d) {
        stack.push(d);
        if(max.peek() < d)
            max.push(d);
    }

    public double pop() {
        if(stack.peek() == max.peek())
            max.pop();
        return stack.pop();
    }

    public int size() {
        return stack.size();
    }

    public double maximum() {
        return max.peek();
    }

}
