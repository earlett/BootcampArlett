package org.example;

public class Book {
    private int id;
    private String isbn;
    private String title;
    private boolean isCheckedOut;
    private String checkedOutTo;
    private String shortSummary;
    private String genre;

    public Book(int id, String isbn, String title, boolean isCheckedOut, String checkedOutTo, String shortSummary, String genre) {
        this.id = id;
        this.isbn = isbn;
        this.title = title;
        this.isCheckedOut = true;
        this.checkedOutTo = checkedOutTo;
        this.shortSummary = shortSummary;
        this.genre = genre;
        

    }
    public Book(int id, String isbn, String title, boolean isCheckedOut, String shortSummary, String genre){
        this.id = id;
        this.isbn = isbn;
        this.title = title;
        this.isCheckedOut = false;
        this.shortSummary = shortSummary;
        this.genre = genre;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isCheckedOut() {
        return isCheckedOut;
    }

    public void setCheckedOut(boolean checkedOut) {
        isCheckedOut = checkedOut;
    }

    public String getCheckedOutTo() {
        return checkedOutTo;
    }

    public void setCheckedOutTo(String checkedOuyTo) {
        this.checkedOutTo = checkedOutTo;
    }

    public String getSummary() {
        return shortSummary;
    }

    public void setSummary(String summary) {
        this.shortSummary = summary;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", isCheckedOut=" + isCheckedOut +
                ", checkedOutTo='" + checkedOutTo + '\'' +
                ", shortSummary='" + shortSummary + '\'' +
                '}';
    }
}
