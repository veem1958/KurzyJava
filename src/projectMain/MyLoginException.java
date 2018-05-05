package projectMain;

/**
 * Trieda vlastnej výnimky, ktorá sa využije pri kontrole zadaného login a hesla.
 * @author Denisa Mensatorisová
 */
public class MyLoginException extends Throwable {

	public MyLoginException() {
		super();
	}

	public MyLoginException(String s) {
		super(s);
	}
	

}
