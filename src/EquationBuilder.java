
public class EquationBuilder {
	private static final String[][] Operands = {{"+","-"},{"*","/"},{"^","log"}};
	
	//serves as wrapper function to private constructor
	//adds error checking to string which will not need to be iteraively called
	public static Equation build(String s) {
		//TODO error checking
		s= s.replaceAll("\\s+","");
		return recBuild(s);
		
	}
	
	private static Equation recBuild(String s) {
		if(s ==null) throw new Error("Null String encounted");
		if(s.length() == 0) return null;
		if(isNumeric(s)) return new Const(Double.parseDouble(s));
		if(isVar(s)) return new Var(s);
		IndexPair ind= getOppIndexs(s);
		if(ind.getL() ==-1 && s.charAt(0)=='(') return recBuild(s.substring(1, s.length()-1));//pull off parenthesis 
		if(ind.getL() ==-1) throw new Error("unidentified opperant: "+s);
		String lh = s.substring(0, ind.getL());
		String oppStr =s.substring(ind.getL(),ind.getR()); 
		String rh = s.substring(ind.getR(),s.length());
		
		if(oppStr.equals("+")) {
			return new Plus(recBuild(lh),recBuild(rh));
		}else if(oppStr.equals("-")) {
			return new Minus(recBuild(lh),recBuild(rh));
		} else if(oppStr.equals("*")) {
			return new Mult(recBuild(lh),recBuild(rh));
		} else if(oppStr.equals("/")) {
			return new Div(recBuild(lh),recBuild(rh));
		} else if(oppStr.equals("^")) {
			return new Pow(recBuild(lh),recBuild(rh));
		}
		
		throw new Error("unable to evalautate opperant: "+oppStr);
	}

	
	private static boolean isNumeric(String s) {
		try {
			Double.parseDouble(s);
		}catch (NumberFormatException nfe){
			return false;
		}
		return true;
	}
	//currenly define vars as only aplha, could be considered for revision
	private static boolean isVar(String str) {
	   	for(int i = 0; i < str.length(); i++) {
	    	if( !Character.isLetter( str.charAt(i) ) ) {
	    			return false;
	    	}	
	   	}
	    return true;	
	}
	
	private static IndexPair getOppIndexs(String s) {
		EquationBuilder eq = new EquationBuilder();
		int[] parenDepth = getParenDepth(s);
		for(int i = 0; i <Operands.length; i++) {
			for(int j = 0; j<Operands[i].length; j++) {
				int lh = s.length()-1;
				while(true) {
					lh = s.lastIndexOf(Operands[i][j],--lh);//please note preincriment
					if( lh ==-1) break;
					if(parenDepth[lh]==0) {
						IndexPair p =eq.new IndexPair(lh,lh+Operands[i][j].length());
						return p;
					}
				}
			}
			
		}
		
		IndexPair p =eq.new IndexPair(-1,-1);//no op found
		return p;
	}
	
	private static int[] getParenDepth(String s) {
		int[] depths = new int[s.length()];
		int currDepth =0;
		for(int i = 0; i< s.length(); i++) {
			if(s.charAt(i)=='(') currDepth --;
			if(s.charAt(i)==')') currDepth ++;
			depths[i]= currDepth;
		}
		return depths;
	}
	
	//private helper class
	public class IndexPair{
		private int l;
		private int r;
		
		public IndexPair(int l, int r) {
			this.l = l;
			this.r = r;
		}
		
		public int getL() {
			return l;
		}
		
		public int getR() {
			return r;
		}
	}
	
	
	
}
