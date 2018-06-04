import java.util.HashMap;

public class Sin extends UniOpp{

	public Sin(Equation a) {
		super(a);
		
	}

	
	public double eval(HashMap<String, Double> context) {
		return Math.sin(inter.eval(context));
	}

	
	public String toString() {
		return "sin("+inter.toString()+")";
	}

	
	protected Equation recurSimplify() {
		inter = inter.simplify();
		if(inter instanceof Const) return new Const(this.eval(null));
		return this;
	}

	
	public Equation copy() {
		return new Sin(inter.copy());
	}

	
	protected Equation recurDerivativeOf(String var) {
		if(inter instanceof Const) return new Const(0.0);
		Equation deriveInter = inter.copy().recurDerivativeOf(var);
		return new Mult(deriveInter, new Cos(inter));
	}

}
