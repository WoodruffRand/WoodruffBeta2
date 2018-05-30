import java.util.HashMap;

public class WoodruffBetaTester {
	
	public static void main() {
		testOppClasses();
		testMultBalancing();
		testConst();
		testBuildEquation();
		testCopy();
		testSimplifying();
		testDerivative();
		print("le fin");
		
	}
	
	
	private static void testSimplifying() {
		print("==testing simplifying methods==");
		Const a = new Const(4.0);
		Const b = (Const)a.copy();
		Const z = new Const(0.0);
		
		assert(a.getV() == b.getV());
		b.plus(1.0);
		assert(a.getV()+1 == b.getV());
		
		Equation p = new Plus(null, null);
		//testing null blancing
		p = new Plus(a, null);
		assert(p.simplify()== a);
		p = new Plus(null, b);
		assert(p.simplify()== b);
		//testing Zero simplification
		p = new Plus(a, z);
		p = p.simplify();
		assert(p.simplify()== a);
		p = new Plus(z, b);
		assert(p.simplify()== b);
		p = new Plus(a,b);
		p = p.simplify();
		assert(p instanceof Const);
		assert(((Const)p).getV() ==4.0+5.0);
		
		
		//Minus testing ////////////////////////////////
		p = new Minus(null, null);
		p = p.simplify();
		assert(p instanceof Const);
		assert( ((Const)p).getV()==0.0);
		//testing null blancing
		p = new Minus(a, null);
		assert(p.simplify()== a);
		p = new Minus(null, b);
		assert(p.simplify() instanceof Const);
		//testing Zero simplification
		p = new Minus(a, z);
		assert(p.simplify()== a);
		p = new Minus(z, b);
		assert(p.simplify()instanceof Const);
		p = new Minus(a,b);
		p = p.simplify();
		assert(p instanceof Const);
		assert(((Const)p).getV() ==4.0-5.0);
		
		
		//Mult testing ////////////////////////////////
		p = new Mult(null, null);
		p = p.simplify();
		assert(p instanceof Const);
		assert( ((Const)p).getV()==0.0);
		//testing null blancing
		p = new Mult(a, null);
		p =p.simplify();
		assert(p instanceof Const);
		assert( ((Const)p).getV()==0.0);
		p = new Mult(null, b);
		p = p.simplify();
		assert(p instanceof Const);
		assert( ((Const)p).getV()==0.0);
		//testing Zero simplification
		p = new Mult(a, z);
		p = p.simplify();
		assert(p instanceof Const);
		assert( ((Const)p).getV()==0.0);
		p = new Mult(z, b);
		p = p.simplify();
		assert(p instanceof Const);
		assert( ((Const)p).getV()==0.0);
		p = new Mult(a,b);
		p = p.simplify();
		assert(p instanceof Const);
		assert(((Const)p).getV() ==4.0*5.0);
		
		//testing power simplification
		a.div(a.getV());
		Equation m = new Pow(a.copy(),a.copy());
		
		m = m.simplify();
		assert(m instanceof Const);
		assert( ((Const)m).getV() ==1);
		
		m = new Pow(z.copy(),a.copy());
		m = m.simplify();
		assert(m instanceof Const);
		assert( ((Const)m).getV() ==0);
		
		m = new Pow(b.copy(),a.copy());
		m = m.simplify();
		assert(m instanceof Const);
		assert( ((Const)m).getV() ==b.getV());
		
		m = new Pow(b.copy(),z.copy());
		m = m.simplify();
		assert(m instanceof Const);
		assert( ((Const)m).getV() ==1.0);
		
		m = new Pow(z.copy(),b.copy());
		m = m.simplify();
		assert(m instanceof Const);
		assert( ((Const)m).getV() ==0.0);

		m = new Pow(b.copy(),new Const(2.0));
		m = m.simplify();
		assert(m instanceof Const);
		assert( ((Const)m).getV() ==25.0);
		
		m = new Pow(b.copy(),new Var("x"));
		m = m.simplify();
		assert(m instanceof Pow);
		
		Equation myEQ = EquationBuilder.build("400*((4+6+10)*(-1+45-100)-789)");
		assert(myEQ . simplify().eval(null) == -763600.0);
		myEQ = EquationBuilder.build("400*((4+6+10)*(-1+45-100)-789)+ (3-3)*x - x*(10.1-10.1)-(20-20)/y+(1-10/10) +(x)*(1-2^0)");
		myEQ= myEQ.simplify();
		assert(myEQ.simplify().eval(null) == -763600.0);
		print("==passed simplifying methods testing==");
	}
	
	private static void testMultBalancing() {
		System.out.println("==testibng mult balance==");
		Equation myEq = EquationBuilder.build("3*X*4*x*5*y*6");
		assert(myEq.toString().equals("6.0*5.0*4.0*3.0*X*x*y"));
		myEq =myEq.simplify();
		assert(myEq.toString().equals("360.0*X*x*y"));
		myEq = EquationBuilder.build("X*4");
		assert(myEq.toString().equals("4.0*X"));
		print("==Passed mult tests==");
	}
	
	
	
	
	private static void testConst() {
		System.out.println("==Tesint Const class==");
		Const myConst = new Const(10.0);
		assert(myConst.getV() == 10.0);
		myConst.plus(4);
		assert(myConst.getV() == 14.0);
		myConst.minus(8.0);
		assert(myConst.getV() == 6.0);
		myConst.mult(3.0);
		assert(myConst.getV() == 18.0);
		myConst.div(2.0);
		assert(myConst.getV() == 9.0);
		System.out.println("==Const class passed testing==");
	}
	
	
	private static void testCopy() {
		
		
		
		
	}
	
