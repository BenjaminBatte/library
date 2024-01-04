package dataaccess;

import business.Book;
import business.LibraryMember;

import java.util.HashMap;

public interface DataAccess { 
	public HashMap<String,Book> readBooksMap();
	public HashMap<String,User> readUserMap();
	public HashMap<String, LibraryMember> readMemberMap();
	public void saveMember(LibraryMember member);
	public Book searchBook(String isbn);
	public void saveNewBook(Book book);
	public void updateBook(Book book);
	public LibraryMember searchMember(String memberId);
	public void updateMember(LibraryMember member);
	public User searchUser(String userId);
}
