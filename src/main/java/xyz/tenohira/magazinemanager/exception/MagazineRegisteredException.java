package xyz.tenohira.magazinemanager.exception;

public class MagazineRegisteredException extends Exception {

	public MagazineRegisteredException() {
		super();
	}
	
	public MagazineRegisteredException(String message) {
		super(message);
	}
	
	public MagazineRegisteredException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public MagazineRegisteredException(Throwable cause) {
		super(cause);
	}
}
