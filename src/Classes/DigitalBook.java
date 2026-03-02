package Classes;

import java.net.URI;
import java.net.URISyntaxException;

public class DigitalBook extends Book {
	private static final String DEFAULT_FORMAT = "PDF";
	private static final double MAX_MB_SIZE = 100_000;

	private String format;
	private double mbSize;
	private String downloadUrl;
	private int availableDownloads;
	private int actualDownloads;

	public DigitalBook(String title, String author, String isbn, int pagesNumber, String format, double mbSize, String downloadUrl) {
		super(title, author, isbn, pagesNumber);
		this.format = sanitizeFormat(format);
		this.mbSize = sanitizeSize(mbSize);
		this.downloadUrl = sanitizeUrl(downloadUrl);
		this.availableDownloads = 3;
		this.actualDownloads = 0;
	}

	public DigitalBook(Book book, String format, double mbSize, String downloadUrl) {
		super(book);
		this.format = sanitizeFormat(format);
		this.mbSize = sanitizeSize(mbSize);
		this.downloadUrl = sanitizeUrl(downloadUrl);
		this.availableDownloads = 3;
		this.actualDownloads = 0;
	}

	private String sanitizeFormat(String format) {
		if(format == null) {
			return DEFAULT_FORMAT;
		}
		String cleanFormat = format.replaceAll("[^A-Za-z0-9]", "").trim().toUpperCase();
		if(cleanFormat.isEmpty()) {
			return DEFAULT_FORMAT;
		}
		return cleanFormat.length() > 10? cleanFormat.substring(0, 10): cleanFormat;
	}

	private double sanitizeSize(double mbSize) {
		if(Double.isNaN(mbSize) || Double.isInfinite(mbSize) || mbSize < 0) {
			return 0;
		}
		return Math.min(mbSize, MAX_MB_SIZE);
	}

	private String sanitizeUrl(String url) {
		if(url == null) {
			return "";
		}
		String cleanUrl = url.replaceAll("[\\r\\n\\t]", "").trim();
		if(cleanUrl.isEmpty()) {
			return "";
		}
		try {
			URI uri = new URI(cleanUrl);
			String scheme = uri.getScheme();
			if(scheme != null && (scheme.equalsIgnoreCase("http") || scheme.equalsIgnoreCase("https"))) {
				return cleanUrl;
			}
		} catch(URISyntaxException e) {
			return "";
		}
		return "";
	}

	public String getFormat() {
		return format;
	}

	public double getMbSize() {
		return mbSize;
	}

	public String getDownloadUrl() {
		return downloadUrl;
	}

	public int getAvailableDownloads() {
		return availableDownloads;
	}

	public int getActualDownloads() {
		return actualDownloads;
	}

	public boolean download() {
		if(downloadUrl == null || downloadUrl.isEmpty()) {
			return false;
		}
		if(actualDownloads < availableDownloads) {
			actualDownloads++;
			return true;
		}
		return false;
	}

	public void resetDownloads() {
		actualDownloads = 0;
	}

	@Override
	public boolean loanBook() {
		if(actualDownloads < availableDownloads) {
			return super.loanBook();
		}
		return false;
	}

	@Override
	public String toString() {
		return super.toString()+"\nFormato: "+format+"\nTamaño: "+mbSize+" MB"+"\nURL de descarga: "+downloadUrl+"\nDescargas realizadas: "+actualDownloads+"/"+availableDownloads+".";
	}
}
