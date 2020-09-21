package xyz.tenohira.magazinemanager.exception;

public class MagazineNotExistException extends Exception {

	public MagazineNotExistException() {
		super();
	}
	
	public MagazineNotExistException(String message) {
		super(message);
	}
	
	public MagazineNotExistException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public MagazineNotExistException(Throwable cause) {
		super(cause);
	}
}
