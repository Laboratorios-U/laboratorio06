package Classes;

public class Employee extends Person {
	public static final int MORNING_SHIFT = 0;
	public static final int AFTERNOON_SHIFT = 1;
	public static final int MIXED_SHIFT = 2;

	private String employeeNumber;
	private String position;
	private double wage;
	private int turn;
	private Loan loanStatus;
	private static int counterId = 0;

	public Employee(String name, String id, String employeeNumber, String position) {
		super(name, id);
		this.employeeNumber = sanitizeString(employeeNumber, "EMP000");
		this.position = sanitizeString(position, "Sin puesto.");
		this.wage = 0;
		this.turn = MORNING_SHIFT;
		this.loanStatus = null;
	}

	private String sanitizeString(String value, String fallback) {
		if(value == null) {
			return fallback;
		}
		String cleanString = value.replaceAll("[\\r\\n\\t]", " ").trim();
		if(cleanString.isEmpty()) {
			return fallback;
		}
		return cleanString.length() > 80? cleanString.substring(0, 80): cleanString;
	}

	public String getEmployeeNumber() {
		return employeeNumber;
	}

	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = sanitizeString(employeeNumber, this.employeeNumber);
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = sanitizeString(position, this.position);
	}

	public double getWage() {
		return wage;
	}

	public void setWage(double wage) {
		this.wage = (wage > 0 && wage <= 1_000_000_000)? wage: 0;
	}

	public int getTurn() {
		return turn;
	}

	public void setTurn(int turn) {
		if(turn == MORNING_SHIFT || turn == AFTERNOON_SHIFT || turn == MIXED_SHIFT) {
			this.turn = turn;
		}
	}

	public Loan getLoanStatus() {
		return loanStatus;
	}

	@Override
	public String getType() {
		return "Empleado";
	}

	public static String generateId() {
		counterId++;
		return "P"+String.format("%04d", counterId);
	}

	public boolean processLoan(Book book, User user) {
		if(loanStatus != null) {
			return false;
		}
		if(book != null && user != null && !book.isLoaned()) {
			if(user.askForLoan(book)) {
				loanStatus = new Loan(generateId(), user, book);
				return true;
			}
		}
		return false;
	}

	public boolean returnLoan() {
		if(loanStatus != null) {
			loanStatus = null;
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "Empleado [numeroDeEmpleado="+employeeNumber+", puesto="+position+", salario="+wage+", turno="+turn+", estadoDelPrestamo="+loanStatus+", nombre="+getName()+", id="+getId()+"]";
	}
}
