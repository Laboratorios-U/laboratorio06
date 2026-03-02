package Classes;

public class Library {
	private String name;
	private String location;
	private Book book;
	private User user;
	private Employee librarian;

	public Library(String name, String location) {
		this.name = sanitizeString(name, "Sin nombre");
		this.location = sanitizeString(location, "Sin ubicacion");
		this.librarian = null;
		this.book = null;
		this.user = null;
	}

	private String sanitizeString(String value, String fallback) {
		if(value == null) {
			return fallback;
		}
		String cleanString = value.replaceAll("[\\r\\n\\t]", " ").trim();
		if(cleanString.isEmpty()) {
			return fallback;
		}
		return cleanString.length() > 120? cleanString.substring(0, 120): cleanString;
	}

	public String getName() {
		return name;
	}

	public String getLocation() {
		return location;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public Book getBook() {
		return book;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public Employee getLibrarian() {
		return librarian;
	}

	public void setLibrarian(String name, String id, double wage, String position, int turn) {
		this.librarian = new Employee(name, id, id, position);
		librarian.setWage(wage);
		librarian.setTurn(turn);
	}

	public boolean loanBook() {
		if(book == null || user == null || librarian == null) {
			return false;
		}
		if(book.isLoaned()) {
			return false;
		}
		return librarian.processLoan(book, user);
	}

	public boolean returnBook() {
		if(book == null || user == null || librarian == null || !book.isLoaned()) {
			return false;
		}

		Loan loan = librarian.getLoanStatus();
		if(loan == null) {
			return false;
		}

		User userLoan = loan.getUser();
		Book bookLoan = loan.getBook();
		boolean userMatches = userLoan != null && userLoan.getId().equals(user.getId());
		boolean bookMatches = loanedBookMatches(bookLoan, book);
		if(!userMatches || !bookMatches) {
			return false;
		}

		book.returnBook();
		librarian.returnLoan();
		return true;
	}

	private boolean loanedBookMatches(Book bookLoan, Book actualBook) {
		if(bookLoan == null || actualBook == null) {
			return false;
		}
		String loanIsbn = bookLoan.getIsbn();
		String actualIsbn = actualBook.getIsbn();
		if(loanIsbn != null && actualIsbn != null && !loanIsbn.equals("0000000000000") && !actualIsbn.equals("0000000000000")) {
			return loanIsbn.equals(actualIsbn);
		}
		return bookLoan.getTitle().equals(actualBook.getTitle()) && bookLoan.getAuthor().equals(actualBook.getAuthor());
	}

	@Override public String toString() {
		return "Biblioteca "+name+"\n"+"Ubicacion: "+location+"\n"+"Libro actual: "+(book != null? book.getTitle(): "Ninguno")+"\n"+"Usuario actual: "+(user != null? user.getName(): "Ninguno")+"\n"+"Bibliotecario: "+(librarian != null? librarian.getName(): "Sin asignar")+"\n"+"Estado del préstamo: "+(librarian != null && librarian.getLoanStatus() != null? "Prestamo activo.": "Sin prestamos.");
	}
}
