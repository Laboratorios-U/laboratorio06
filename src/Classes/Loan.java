package Classes;

import java.time.LocalDate;

public class Loan {
	public static final int ACTIVE = 0;
	public static final int RETURNED = 1;
	public static final int EXPIRED = 2;
	private static final int DEFAULT_LOAN_DAYS = 14;
	private static final int MAX_LOAN_EXTENSION = 30;

	private String id;
	private User user;
	private Book book;
	private LocalDate loanDate;
	private LocalDate expectredReturnDate;
	private LocalDate realReturnDate;
	private int status;

	public Loan(String id, User user, Book book) {
		if(user == null || book == null) {
			throw new IllegalArgumentException("Es necesario un usuario y un libro para registrar un prestamo.");
		}
		this.id = sanitizeId(id);
		this.user = user;
		this.book = book;
		this.loanDate = LocalDate.now();
		this.expectredReturnDate = loanDate.plusDays(DEFAULT_LOAN_DAYS);
		this.realReturnDate = null;
		this.status = ACTIVE;
	}

	private String sanitizeId(String id) {
		if(id == null) {
			return "P0000";
		}
		String cleanId = id.replaceAll("[^A-Za-z0-9_-]", "").trim();
		if(cleanId.isEmpty()) {
			return "P0000";
		}
		return cleanId.length() > 32? cleanId.substring(0, 32): cleanId;
	}

	public String getId() {
		return id;
	}

	public User getUser() {
		return user != null? new User(user): null;
	}

	public Book getBook() {
		return book != null? new Book(book): null;
	}

	public LocalDate getLoanDate() {
		return loanDate;
	}

	public LocalDate getExpectredReturnDate() {
		return expectredReturnDate;
	}

	public LocalDate getRealReturnDate() {
		return realReturnDate;
	}

	public int getStatus() {
		return status;
	}

	public boolean registerLoan() {
		if(user == null || book == null) {
			return false;
		}
		if(status != ACTIVE) {
			return false;
		}
		if(book.isLoaned() || user.getLoanedBooks() != null) {
			return false;
		}
		if(user.askForLoan(book)) {
			status = ACTIVE;
			return true;
		}
		return false;
	}

	public boolean processReturn() {
		if(user == null) {
			return false;
		}
		if(status == ACTIVE || status == EXPIRED) {
			realReturnDate = LocalDate.now();
			if(user.returnBook()) {
				status = RETURNED;
				return true;
			}
		}
		return false;
	}

	public void verifyStatus() {
		if(status == ACTIVE && expectredReturnDate != null && LocalDate.now().isAfter(expectredReturnDate)) {
			status = EXPIRED;
		}
	}

	public boolean extendLoan(int days) {
		if(days <= 0 || days > MAX_LOAN_EXTENSION) {
			return false;
		}
		if(expectredReturnDate == null) {
			return false;
		}
		if(status == ACTIVE && !LocalDate.now().isAfter(expectredReturnDate)) {
			expectredReturnDate = expectredReturnDate.plusDays(days);
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		String statusString = switch(status) {
			case ACTIVE -> "ACTIVO";
			case RETURNED -> "DEVUELTO";
			case EXPIRED -> "VENCIDO";
			default -> "DESCONOCIDO";
		};

		String userName = (user != null)? user.getName(): "N/A";
		String titleName = (book != null)? book.getTitle(): "N/A";
		return "Prestamo [ID="+id+", Usuario="+userName+", Libro="+titleName+", Fecha del Prestamo="+loanDate+", Fecha de Devolución Esperada="+expectredReturnDate+", Fecha de Devolución Real="+(realReturnDate != null? realReturnDate: "No devuelto")+", Estado="+statusString+"]";
	}
}
