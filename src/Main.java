import Classes.Book;
import Classes.Employee;
import Classes.Library;
import Classes.User;

void main() {
	Library library = new Library("Biblioteca Central", "Av. Principal #123");
	library.setLibrarian("Jose Iturbide", "EMP001", 1000.00, "Bibliotecario", Employee.MORNING_SHIFT);

	Book book1 = new Book("El Principito", "Antoine de Saint-Exupery", "978-0156012195", 96);
	Book book2 = new Book("Don Quijote", "Miguel de Cervantes", "978-8424922498", 863);
	User user1 = new User("Maria Lopez", "U001");
	User user2 = new User("Carlos Ruiz", "U002");

	IO.println("=== Iniciando proceso de prestamo ===");
	library.setBook(book1);
	library.setUser(user1);

	if(library.loanBook()) {
		IO.println("Prestamo realizado con éxito:");
		IO.println("Libro: "+book1.getTitle());
		IO.println("Usuario: "+user1.getName());
	} else {
		IO.println("No se pudo realizar el prestamo.");
	}
	IO.println("\n=== Intentando prestar libro no disponible ===");
	library.setBook(book1);
	library.setUser(user2);

	if(!library.loanBook()) {
		IO.println("Prestamo denegado: Libro no disponible.");
	}

	IO.println("\n=== Proceso de devolucion ===");
	library.setUser(user1);

	if(library.returnBook()) {
		IO.println("Libro devuelto con éxito:");
		IO.println("Libro: "+book1.getTitle());
		IO.println("Estado: Disponible");
	} else {
		IO.println("No se pudo devolver el libro.");
	}
	IO.println("\n=== Estado de la Biblioteca ===");
	IO.println(library);
}
