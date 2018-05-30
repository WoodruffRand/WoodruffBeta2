import java.util.HashMap;

public class Pow extends BiOpp{
	
	public Pow(Equation a, Equation b) {
		super(a,b);//a^b
	}

	public Equation copy() {
		return new Pow(this.lh.copy(), this.rh.copy());
		
	}
	
	public double eval(HashMap<String, Double> context) {
		return Math.pow(lh.eval(context),rh.eval(context));
	}
	
	protected Equation recurDerivativeOf(String var) {
		if( lh instanceof  Var && rh instanceof Const) {
			Const cRh =(Const)rh;
			if(cRh.getV()== 1){
				return rh;
			}
			Mult tempM = new Mult(new Const(cRh.getV()),this);
			cRh.minus(1.0);
			return tempM;
		}
		return new Const(0.0);//TODO just a place holder here
	} 
	
	public String toString() {
		return lh.toString() +"^("+rh.toString()+")";
	}

	
	protected Equation recurSimplify() {
		this.lh = this.lh.recurSimplify();
		this.rh = this.rh.recurSimplify();
		return powSimplify();
	}
	
	private Equation powSimplify() {
		if(isZero(lh)) return new Const(0.0); //if base if zero
		if(isZero(rh)) return new Const(1.0);	//if exp is zero
		if(lh instanceof Const && ((Const)lh).getV()==1.0) return new Const(1.0); //if base is 1
		if(lh instanceof Const && rh instanceof Const) return new Const(this.eval(null)); //if base is 1
		
		return this;
	}

}
