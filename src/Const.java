import java.util.HashMap;

public class Const extends Equation{
	private double value;
	
	public Const(Double v) {
		value = v;
	}
	
	public Equation copy() {
		return new Const(this.value);
	}
	
	
	public double eval(HashMap<String, Double> context) {
		// TODO Auto-generated method stub
		return value;
	}

	protected Equation recurDerivativeOf(String var) {
		return new Const(0.0);
	}
	
	protected Equation recurSimplify() {
		if(getV()==0.0) return new Const(0.0);
		return this;
	}
	
	public void minus(double v) {
		value -= v;
	}
	
	public void plus(double v) {
		value += v;
	}
	
	public void mult(double v) {
		value *= v;
	}
	
	public void mult(Const const2) {
		this.mult(const2.getV());
	}
	
	public void div(double v) {
		value /= v;
	}
	
	public double getV() {
		return value;
	}
	public String toString() {
		int temp = (int)value*100;
		double rtn = (double)temp/100;
		return ""+rtn;
	}




}
