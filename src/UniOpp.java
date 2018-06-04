import java.util.HashMap;

public abstract class UniOpp extends Opp{

	public UniOpp(Equation a) {
		inter = a; 
	}

	//Ivars
	protected Equation inter;// interior 
}
