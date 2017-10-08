<%@page import="java.util.List"%>
<%@page import="com.gcit.libmgmtsys.entity.Book"%>
<%@page import="com.gcit.libmgmtsys.entity.LibraryBranch"%>
<%@page import="com.gcit.libmgmtsys.service.AdminService"%>
<%
	AdminService service = new AdminService();
	List<Book>   books   = service.readBooks(null, 1);
%>
<div>
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal" aria-label="Close">
		<span aria-hidden="true">&times;</span></button>
		<h4 class="modal-title">Add New Branch Information</h4>
	</div>
	<form action="addLibraryBranch" method="post">
		<div class="modal-body">
			<p>Enter the name of this branch:</p>
			<input type="text" class="form-control" name="branchName">
			<br>
			<p>Enter the Address of this branch:</p>
			<input type="text" class="form-control" name="branchAddress">
			<br>
			<p>You can also add books to this new branch: </p>
			<div class = "form-group" align = "center">
				<select class = "form-control" id="bookList" multiple="multiple" size = 10 name="bookIds" style = "width:500px">
					<% for (Book book : books) { %>
						<option value="<%=book.getBookId()%>"><%=book.getTitle()%></option>
					<% } %>
				</select>
			</div>
			<p>How many copies of each book do you want to add to the library? </p>
			<div class = "form-group" align = "center">
				<select class = "form-control" id="countList" name="noOfCopies" style = "width:500px">
					<option value="5">0</option>
					<option value="5">5</option>
					<option value="5">10</option>
					<option value="5">15</option>
					<option value="5">20</option>
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