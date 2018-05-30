import java.util.HashMap;

public class Plus extends BiOpp {

	public Plus(Equation a, Equation b) {
		super(a,b);
	}

	public Equation copy() {
		return new Plus(this.lh.copy(),this.rh.copy());
		
	}
	
	public double eval(HashMap<String, Double> context) {
		// TODO Auto-generated method stub
		return lh.eval(context)+rh.eval(context);
	}

	

	protected Equation recurDerivativeOf(String var) {
		if(lh != null) lh = lh.recurDerivativeOf(var);
		if(rh != null) rh = rh.recurDerivativeOf(var);
		return plusSimplify();
	}
	
	protected Equation recurSimplify() {
		if(this.lh != null) this.lh = this.lh.recurSimplify();
		if(this.rh != null) this.rh = this.rh.recurSimplify();
		return plusSimplify();
	}
	
	private Equation plusSimplify() {
		if(lh == null || isZero(lh) ) return rh;
		if(rh == null || isZero(rh) ) return lh;
		
		if( (lh instanceof Const) &&(rh instanceof Const)) {
			
			return new Const(((Const)lh).getV() +((Const)rh).getV());
		}
		
		return this;
	}
	
	public String toString() {
		return lh.toString() +"+"+rh.toString();
	}
}