	private static void testBuildEquation() {
		System.out.println("==Testing build Equation==");
		HashMap<String, Double> c = new HashMap<String,Double>();	
		c.put("x", 10.0);
		Equation myEq = EquationBuilder.build("3-(5-10)");
		//System.out.println(myEq.toString());
		assert(myEq.eval(null)==8.0);
		try {
			myEq = EquationBuilder.build("4*(x!)");
		} catch( Error e) {
			print("caught undeffined opperator");
		}
		
		
		myEq = EquationBuilder.build("3-5-10+13+45-62");
		//System.out.println(myEq.toString());
		assert(myEq.eval(null)==-16.0);
		myEq =myEq.derivativeOf("x");
		assert(myEq.toString().toString().equals("0.0"));
		myEq = EquationBuilder.build("3-4+x");
		assert(myEq.toString().equals("3.0-4.0+x"));
		assert(myEq.eval(c)==9.0);
		myEq =myEq.derivativeOf("x");
		assert(myEq.toString().equals("1.0"));
		assert(myEq.eval(null)==1.0);
		myEq = EquationBuilder.build("3-x^4");
		//System.out.println(myEq.toString());
		assert(myEq.eval(c)==-9997.0);
		myEq =myEq.derivativeOf("x");
		assert(myEq.toString().equals("-4.0*x^(3.0)"));
	
		
		myEq = EquationBuilder.build("3-x^1");
		assert(myEq.toString().equals("3.0-x^(1.0)"));
		assert(myEq.eval(c)==-7.0);
		myEq =myEq.derivativeOf("x");
		assert(myEq.toString().equals("-1.0"));
		

	}
	
	private static void testDerivative() {
		Equation myEQ = EquationBuilder.build("3*x+45*x^15");
		print("Equation:"+myEQ.toString() );
		myEQ = myEQ.derivativeOf("x");
		print("Derivative: "+myEQ.toString());

		
		myEQ = EquationBuilder.build("3*x+2*x^15*x^5");
		print("Equation:"+myEQ.toString() );
		myEQ = myEQ.derivativeOf("x");
		print("Derivative: "+myEQ.toString());
		assert(myEQ.toString().equals("3.0+30.0*x^(14.0)*x^(5.0)+10.0*x^(4.0)*x^(15.0)"));
		
		
		
		myEQ = EquationBuilder.build("3*x-2*x^15*x^5");
		print("Equation:"+myEQ.toString() );
		myEQ = myEQ.derivativeOf("x");
		print("Derivative: "+myEQ.toString());
		myEQ.simplify();
		assert(myEQ.toString().equals("3.0-30.0*x^(14.0)*x^(5.0)+10.0*x^(4.0)*x^(15.0)"));
		
		
		myEQ = EquationBuilder.build("x^9/(x)");
		print("Equation:"+myEQ.toString() );
		myEQ = myEQ.derivativeOf("x");
		assert(myEQ.toString().equals("9.0*x^(8.0)*x-1.0*x^(9.0)/x^(2.0)"));
		print("Derivative: "+myEQ.toString());
		
		HashMap<String, Double> c = new HashMap<String, Double>();
		c.put("x",1.0);
		assert(myEQ.eval(c)==8.0);
		c.put("x",2.0);
		assert(myEQ.eval(c)==1024.0);
	}
	
	
	private static<T> void print(T t) {
		System.out.println(t);
	}
	private static void testOppClasses() {
		Const a = new Const(5.0);
		Const b = new Const(2.0);
		Var x = new Var("X");
		HashMap<String, Double> c = new HashMap<String,Double>();	
		c.put("X", 10.0);
		plusTest(c,x,a,b);
		minusTest(c,x,a,b);
		multTest(c,x,a,b);
		divTest(c,x,a,b);
		expTest(c,x,a,b);
		System.out.println("==Passed atomic Opp tests==");
	}
	
	
	public static void plusTest(HashMap<String,Double> c,Var x, Const a, Const b) {
		Equation p = new Plus(a,b);
		assert(p.eval(c)==7);
		
		p = new Plus(a,x);
		assert(p.eval(c)==15);
	}

	public static void minusTest(HashMap<String,Double> c,Var x, Const a, Const b) {
		Equation p = new Minus(a,b);
		assert(p.eval(c)==3);
		
		p = new Minus(a,x);
		assert(p.eval(c)==-5);
	}

	public static void multTest(HashMap<String,Double> c,Var x, Const a, Const b) {
		Equation p = new Mult(a,b);
		assert(p.eval(c)==10);
		
		p = new Mult(a,x);
		assert(p.eval(c)==50);
	}
	
	public static void divTest(HashMap<String,Double> c,Var x, Const a, Const b) {
		Equation p = new Div(a,b);
		assert(p.eval(c)==2.5);
		p = new Div(b,a);
		assert(p.eval(c)==.4);
		
		p = new Div(a,x);
		assert(p.eval(c)==.5);
		p = new Div(x,a);
		assert(p.eval(c)==2);
	}
	
	public static void expTest(HashMap<String,Double> c,Var x, Const a, Const b) {
		Equation p = new Pow(a,b);
		assert(p.eval(c)==25);
		
		p = new Pow(x,a);
		assert(p.eval(c)==100000);
	}
	
}
