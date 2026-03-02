package Classes;

import java.util.regex.Pattern;

public abstract class Person {
	private static final String DEFAULT_EMAIL = "usuario@servidor.com";
	private static final String DEFAULT_PHONE = "0000000000";
	private static final int MAX_TEXT_LENGHT = 120;
	private static final Pattern EMAIL_REGEX = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
	private static final Pattern PHONE_PATTER = Pattern.compile("^\\d{7,15}$");

	private String name;
	private String id;
	private String email;
	private String phone;

	public Person(String name, String id) {
		this.name = sanitizeString(name, "Sin nombre");
		this.id = sanitizeString(id, "SIN_ID");
		this.email = DEFAULT_EMAIL;
		this.phone = DEFAULT_PHONE;
	}

	private String sanitizeString(String value, String fallback) {
		if(value == null) {
			return fallback;
		}
		String cleanString = value.replaceAll("[\\p{Cntrl}&&[^\\r\\n\\t]]", "").replace('\r', ' ').replace('\n', ' ').trim();
		if(cleanString.isEmpty()) {
			return fallback;
		}
		if(cleanString.length() > MAX_TEXT_LENGHT) {
			return cleanString.substring(0, MAX_TEXT_LENGHT);
		}
		return cleanString;
	}

	public String getName() {
		return name;
	}

	public String getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public String getPhone() {
		return phone;
	}

	public void setEmail(String email) {
		String cleanString = sanitizeString(email, this.email);
		if(EMAIL_REGEX.matcher(cleanString).matches()) {
			this.email = cleanString;
		}
	}

	public void setPhone(String phone) {
		if(phone == null) {
			return;
		}
		String onlyDigits = phone.replaceAll("\\D", "");
		if(PHONE_PATTER.matcher(onlyDigits).matches()) {
			this.phone = onlyDigits;
		}
	}

	public abstract String getType();

	@Override
	public String toString() {
		return "Nombre: "+name+" ID: "+id+"\nEmail: "+email+"\nTeléfono: "+phone+"\nTipo: "+getType();
	}
}
