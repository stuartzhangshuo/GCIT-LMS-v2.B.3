package com.gcit.libmgmtsys.web;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gcit.libmgmtsys.dao.BookDAO;
import com.gcit.libmgmtsys.entity.*;
import com.gcit.libmgmtsys.service.BorrowerService;
/**
 * Servlet implementation class BorrowerServelet
 */
@WebServlet({"/borrowerCheckIn", "/checkInBook", "/borrowerCheckOut", "/checkOutBook"})
public class BorrowerServelet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    BorrowerService borrowerService = new BorrowerService();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BorrowerServelet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String requestURL  = request.getRequestURI().substring(request.getContextPath().length(), request.getRequestURI().length());
		String redirectURL = "index.jsp";
		switch (requestURL) {
			case "/checkInBook":
				redirectURL = checkInBook(request);
				break;
			case "/checkOutBook":
				redirectURL = checkOutBook(request);
				break;
			default:
				break;
		}
		
		RequestDispatcher rd = request.getRequestDispatcher(redirectURL);
		rd.forward(request, response);
	}

	private String checkOutBook(HttpServletRequest request) {
		String redirectURL = "borrower_check_out.jsp";
		String message     = "Checked Out Successfully";
		if (request.getParameter("bookId") != null && request.getParameter("branchId") != null
				&& request.getParameter("cardNo") != null) {
			Integer bookId   = Integer.parseInt(request.getParameter("bookId"));
			Integer branchId = Integer.parseInt(request.getParameter("branchId"));
			Integer cardNo   = Integer.parseInt(request.getParameter("cardNo"));
			//entities
			BookLoans 	  bookLoan = new BookLoans();
			Book 	  	  book 	   = new Book();
			LibraryBranch branch   = new LibraryBranch();
			Borrower 	  borrower = new Borrower();
			//setup each entity
			book.setBookId(bookId);
			branch.setBranchId(branchId);
			borrower.setCardNo(cardNo);
			//setup book loan
			bookLoan.setBook(book);
			bookLoan.setLibraryBranch(branch);
			bookLoan.setBorrower(borrower);
			try {
				borrowerService.checkOutOneBook(bookLoan);
				request.setAttribute("cardNo", cardNo);
				request.setAttribute("branchId", branchId);
			} catch (SQLException e) {
				e.printStackTrace();
				message = "Book loan deleted Failed";
			}
		} else {
			message = "Book Loan not found, please contact admin.";
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
		Integer branchId = 1;
		Integer cardNo   = 1;
		switch (requestURL) {
			case "/borrowerCheckIn":
				redirectURL = "borrower_check_in.jsp";
				branchId    = Integer.parseInt(request.getParameter("branchId"));
				cardNo      = Integer.parseInt(request.getParameter("cardNo"));
				request.setAttribute("cardNo", cardNo);
				request.setAttribute("branchId", branchId);
				break;
			case "/borrowerCheckOut":
				redirectURL = "borrower_check_out.jsp";
				branchId    = Integer.parseInt(request.getParameter("branchId"));
				cardNo      = Integer.parseInt(request.getParameter("cardNo"));
				request.setAttribute("cardNo", cardNo);
				request.setAttribute("branchId", branchId);
				break;
			default:
				break;
		}
		
		RequestDispatcher rd = request.getRequestDispatcher(redirectURL);
		rd.forward(request, response);
	}

	private String checkInBook(HttpServletRequest request) {
		String redirectURL = "borrower_check_in.jsp";
		String message     = "Checked In Successfully";
		if (request.getParameter("bookId") != null && request.getParameter("branchId") != null
				&& request.getParameter("cardNo") != null && request.getParameter("dateOut") != null) {
			Integer bookId   = Integer.parseInt(request.getParameter("bookId"));
			Integer branchId = Integer.parseInt(request.getParameter("branchId"));
			Integer cardNo   = Integer.parseInt(request.getParameter("cardNo"));
			String  dateOut  = request.getParameter("dateOut");
			//entities
			BookLoans 	  bookLoan = new BookLoans();
			Book 	  	  book 	   = new Book();
			LibraryBranch branch   = new LibraryBranch();
			Borrower 	  borrower = new Borrower();
			//setup each entity
			book.setBookId(bookId);
			branch.setBranchId(branchId);
			borrower.setCardNo(cardNo);
			//setup book loan
			bookLoan.setBook(book);
			bookLoan.setLibraryBranch(branch);
			bookLoan.setBorrower(borrower);
			bookLoan.setDateOut(dateOut);
			try {
				borrowerService.checkInOneBook(bookLoan);
				request.setAttribute("cardNo", cardNo);
				request.setAttribute("branchId", branchId);
			} catch (SQLException e) {
				e.printStackTrace();
				message = "Book loan deleted Failed";
			}
		} else {
			message = "Book Loan not found, please contact admin.";
		}
		request.setAttribute("statusMessage", message);
		
		return redirectURL;
	}
}
