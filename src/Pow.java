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
		if(lh instanceof Const && rh instanceof Const) return new Const(0.0);//is itself a constant
		if( rh instanceof Const) return constExpoDerivative(var);
				
		return exponetialDerivaitve(var);//exponse is not const, some for of function
	} 
	
	
	protected Equation recurSimplify() {
		this.lh = this.lh.recurSimplify();
		this.rh = this.rh.recurSimplify();
		return powSimplify();
	}
	
	private Equation powSimplify() {
		if(isZero(lh)) return new Const(0.0); //if base if zero
		if(isZero(rh)) return new Const(1.0);	//if exp is zero
		if(isOne(lh)) return new Const(1.0); //if base is 1
		if(lh instanceof Const && rh instanceof Const) return new Const(this.eval(null)); //if all consts
		if(isOne(rh)) return lh;
		
		return this;
	}
	
	private Equation constExpoDerivative(String var) {
		//TODO figure out what to do with not matching var
		Const cRh =(Const)rh;
		if(cRh.getV()== 1){
			return rh.copy();
		}
		Mult tempM = new Mult(new Const(cRh.getV()),this);
		cRh.minus(1.0);
		Equation innderDeriv = lh.derivativeOf(var);
		if(isZero(innderDeriv) || isOne(innderDeriv)){
			return tempM;
		}else {
			return new Mult(innderDeriv, tempM);
		}
	}
	
	private Equation exponetialDerivaitve(String var){
		//concert to base e, call the derivative 
		// [e^(ln(lh)*rh)]' 
		return new Const(0.0);//Place holder
	
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
		
		return leftString +"^"+rightString;
	}

}
