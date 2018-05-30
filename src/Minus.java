import java.util.HashMap;

public class Minus extends BiOpp{

	
	public Minus(Equation a, Equation b) {
		super(a,b);
	}
	
	public Equation copy() {
		return new Minus(this.lh.copy(), this.rh.copy());
	}

	public double eval(HashMap<String, Double> context) {
		// TODO Auto-generated method stub
		return lh.eval(context)-rh.eval(context);
	}
	
	protected Equation recurDerivativeOf(String var) {
		lh = lh.recurDerivativeOf(var);
		rh = rh.recurDerivativeOf(var);
		return minusSimplify();
	}
	
	protected Equation recurSimplify() {
		if(this.lh != null) this.lh = this.lh.recurSimplify();
		if(this.rh != null) this.rh = this.rh.recurSimplify();
		return minusSimplify();
	}
	
	
	private Equation minusSimplify() {
		if(lh == null && rh == null) return new Const(0.0);
		if(rh == null || isZero(rh) ) return lh;
		if(lh == null || isZero(lh) ) {
			if(rh instanceof Const) return new Const(-1*  ((Const)rh).getV());
			return new Mult(new Const(-1.0), rh);
		}

		if( (lh instanceof Const) &&(rh instanceof Const)) {
			
			return new Const(((Const)lh).getV() -((Const)rh).getV());
		}
		
		return this;
	}
	public String toString() {
		return lh.toString() +"-"+rh.toString();
	}
}
