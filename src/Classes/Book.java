package Classes;

import java.util.Objects;
import java.util.regex.Pattern;

public class Book {
	private static final String DEFAULT_TITLE = "Sin titulo";
	private static final String DEFAULT_AUTHOR = "Autor desconocido";
	private static final String DEFAULT_ISBN = "0000000000000";
	private static final int MAX_TEXT_LENGTH = 120;
	private static final int MAX_ISBN_LENGTH = 20;
	private static final int MAX_PAGE_LENGTH = 100000;

	private static final Pattern ISBN_13_DIGIT = Pattern.compile("\\d{13}");
	private static final Pattern ISBN_LEGACY = Pattern.compile("[A-Za-z0-9-]{3,20}");

	private String title;
	private String author;
	private String isbn;
	private int pagesNumber;
	private boolean loaned;

	public Book() {
		this.title = DEFAULT_TITLE;
		this.author = DEFAULT_AUTHOR;
		this.isbn = DEFAULT_ISBN;
		this.pagesNumber = 0;
		this.loaned = false;
	}

	public Book(String title, String author) {
		this();
		setTitle(title);
		setAuthor(author);
	}

	public Book(String title, String author, String isbn, int pagesNumber) {
		this();
		setTitle(title);
		setAuthor(author);
		setIsbn(isbn);
		setPagesNumber(pagesNumber);
	}

	public Book(Book otherBook) {
		Objects.requireNonNull(otherBook, "No se puede copiar el libro, debido a que esta vacio.");
		this.title = otherBook.title;
		this.author = otherBook.author;
		this.isbn = otherBook.isbn;
		this.pagesNumber = otherBook.pagesNumber;
		this.loaned = false;
	}

	private String sanitizeString(String value, String fallback) {
		if(value == null) {
			return fallback;
		}
		String cleanString = value.replaceAll("[\\p{Cntrl}&&[^\\r\\n\\t]]", "").replace('\r', ' ').replace('\n', ' ').trim();
		if(cleanString.isEmpty()) {
			return fallback;
		}
		if(cleanString.length() > MAX_TEXT_LENGTH) {
			cleanString = cleanString.substring(0, MAX_TEXT_LENGTH);
		}
		return cleanString;
	}

	private String sanitizeIsbn(String value) {
		if(value == null) {
			return DEFAULT_ISBN;
		}
		String cleanIsbn = value.replaceAll("\\s+", "").trim();
		if(cleanIsbn.isEmpty()) {
			return DEFAULT_ISBN;
		}
		String onlyDigits = cleanIsbn.replaceAll("[^0-9]", "");
		if(ISBN_13_DIGIT.matcher(onlyDigits).matches()) {
			return onlyDigits;
		}
		if(ISBN_LEGACY.matcher(cleanIsbn).matches()) {
			return cleanIsbn;
		}
		return DEFAULT_ISBN;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = sanitizeString(title, this.title != null? this.title: DEFAULT_TITLE);
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = sanitizeString(author, this.author != null? this.author: DEFAULT_AUTHOR);
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		String newIsbn = sanitizeIsbn(isbn);
		if(newIsbn.length() <= MAX_ISBN_LENGTH) {
			this.isbn = newIsbn;
		}
	}

	public int getPagesNumber() {
		return pagesNumber;
	}

	public void setPagesNumber(int pagesNumber) {
		if(pagesNumber > 0 && pagesNumber <= MAX_PAGE_LENGTH) {
			this.pagesNumber = pagesNumber;
		}
	}

	public boolean isLoaned() {
		return loaned;
	}

	public void setLoaned(boolean loaned) {
		this.loaned = loaned;
	}

	public boolean loanBook() {
		if(!loaned) {
			loaned = true;
			return true;
		}
		return false;
	}

	public void returnBook() {
		loaned = false;
	}

	public boolean checkAvailability() {
		return !loaned;
	}

	@Override
	public String toString() {
		return "Classes.Libro: "+title+" por "+author+"\nISBN: "+isbn+"\nPaginas: "+pagesNumber+"\nEstado: "+(loaned? "En prestamo.": "Disponible.");
	}
}
