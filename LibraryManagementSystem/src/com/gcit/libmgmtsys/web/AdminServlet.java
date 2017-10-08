package com.gcit.libmgmtsys.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gcit.libmgmtsys.entity.*;
import com.gcit.libmgmtsys.service.AdminService;

/**
 * Servlet implementation class AdminServlet
 */
@WebServlet({"/addAuthor",    "/updateAuthor",    "/deleteAuthor",    "/addGenre", 		   "/updateGenre", 		   "/deleteGenre",
			 "/addPublisher", "/updatePublisher", "/deletePublisher", "/addLibraryBranch", "/updateLibraryBranch", "/deleteLibraryBranch",
			 "/addBorrowers", "/updateBorrowers", "/deleteBorrowers", "/overrideBookLoans", 
			 "/pageAuthors", "/pageGenres", "/pagePublishers", "/pageBooks", "/addBook", "/deleteBook", "/updateBook",
			 "/pageLibraryBranch"})
public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private AdminService adminService = new AdminService();
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String requestURL  = request.getRequestURI().substring(request.getContextPath().length(), request.getRequestURI().length());
		String redirectURL = "index.jsp";
		
		switch (requestURL) {
			case "/pageAuthors":
				redirectURL = pageAuthors(request);
				break;
			case "/deleteAuthor":
				redirectURL = deleteAuthor(request);
				break;
			case "/pageGenres":
				redirectURL = pageGenres(request);
				break;
			case "/pageBooks":
				redirectURL = pageBooks(request);
				break;
			case "/deleteBook":
				redirectURL = deleteBook(request);
				break;
			case "/deleteGenre":
				redirectURL = deleteGenre(request);
				break;
			case "/pagePublishers":
				redirectURL = pagePublisher(request);
				break;
			case "/deletePublisher":
				redirectURL = deletePublisher(request);
				break;
			case "/pageLibraryBranch":
				redirectURL = pageLibraryBranch(request);
				break;
			case "/deleteLibraryBranch":
				redirectURL = deleteBranch(request);
			default:
				break;
		}
		RequestDispatcher rd = request.getRequestDispatcher(redirectURL);
		rd.forward(request, response);
	}
	
	private String pageLibraryBranch(HttpServletRequest request) {
		if (request.getParameter("pageNo") != null) {
			Integer pageNo = Integer.parseInt(request.getParameter("pageNo"));
			try {
				request.setAttribute("libraryBranches", adminService.readLibraryBranches(null, pageNo));
				request.setAttribute("currentPageNo", pageNo);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return "admin_library_branch.jsp";
		}
		return null;
	}

	private String pageAuthors(HttpServletRequest request) {
		if (request.getParameter("pageNo") != null) {
			Integer pageNo = Integer.parseInt(request.getParameter("pageNo"));
			try {
				request.setAttribute("authors", adminService.readAuthors(null, pageNo));
				request.setAttribute("currentPageNo", pageNo);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return "admin_author.jsp";
		}
		return null;
	}
	
	private String deleteAuthor(HttpServletRequest request) {
		String redirectURL = "admin_author.jsp";
		String message     = "Author deleted Successfully";
		if (request.getParameter("authorId") != null) {
			Integer authorId = Integer.parseInt(request.getParameter("authorId"));
			Author author = new Author();
			author.setAuthorId(authorId);
			try {
				adminService.deleteAuthor(author);
			} catch (SQLException e) {
				e.printStackTrace();
				message = "Author deleted Failed";
			}
		} else {
			message = "Author not found, please contact admin.";
		}
		request.setAttribute("statusMessage", message);
		return redirectURL;
	}
	
	private String deleteBook(HttpServletRequest request) {
		String redirectURL = "admin_book.jsp";
		String message     = "Book deleted Successfully";
		if (request.getParameter("bookId") != null) {
			Integer bookId = Integer.parseInt(request.getParameter("bookId"));
			Book book = new Book();
			book.setBookId(bookId);
			try {
				adminService.deleteBook(book);
			} catch (SQLException e) {
				e.printStackTrace();
				message = "Book deleted Failed";
			}
		} else {
			message = "Book not found, please contact admin.";
		}
		request.setAttribute("statusMessage", message);
		return redirectURL;
	}
	
	private String pageGenres(HttpServletRequest request) {
		if (request.getParameter("pageNo") != null) {
			Integer pageNo = Integer.parseInt(request.getParameter("pageNo"));
			try {
				request.setAttribute("genres", adminService.readGenres(null, pageNo));
				request.setAttribute("currentPageNo", pageNo);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return "admin_genre.jsp";
		}
		return null;
	}
	
	private String pageBooks(HttpServletRequest request) {
		if (request.getParameter("pageNo") != null) {
			Integer pageNo = Integer.parseInt(request.getParameter("pageNo"));
			try {
				request.setAttribute("books", adminService.readBooks(null, pageNo));
				request.setAttribute("currentPageNo", pageNo);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return "admin_book.jsp";
		}
		return null;
	}

	private String deleteGenre(HttpServletRequest request) {
		String message = "Genre deleted Successfully";
		String redirectURL = "admin_genre.jsp";
		if (request.getParameter("genreId") != null) {
			Integer genreId = Integer.parseInt(request.getParameter("genreId"));
			Genre genre = new Genre();
			genre.setGenreId(genreId);
			try {
				adminService.deleteGenre(genre);
			} catch (SQLException e) {
				e.printStackTrace();
				message = "Genre deleted Failed";
			}
		} else {
			message = "Genre not found, please contact admin.";
		}
		request.setAttribute("statusMessage", message);
		return redirectURL;
	}
	
	private String pagePublisher(HttpServletRequest request) {
		if (request.getParameter("pageNo") != null) {
			Integer pageNo = Integer.parseInt(request.getParameter("pageNo"));
			try {
				request.setAttribute("publishers", adminService.readPublishers(null, pageNo));
				request.setAttribute("currentPageNo", pageNo);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return "admin_publisher.jsp";
		}
		return null;
	}
	
	private String deletePublisher(HttpServletRequest request) {
		String message = "Publisher deleted Successfully";
		String redirectURL = "admin_publisher.jsp";
		if (request.getParameter("publisherId") != null) {
			Integer publisherId = Integer.parseInt(request.getParameter("publisherId"));
			Publisher publisher = new Publisher();
			publisher.setPublisherId(publisherId);
			try {
				adminService.deletePublisher(publisher);
			} catch (SQLException e) {
				e.printStackTrace();
				message = "Publisher deleted Failed";
			}
		} else {
			message = "Publisher not found, please contact admin.";
		}
		request.setAttribute("statusMessage", message);
		return redirectURL;
	}
	
	private String deleteBranch(HttpServletRequest request) {
		String message = "Branch deleted Successfully";
		String redirectURL = "admin_library_branch.jsp";
		if (request.getParameter("branchId") != null) {
			Integer branchId = Integer.parseInt(request.getParameter("branchId"));
			LibraryBranch branch = new LibraryBranch();
			branch.setBranchId(branchId);
			try {
				adminService.deleteBranch(branch);
			} catch (SQLException e) {
				e.printStackTrace();
				message = "Branch deleted Failed";
			}
		} else {
			message = "Library branch not found, please contact admin.";
		}
		request.setAttribute("statusMessage", message);
		return redirectURL;
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String requestURL  = request.getRequestURI().substring(request.getContextPath().length(), request.getRequestURI().length());
		String redirectURL = "index.jsp";
		
		switch (requestURL) {
			case "/addBook":
				redirectURL = addBook(request);
				break;
			case "/updateBook":
				redirectURL = updateBook(request);
				break;
			case "/addAuthor":
				redirectURL = addAuthor(request);
				break;
			case "/updateAuthor":
				redirectURL = updateAuthor(request);
				break;
			case "/addGenre":
				redirectURL = addGenre(request);
				break;
			case "/updateGenre":
				redirectURL = updateGenre(request);
				break;
			case "/addPublisher":
				redirectURL = addPublisher(request);
				break;
			case "/updatePublisher":
				redirectURL = updatePublisher(request);
				break;
			case "/addLibraryBranch":
				redirectURL = addLibraryBranch(request);
				break;
			case "/updateLibraryBranch":
				redirectURL = updateLibraryBranch(request);
				break;
			case "/addBorrowers":
				break;
			case "/updateBorrowers":
				break;
			default:
				break;
		}
		RequestDispatcher rd = request.getRequestDispatcher(redirectURL);
		rd.forward(request, response);
	}
	

	private String addAuthor(HttpServletRequest request) {
		String   message     = "Author added successfully!";
		String   redirectURL = "admin_author.jsp";
		Author   author 	 = new Author();
		String   authorName  = request.getParameter("authorName").trim().replaceAll("\\s+", " ");
		String[] bookIds     = request.getParameterValues("bookIds");
		Boolean  exist 		 = Boolean.FALSE;
		try {
			exist = adminService.checkAuthorName(authorName);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		if (authorName == null || authorName.length() == 0) {
			message = "Author name can't be empty, please try again.";
		} else if (exist){
			message = "Author name already exists, please enter a new name.";
		} else if (authorName.length() > 45) {
			message = "Author name can't be more than 45 chars, please try again.";
		} else {
			author.setAuthorName(authorName);
			try {
				if (bookIds != null && bookIds.length > 0) {
					List<Book> books = new ArrayList<>();
					for (String bookId : bookIds) {
						Book book = new Book();
						book = adminService.readOneBook(Integer.parseInt(bookId));
						books.add(book);
					}
					author.setBooks(books);
				}
				adminService.addAuthor(author);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		request.setAttribute("statusMessage", message);
		return redirectURL;
	}
	
	private String addBook(HttpServletRequest request) {
		String   message     = "Book added successfully!";
		String   redirectURL = "admin_book.jsp";
		Book	 book		 = new Book();
		String   title       = request.getParameter("bookTitle").trim().replaceAll("\\s+", " ");
		String   pubilsherId = request.getParameter("publisherId");
		String[] authorIds	 = request.getParameterValues("authorIds");
		String[] genreIds	 = request.getParameterValues("genreIds");
		Boolean  exist 		 = Boolean.FALSE;
		try {
			exist = adminService.checkBookName(title);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		if (title == null || title.length() == 0) {
			message = "title can't be empty, please try again.";
		} else if (exist){
			message = "title already exists, please enter a new name.";
		} else if (title.length() > 45) {
			message = "title can't be more than 45 chars, please try again.";
		} else {
			book.setTitle(title);
			try {
				if (authorIds != null && authorIds.length > 0) {
					List<Author> authors = new ArrayList<>();
					for (String authorId : authorIds) {
						Author author = new Author();
						author = adminService.readOneAuthor(Integer.parseInt(authorId));
						authors.add(author);
					}
					book.setAuthors(authors);
				}
				if (genreIds != null && genreIds.length > 0) {
					List<Genre> genres = new ArrayList<>();
					for (String genreId : genreIds) {
						Genre genre = new Genre();
						genre = adminService.readOneGenre(Integer.parseInt(genreId));
						genres.add(genre);
					}
					book.setGenres(genres);
				}
				if (pubilsherId != null) {
					Publisher publisher = new Publisher();
					publisher.setPublisherId(Integer.parseInt(pubilsherId));
					book.setPublisher(publisher);
				}
				adminService.addBook(book);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		request.setAttribute("statusMessage", message);
		return redirectURL;
	}
	
	private String updateBook(HttpServletRequest request) {
		String   message     = "Book added successfully!";
		String   redirectURL = "admin_book.jsp";
		Book	 book		 = new Book();
		Integer  bookId		 = Integer.parseInt(request.getParameter("bookId"));
		String   title       = request.getParameter("bookTitle").trim().replaceAll("\\s+", " ");
		String   pubilsherId = request.getParameter("publisherId");
		String[] authorIds	 = request.getParameterValues("authorIds");
		String[] genreIds	 = request.getParameterValues("genreIds");
		Boolean  exist 		 = Boolean.FALSE;
		try {
			if (!title.equalsIgnoreCase(request.getParameter("bookTitleOriginal")) && 
					adminService.checkBookName(title)) {
				exist = Boolean.TRUE;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		if (title == null || title.length() == 0) {
			message = "title can't be empty, please try again.";
		} else if (exist){
			message = "title already exists, please enter a new name.";
		} else if (title.length() > 45) {
			message = "title can't be more than 45 chars, please try again.";
		} else {
			book.setTitle(title);
			book.setBookId(bookId);
			try {
				if (authorIds != null && authorIds.length > 0) {
					List<Author> authors = new ArrayList<>();
					for (String authorId : authorIds) {
						Author author = new Author();
						author = adminService.readOneAuthor(Integer.parseInt(authorId));
						authors.add(author);
					}
					book.setAuthors(authors);
				}
				if (genreIds != null && genreIds.length > 0) {
					List<Genre> genres = new ArrayList<>();
					for (String genreId : genreIds) {
						Genre genre = new Genre();
						genre = adminService.readOneGenre(Integer.parseInt(genreId));
						genres.add(genre);
					}
					book.setGenres(genres);
				}
				if (pubilsherId != null) {
					Publisher publisher = new Publisher();
					publisher.setPublisherId(Integer.parseInt(pubilsherId));
					book.setPublisher(publisher);
				}
				adminService.updateBook(book);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		request.setAttribute("statusMessage", message);
		return redirectURL;
	}
	
	private String updateAuthor(HttpServletRequest request) {
		String   message     = "Author updated successfully!";
		String   redirectURL = "admin_author.jsp";
		Author   author 	 = new Author();
		String   authorName  = request.getParameter("authorName").trim().replaceAll("\\s+", " ");
		Integer  authorId    = Integer.parseInt(request.getParameter("authorId"));
		String[] bookIds     = request.getParameterValues("bookIds");
		Boolean  exist 		 = Boolean.FALSE;
		try {
			if (!authorName.equalsIgnoreCase(request.getParameter("authorNameOriginal")) && 
					adminService.checkAuthorName(authorName)) {
				exist = Boolean.TRUE;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		if (authorName == null || authorName.length() == 0) {
			message = "Author name can't be empty, please try again.";
		} else if (exist) {
			message = "Author name already exists, please enter a new name.";
		} else if (authorName.length() > 45) {
			message = "Author name can't be more than 45 chars, please try again.";
		} else {
			author.setAuthorId(authorId);
			author.setAuthorName(authorName);
			try {
				if (bookIds != null && bookIds.length > 0) {
					List<Book> books = new ArrayList<>();
					for (String bookId : bookIds) {
						Book book = new Book();
						book = adminService.readOneBook(Integer.parseInt(bookId));
						books.add(book);
					}
					author.setBooks(books);
				}
				adminService.updateAuthor(author);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		request.setAttribute("statusMessage", message);
		return redirectURL;
	}
	
	private String addGenre(HttpServletRequest request) {
		String   message     = "Genre updated successfully!";
		String   redirectURL = "admin_genre.jsp";
		Genre	 genre		 = new Genre();
		String   genreName   = request.getParameter("genreName").trim().replaceAll("\\s+", " ");
		String[] bookIds     = request.getParameterValues("bookIds");
		Boolean  exist 		 = Boolean.FALSE;
		try {
			exist = adminService.checkGenreName(genreName);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		if (genreName == null || genreName.length() == 0) {
			message = "Genre name can't be empty, please try again.";
		} else if (exist){
			message = "Genre name already exists, please enter a new name.";
		} else if (genreName.length() > 45) {
			message = "Author name can't be more than 45 chars, please try again.";
		} else {
			genre.setGenreName(genreName);
			try {
				if (bookIds != null && bookIds.length > 0) {
					List<Book> books = new ArrayList<>();
					for (String bookId : bookIds) {
						Book book = new Book();
						book = adminService.readOneBook(Integer.parseInt(bookId));
						books.add(book);
					}
					genre.setBooks(books);
				}
				adminService.addGenre(genre);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		request.setAttribute("statusMessage", message);
		return redirectURL;
	}
	
	private String updateGenre(HttpServletRequest request) {
		String   message     = "Genre updated successfully!";
		String   redirectURL = "admin_genre.jsp";
		Genre	 genre 		 = new Genre();
		String   genreName   = request.getParameter("genreName").trim().replaceAll("\\s+", " ");
		Integer  genreId     = Integer.parseInt(request.getParameter("genreId"));
		String[] bookIds     = request.getParameterValues("bookIds");
		Boolean  exist 		 = Boolean.FALSE;
		try {
			if (!genreName.equalsIgnoreCase(request.getParameter("genreNameOriginal")) && 
					adminService.checkGenreName(genreName)) {
				exist = Boolean.TRUE;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		if (genreName == null || genreName.length() == 0) {
			message = "Author name can't be empty, please try again.";
		} else if (exist) {
			message = "Author name already exists, please enter a new name.";
		} else if (genreName.length() > 45) {
			message = "Author name can't be more than 45 chars, please try again.";
		} else {
			genre.setGenreId(genreId);
			genre.setGenreName(genreName);
			try {
				if (bookIds != null && bookIds.length > 0) {
					List<Book> books = new ArrayList<>();
					for (String bookId : bookIds) {
						Book book = new Book();
						book = adminService.readOneBook(Integer.parseInt(bookId));
						books.add(book);
					}
					genre.setBooks(books);
				}
				adminService.updateGenre(genre);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		request.setAttribute("statusMessage", message);
		return redirectURL;
	}
	
	private String updatePublisher(HttpServletRequest request) {
		String    message          = "Publisher updated successfully!";
		String    redirectURL      = "admin_publisher.jsp";
		Publisher publisher        = new Publisher();
		String    publisherName    = request.getParameter("publisherName").trim().replaceAll("\\s+", " ");
		String	  publisherAddress = request.getParameter("publisherAddress").trim().replaceAll("\\s+", " ");
		String	  publisherPhone   = request.getParameter("publisherPhone").trim().replaceAll("\\s+", " ");
		Integer	  publisherId	   = Integer.parseInt(request.getParameter("publisherId"));
		String[]  bookIds          = request.getParameterValues("bookIds");
		Boolean   exist 	       = Boolean.FALSE;
		try {
			if (!publisherName.equalsIgnoreCase(request.getParameter("publisherNameOriginal")) && 
					adminService.checkGenreName(publisherName)) {
				exist = Boolean.TRUE;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		if (publisherName == null || publisherName.length() == 0) {
			message = "Publisher name can't be empty, please try again.";
		} else if (exist){
			message = "Publisher name already exists, please enter a new name.";
		} else if (publisherName.length() > 45) {
			message = "Publisher name can't be more than 45 chars, please try again.";
		} else {
			publisher.setPublisherId(publisherId);
			publisher.setPublisherName(publisherName);
			publisher.setPublisherAddress(publisherAddress);
			publisher.setPublisherPhone(publisherPhone);
			try {
				if (bookIds != null && bookIds.length > 0) {
					List<Book> books = new ArrayList<>();
					for (String bookId : bookIds) {
						Book book = new Book();
						book = adminService.readOneBook(Integer.parseInt(bookId));
						books.add(book);
					}
					publisher.setBooks(books);
				}
				adminService.updatePublisher(publisher);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		request.setAttribute("statusMessage", message);
		return redirectURL;
	}
	
	private String updateLibraryBranch(HttpServletRequest request) {
		String    message           = "Branch info updated successfully!";
		String    redirectURL       = "admin_library_branch.jsp";
		LibraryBranch libraryBranch = new LibraryBranch();
		String	  branchName	    = request.getParameter("branchName").trim().replaceAll("\\s+", " ");
		String	  branchAddress     = request.getParameter("branchAddress").trim().replaceAll("\\s+", " ");
		Integer	  branchId	   		= Integer.parseInt(request.getParameter("branchId"));
		Boolean   exist 	        = Boolean.FALSE;
		try {
			if (!branchName.equalsIgnoreCase(request.getParameter("branchNameOriginal")) && 
					adminService.checkBranchName(branchName)) {
				exist = Boolean.TRUE;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		if (branchName == null || branchName.length() == 0) {
			message = "Branch name can't be empty, please try again.";
		} else if (exist){
			message = "Branch name already exists, please enter a new name.";
		} else if (branchName.length() > 45) {
			message = "Branch name can't be more than 45 chars, please try again.";
		} else {
			libraryBranch.setBranchId(branchId);
			libraryBranch.setBranchName(branchName);
			libraryBranch.setBranchAddress(branchAddress);
			try {
				adminService.updateLibraryBranch(libraryBranch);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		request.setAttribute("statusMessage", message);
		return redirectURL;
	}
	
	private String addPublisher(HttpServletRequest request) {
		String    message          = "Publisher added successfully!";
		String    redirectURL      = "admin_publisher.jsp";
		Publisher publisher        = new Publisher();
		String    publisherName    = request.getParameter("publisherName").trim().replaceAll("\\s+", " ");
		String	  publisherAddress = request.getParameter("publisherAddress").trim().replaceAll("\\s+", " ");
		String	  publisherPhone   = request.getParameter("publisherPhone").trim().replaceAll("\\s+", " ");
		String[]  bookIds          = request.getParameterValues("bookIds");
		Boolean   exist 	       = Boolean.FALSE;
		try {
			exist = adminService.checkPublisherName(publisherName);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		if (publisherName == null || publisherName.length() == 0) {
			message = "Publisher name can't be empty, please try again.";
		} else if (exist){
			message = "Publisher name already exists, please enter a new name.";
		} else if (publisherName.length() > 45) {
			message = "Publisher name can't be more than 45 chars, please try again.";
		} else {
			publisher.setPublisherName(publisherName);
			publisher.setPublisherAddress(publisherAddress);
			publisher.setPublisherPhone(publisherPhone);
			
			try {
				if (bookIds != null && bookIds.length > 0) {
					List<Book> books = new ArrayList<>();
					for (String bookId : bookIds) {
						Book book = new Book();
						book = adminService.readOneBook(Integer.parseInt(bookId));
						books.add(book);
					}
					publisher.setBooks(books);
				}
				adminService.addPublisher(publisher);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		request.setAttribute("statusMessage", message);
		return redirectURL;
	}
	
	private String addLibraryBranch(HttpServletRequest request) {
		String    message           = "Library Branch added successfully!";
		String    redirectURL       = "admin_library_branch.jsp";
		LibraryBranch libraryBranch = new LibraryBranch();
		String 	  branchName 	    = request.getParameter("branchName").trim().replaceAll("\\s+", " ");
		String	  branchAddress     = request.getParameter("branchAddress").trim().replaceAll("\\s+", " ");
		Integer   noOfCopies	    = Integer.parseInt(request.getParameter("noOfCopies").trim().replaceAll("\\s+", " "));
		String[]  bookIds           = request.getParameterValues("bookIds");
		Boolean   exist 	        = Boolean.FALSE;
		Boolean   addBooks			= Boolean.TRUE;
		try {
			exist = adminService.checkBranchName(branchName);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		if (branchName == null || branchName.length() == 0) {
			message = "Branch name can't be empty, please try again.";
		} else if (exist){
			message = "Branch name already exists, please enter a new name.";
		} else if (branchName.length() > 45) {
			message = "Branch name can't be more than 45 chars, please try again.";
		} else {
			libraryBranch.setBranchName(branchName);
			libraryBranch.setBranchAddress(branchAddress);
			if (noOfCopies == 0 || bookIds == null || bookIds.length == 0) {
				addBooks = Boolean.FALSE;
			}
			try {
				if (bookIds != null && bookIds.length != 0) {
					List<BookCopies> bookCopies = new ArrayList<>();
					for (String bookId : bookIds) {
						BookCopies bookCopy = new BookCopies();
						//container for branchId;
						LibraryBranch branch = new LibraryBranch();
						bookCopy.setLibraryBranch(branch);
						//container for bookId;
						Book book = new Book();
						book.setBookId(Integer.parseInt(bookId));
						bookCopy.setBook(book);
						//set No. of copies.
						bookCopy.setNoOfCopies(noOfCopies);
						//add to list.
						bookCopies.add(bookCopy);
					}
					libraryBranch.setBookCopies(bookCopies);
				}
				adminService.addLibraryBranch(libraryBranch, addBooks);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		request.setAttribute("statusMessage", message);
		return redirectURL;
	}
}
