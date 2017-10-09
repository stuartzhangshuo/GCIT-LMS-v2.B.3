<%@page import="java.sql.Timestamp"%>
<%@page import="com.gcit.libmgmtsys.entity.*"%>
<%@page import="com.gcit.libmgmtsys.service.AdminService"%>
<%
	AdminService service          = new AdminService();
	
	BookLoans bookLoan = new BookLoans();
	Book book = new Book();
	book.setBookId(Integer.parseInt(request.getParameter("bookId")));
	Borrower borrower = new Borrower();
	borrower.setCardNo(Integer.parseInt(request.getParameter("cardNo")));
	LibraryBranch branch = new LibraryBranch();
	branch.setBranchId(Integer.parseInt(request.getParameter("branchId")));
	bookLoan.setBook(book);
	bookLoan.setBorrower(borrower);
	bookLoan.setLibraryBranch(branch);
	System.out.println(request.getParameter("dateOut"));
	bookLoan.setDateOut(request.getParameter("dateOut"));
	bookLoan = service.readOneBookLoan(bookLoan);
%>
<div>
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal" aria-label="Close">
		<span aria-hidden="true">&times;</span></button>
		<h4 class="modal-title">Override Book Loan Due Date</h4>
	</div>
	<form action="updatePublisher" method="post">
		<div class="modal-body">
			<p>Borrower Name: <%=bookLoan.getBorrower().getName() %></p>
			<input type="hidden" class="form-control" name="cardNo" value="<%=bookLoan.getBorrower().getCardNo()%>">
			<br>
			<p>Book title: <%=bookLoan.getBook().getTitle() %></p>
			<input type="hidden" class="form-control" name="bookId" value="<%=bookLoan.getBook().getBookId()%>">
			<br>
			<p>Checked Out From Branch: <%=bookLoan.getLibraryBranch().getBranchName() %></p>
			<input type="hidden" class="form-control" name="branchId" value="<%=bookLoan.getLibraryBranch().getBranchId()%>">
			<br>
			<p>Checked Out on: <%=bookLoan.getDateOut()%></p>
			<input type="hidden" class="form-control" name="dateOut" value="<%=bookLoan.getDateOut()%>">
			<br>
			<p>Current Due Date: <%=bookLoan.getDueDate() %></p>
			<br>
			<p>Please select new due date:</p>
			<input type="date" name="newDueDate" max="2017-12-31" value = <%=bookLoan.getDueDate()%>><br><br>
			
		</div>
		<div class="modal-footer">
			<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
			<button type="submit" class="btn btn-primary">Override</button>
		</div>	      
	</form>
</div>
<!-- clear modal after closed -->
<script>
$(document).ready(function()
{
    $('.modal').on('hidden.bs.modal', function(e)
    { 
        $(this).removeData();
    }) ;
});
</script>