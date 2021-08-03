
public class IncorrectCustomerFormatException extends Exception {
	@Override
	public String toString(){ 
		return ("Customer name contains % and &") ;
	   }
}
