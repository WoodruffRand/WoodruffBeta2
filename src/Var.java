import java.util.HashMap;

public class Var extends Equation{
	private String name;
	
	
	public Var(String n) {
		name = n;
		
	}
	
	public Equation copy() {
		return new Var(this.name);
	}
	
	public double eval(HashMap<String, Double> context) {
		if(context.containsKey(name)) return context.get(name);
		throw new Error("No context for variable "+ name);
	}

	
	protected Equation recurDerivativeOf(String var) {
		if(this.name.equals(var)) return new Const(1.0);
		return this;//TODO replave wiht new mult dydx
	}

	public String toString() {
		return name;
	}

	protected Equation recurSimplify() {
		return this;
	}

}