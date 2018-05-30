import java.util.HashMap;

public class Mult extends BiOpp{
	
	public Mult(Equation a, Equation b) {
		//puts Constants on lh side of tree to aid in simplification
		super(new ProperPair(a,b).gA(),new ProperPair(a,b).gB());
	}

	
	public double eval(HashMap<String, Double> context) {
		// TODO Auto-generated method stub
		return lh.eval(context)*rh.eval(context);
	}
	
	public Equation copy() {
		return new Mult(this.lh.copy(), this.rh.copy());
		
	}
	
	protected Equation recurSimplify() {
		if(lh == null || rh == null) return new Const(0.0);
		if(isZero(lh) || isZero(rh) ) return new Const(0.0);
		
		this.lh =this.lh.recurSimplify();
		if(lh == null || isZero(lh) ) return new Const(0.0);
		this.rh =this.rh.recurSimplify();
		if(this.rh == null || isZero(rh)) return new Const(0.0);
		
		
		
		if(this.rh instanceof Mult) {
			if(this.hasConst() ) {
				this.rh = removeRedundantCosnts(this.rh,(Const)this.lh);
			} else {//creating temp node to accumulate possible consts 
				Const temp = new Const(1.0);
				this.rh = removeRedundantCosnts(this.rh,temp);
				if(temp.getV()!= 1.0) {
					return new Mult(temp,this);
				}
			}
			
		} else if(this.lh instanceof Const && this.rh instanceof Const) {
			return new Const(  ((Const)this.lh).getV()*((Const)this.rh).getV()    );
		}

	
		return this;
	}
	
	protected Equation recurDerivativeOf(String var) {
		Equation left = this.lh.copy(); //TODO think about replacing with more robust copy mechanism
		Equation right = this.rh.copy();
		Equation leftPrime = this.lh.recurDerivativeOf(var);
		Equation rightPrime = this.rh.recurDerivativeOf(var);
		if(leftPrime== null && rightPrime == null) return new Const(0.0);
		Mult ml = new Mult(leftPrime,right);
		Mult mr = new Mult(left, rightPrime);
		if(leftPrime== null) return mr;
		if(rightPrime== null) return ml;
		Plus newPlus = new Plus(ml, mr);
		return newPlus.recurSimplify();
		
	}
	
	//trims out other constant terms from multliplcation clusters
	//EG 3.0*4.0*6.0*x reduces to 72.0*x
	private Equation removeRedundantCosnts(Equation n, Const c) {
		if(! (n instanceof Mult)) return n;//only return if pluss
		//if not mult do simple stuff
		//if mult do your mult stuff
		Mult m = (Mult)n;
		m.rh = removeRedundantCosnts(m.rh,c);
		if(m.lh instanceof Const) {
			c.mult((Const)m.lh);
			return m.rh;
		}else {
			m.lh = removeRedundantCosnts(m.lh,c);
			return m;
		}
	}
	
	
	public boolean hasConst() {
		if(this.lh instanceof Const) {
			return true;
		}
		//if(this.lh instanceof Mult) {
		//	return ((Mult)this.lh).hasConst();
		//}
		return false;
	}
	
	public String toString() {
		return lh.toString() +"*"+rh.toString();
	}



	
	
	
}



