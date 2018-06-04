import java.util.HashMap;

public class Div extends BiOpp{
	public Div(Equation a, Equation b) {
		super(a,b);//  a/b
	}


	public double eval(HashMap<String, Double> context) {
		// TODO Auto-generated method stub
		return lh.eval(context)/rh.eval(context);
	}
	
	public String toString() {
		String leftString =lh.toString();
		String rightString = rh.toString();
		
		if(!(lh instanceof Const || lh instanceof Var)) {
			leftString= addParentheses(leftString);
		}
		
		if(!(rh instanceof Const || rh instanceof Var)) {
			rightString= addParentheses(rightString);
		} 
		
		return leftString +"/"+rightString;
	}


	protected Equation recurSimplify() {
		if(this.rh == null || isZero(rh)) throw new Error("It apears you are trying to divide by zero");
		if(this.lh == null || isZero(lh)) return new Const(0.0);
		if(this.lh != null) lh = lh.recurSimplify();
		if(this.rh != null)rh = rh.recurSimplify();
		return simfliyDiv();
	}

	public Equation copy() {
		return new Div(this.lh.copy(), this.rh.copy());
	}

	
	protected Equation recurDerivativeOf(String var) {
		Equation g = this.lh;
		Equation h = this.rh;
		
		Equation numerator = new Minus(new Mult(g.derivativeOf(var),h.copy()) ,new Mult(g.copy(), h.derivativeOf(var)));
		Equation denominator = new Pow(h.copy(),new Const(2.0));
		numerator =numerator.simplify();
		denominator =denominator.simplify();
		if(denominator == null) throw new Error("quotent rule went into the rubarb");
		if(numerator==null) return new Const(0.0);
		return new Div(numerator, denominator);
	}
	
	private Equation simfliyDiv() {
		if(this.rh == null || isZero(rh)) throw new Error("It apears you are trying to divide by zero");
		if(this.lh == null || isZero(lh)) return new Const(0.0);
		
		if((lh instanceof Const) && (rh instanceof Const) ) {
			return new Const ( this.eval(null) );
		}
		return this;
	}
}
