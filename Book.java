public class Book {
	
	private int isbn;
	private String title;
	private int cost;
	private boolean isReserved;
	private int edition;
	private String publisher;
	private String publisherLocation;
	private int copyrightYear;
	private int shelfNum;
	private String subjectName;

	public Book(int isbn, String title, int cost, boolean isReserved, int edition, String publisher, String publisherLocation, int copyrightYear, int shelfNum, String subjectName) {
		this.setIsbn(isbn);
		this.setTitle(title);
		this.cost = cost;
		this.isReserved = isReserved;
		this.edition = edition;
		this.publisher = publisher;
		this.publisherLocation = publisherLocation;
		this.copyrightYear = copyrightYear;
		this.shelfNum = shelfNum;
		this.setSubjectName(subjectName);
	}

	public String toString() {
		return "Isbn: " + isbn + " Title: " + title + " Edition: " + edition;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public int getIsbn() {
		return isbn;
	}

	public void setIsbn(int isbn) {
		this.isbn = isbn;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public boolean isReserved() {
		return isReserved;
	}

	public void setReserved(boolean isReserved) {
		this.isReserved = isReserved;
	}

	public int getEdition() {
		return edition;
	}

	public void setEdition(int edition) {
		this.edition = edition;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getPublisherLocation() {
		return publisherLocation;
	}

	public void setPublisherLocation(String publisherLocation) {
		this.publisherLocation = publisherLocation;
	}

	public int getCopyrightYear() {
		return copyrightYear;
	}

	public void setCopyrightYear(int copyrightYear) {
		this.copyrightYear = copyrightYear;
	}

	public int getShelfNum() {
		return shelfNum;
	}

	public void setShelfNum(int shelfNum) {
		this.shelfNum = shelfNum;
	}

}