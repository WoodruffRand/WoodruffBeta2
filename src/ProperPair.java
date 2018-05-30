public class ProperPair {
	private Equation A, B;
	
	public ProperPair(Equation a, Equation b) {
		if(a instanceof Const) {
			A = a;
			B = b;
		}else if(b instanceof Const) {
			A = b;
			B = a;
		//} //else if(a instanceof Mult && ((Mult)a).hasConst() ){//roate const up, and rebalnce mult
			//Const temp = (Const)((Mult)a).lh;
			//A = temp;
			//B = ((Mult)a).lh=b;		
		} else if(b instanceof Mult && ((Mult)b).hasConst() ){
			A = b;
			B = a;		
		}else{
			A=a;
			B=b;
		}
			
	}
	
	public Equation gA() {
		return A;
	}
	
	public Equation gB() {
		return B;
	}

}
