import java.util.HashMap;

public class Cos extends UniOpp{

	public Cos(Equation a) {
		super(a);
		
	}

	
	public double eval(HashMap<String, Double> context) {
		return Math.cos(inter.eval(context));
	}


	protected Equation recurSimplify() {
		inter = inter.simplify();
		if(inter instanceof Const) return new Const(this.eval(null));
		return this;
	}

	
	public Equation copy() {
		return new Cos(inter.copy());
	}

	@Override
	protected Equation recurDerivativeOf(String var) {
		if(inter instanceof Const) return new Const(0.0);
		Equation deriveInter = inter.copy().recurDerivativeOf(var);
		return new Mult(deriveInter, new Mult(new Const(-1.0),new Sin(inter)));
	}
	
	
	
	public String toString() {
		return "cos("+inter.toString()+")";
	}

}
