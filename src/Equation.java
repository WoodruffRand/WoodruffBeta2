import java.util.HashMap;

public abstract class Equation {
	
		
	public abstract  double eval(HashMap<String, Double> context);
	
	public abstract String toString();

	public final Equation derivativeOf(String var) {
		Equation temp = this.copy().recurDerivativeOf(var);
		//if(temp ==null) return new Const(0.0);
		return temp.simplify();
	}
	
	public final Equation simplify() {
		return this.recurSimplify();
		
		//Equation temp = this.recurSimplify();
		//if(temp ==null) return new Const(0.0);
		//return temp;
		
	}
	
	protected abstract Equation recurSimplify();
	
	public abstract Equation copy();

	protected abstract Equation recurDerivativeOf(String var);
	
	protected boolean isZero(Equation e) {
		if(!(e instanceof Const)) return false;
		return (((Const)e).getV()==0.0);
		
	}
	
}
