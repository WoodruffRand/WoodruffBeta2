import java.util.HashMap;

public abstract class BiOpp extends Opp{
	
	public BiOpp(Equation a, Equation b) {
		lh = a; 
		rh = b;
	}


	public abstract double eval(HashMap<String, Double> context) ;
	

	
	/*Ivars*/
	protected Equation lh=null, rh = null;
	
	

}