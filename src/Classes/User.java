package Classes;

public class User extends Person {
	private Book loanedBook;

	public User(String name, String id) {
		super(name, id);
		this.loanedBook = null;
	}

	public User(User user) {
		super(user != null? user.getName(): "Sin nombre", user != null? user.getId(): "Sin ID");
		this.loanedBook = null;
		if(user != null) {
			Book copyLoan = user.getLoanedBooks();
			this.loanedBook = (copyLoan != null)? new Book(copyLoan): null;
			setEmail(user.getEmail());
			setPhone(user.getPhone());
		}
	}

	public boolean askForLoan(Book book) {
		if(book == null) {
			return false;
		}
		if(loanedBook != null) {
			return false;
		}
		if(!book.isLoaned()) {
			if(book.loanBook()) {
				loanedBook = book;
				return true;
			}
		}
		return false;
	}

	public boolean returnBook() {
		if(loanedBook != null) {
			loanedBook.returnBook();
			loanedBook = null;
			return true;
		}
		return false;
	}

	public Book getLoanedBooks() {
		if(loanedBook != null) {
			return new Book(loanedBook);
		}
		return null;
	}

	@Override
	public String getType() {
		return "Usuario";
	}

	@Override
	public String toString() {
		return "ID: "+getId()+", Nombre: "+getName()+". "+(this.loanedBook != null? "Tiene en prestamo activo "+this.loanedBook+".": "No tiene un prestamo activo.");
	}
}
