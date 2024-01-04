package business;

import dataaccess.User;

import java.util.List;

public interface ControllerInterface {
    public List<String> allBookIds();

    public List<String> allMemberIds();

    public User getUser(String userId);

    public Book getBookById(String isbn);

    public LibraryMember searchMember(String memberId);

    public void updateBook(Book book) throws LibrarySystemException;

    public void login(String id, String password) throws LibrarySystemException;

    public List<String[]> getMemberCheckoutEntries(String memberId) throws LibrarySystemException;

    public void checkoutBook(String memberId, String isbn) throws LibrarySystemException;

    public void addBook(String isbn, String title, int maxCheckoutLength, List<Author> authors) throws LibrarySystemException;

    public void addMember(String id, String firstName, String lastName, String cell,
                          String street, String city, String state, String zip)
            throws LibrarySystemException;
}
