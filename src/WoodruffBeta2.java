import java.util.HashMap;

public class WoodruffBeta2 {
	
	public static void main(String args[]) {

		WoodruffBetaTester.main();
		
		Equation myEQ = EquationBuilder.build("(4+x)^2");
		System.out.println(myEQ.derivativeOf("x"));
		HashMap<String,Double> c = new HashMap<String, Double>();
		c.put("x", 1.0);
		System.out.println(myEQ.derivativeOf("x").eval(c));
	}

}
