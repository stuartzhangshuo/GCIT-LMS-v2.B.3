<%@page import="com.gcit.libmgmtsys.entity.Book"%>
<%@page import="com.gcit.libmgmtsys.service.LibrarianService"%>
<%@page import="java.util.List"%>
<%
	LibrarianService service = new LibrarianService();
	Integer branchId = Integer.parseInt(request.getParameter("branchId"));
	Integer bookId = Integer.parseInt(request.getParameter("bookId"));
	Book book = service.readOneBook(bookId);
	Integer originalCount = 0;
	if (book.getBranchCopies() != null && book.getBranchCopies().containsKey(branchId)) {
		originalCount = book.getBranchCopies().get(branchId);
	}
%>
<div>
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal" aria-label="Close">
		<span aria-hidden="true">&times;</span></button>
		<h4 class="modal-title">Add New Book Copies</h4>
	</div>
	
	<form action="addBookCopies" method="post">
		<input type="hidden" class="form-control" name="originalCount" value="<%=originalCount%>">
		<input type="hidden" class="form-control" name="bookId" value="<%=bookId%>">
		<input type="hidden" class="form-control" name="branchId" value="<%=branchId%>">
		<div class="modal-body">
			<p>Please select how many copies you want to add: </p>
			<div class = "form-group" align = "center">
				<select class = "form-control" id="noOfCopiesList" name="noOfCopies" style = "width:500px">
					<option value= "1">1</option>
					<option value= "3">3</option>
					<option value= "5">5</option>
					<option value= "7">7</option>
					<option value= "10">10</option>
				</select>
			</div>
		</div>
		<div class="modal-footer">
			<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
			<button type="submit" class="btn btn-primary">Save</button>
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