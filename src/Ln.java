import java.util.HashMap;

public class Ln extends UniOpp{

	public Ln(Equation a) {
		super(a);//ln(a)
	}
	
	@Override
	public double eval(HashMap<String, Double> context) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Equation recurSimplify() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Equation copy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Equation recurDerivativeOf(String var) {
		// TODO Auto-generated method stub
		return null;
	}

}
