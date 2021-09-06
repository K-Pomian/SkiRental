package exceptions;

public class NoEquipmentException extends RuntimeException {
	
	private static final long serialVersionUID = -2815199201684624201L;
	
	public NoEquipmentException(String message) {
		System.out.println(message);
	}

}
