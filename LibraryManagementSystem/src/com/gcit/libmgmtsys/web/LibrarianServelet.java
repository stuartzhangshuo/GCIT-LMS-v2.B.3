package com.gcit.libmgmtsys.web;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gcit.libmgmtsys.entity.*;
import com.gcit.libmgmtsys.service.LibrarianService;

/**
 * Servlet implementation class LibrarianServelet
 */
@WebServlet({"/librarianLogin", "/updateBranchInfo", "/addBookCopies"})
public class LibrarianServelet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    LibrarianService libService = new LibrarianService();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LibrarianServelet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String requestURL  = request.getRequestURI().substring(request.getContextPath().length(), request.getRequestURI().length());
		String redirectURL = "index.jsp";
		Integer branchId   = 1;
		switch (requestURL) {
			case "/librarianLogin":
				redirectURL = "librarian_branch_book.jsp";
				branchId    = Integer.parseInt(request.getParameter("branchId"));
				request.setAttribute("branchId", branchId);
				break;
			case "/updateBranchInfo":
				redirectURL = updateBranchInfo(request);
				break;
			case "/addBookCopies":
				redirectURL = addBookCopies(request);
				break;
			default:
				break;
		}
		RequestDispatcher rd = request.getRequestDispatcher(redirectURL);
		rd.forward(request, response);
	}
	
	private String addBookCopies(HttpServletRequest request) {
		String    message           = "Book copies added successfully!";
		String    redirectURL       = "librarian_branch_book.jsp";
		BookCopies bookCopy = new BookCopies();
		Book book = new Book();
		LibraryBranch branch = new LibraryBranch();
		
		Integer	  bookId	     = Integer.parseInt(request.getParameter("bookId"));
		Integer	  originalCopies = Integer.parseInt(request.getParameter("originalCount"));
		Integer	  noOfCopies     = Integer.parseInt(request.getParameter("noOfCopies"));
		Integer	  branchId	   	 = Integer.parseInt(request.getParameter("branchId"));
		
		book.setBookId(bookId);
		branch.setBranchId(branchId);
		bookCopy.setBook(book);
		bookCopy.setLibraryBranch(branch);
		bookCopy.setNoOfCopies(noOfCopies + originalCopies);
		
		try {
			//need to modify, create check method to check for key.
			if (!libService.checkBookCopies(bookCopy)) {
				libService.insertBookCopies(bookCopy);
			} else {
				libService.updateBookCopies(bookCopy);
			}
			request.setAttribute("branchId", branchId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		request.setAttribute("statusMessage", message);
		return redirectURL;
	}

	private String updateBranchInfo(HttpServletRequest request) {
		String    message           = "Branch info updated successfully!";
		String    redirectURL       = "librarian_branch_book.jsp";
		LibraryBranch libraryBranch = new LibraryBranch();
		String	  branchName	    = request.getParameter("branchName").trim().replaceAll("\\s+", " ");
		String	  branchAddress     = request.getParameter("branchAddress").trim().replaceAll("\\s+", " ");
		Integer	  branchId	   		= Integer.parseInt(request.getParameter("branchId"));
		Boolean   exist 	        = Boolean.FALSE;
		try {
			if (!branchName.equalsIgnoreCase(request.getParameter("branchNameOriginal")) && 
					libService.checkBranchName(branchName)) {
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
				libService.updateLibraryBranch(libraryBranch);
				request.setAttribute("branchId", branchId);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		request.setAttribute("statusMessage", message);
		return redirectURL;
	}
}
