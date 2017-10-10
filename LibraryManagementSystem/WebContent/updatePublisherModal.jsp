<%@page import="java.util.List"%>
<%@page import="com.gcit.libmgmtsys.entity.Book"%>
<%@page import="com.gcit.libmgmtsys.entity.Publisher"%>
<%@page import="com.gcit.libmgmtsys.service.AdminService"%>
<%
	AdminService service          = new AdminService();
	Integer      publisherId      = Integer.parseInt(request.getParameter("publisherId"));
	Publisher    publisher        = service.readOnePublisher(publisherId);
	String		 publisherName    = publisher.getPublisherName();
	String		 publisherAddress = publisher.getPublisherAddress();
	String 		 publisherPhone   = publisher.getPublisherPhone();
	List<Book>   authorBooks 	  = publisher.getBooks();
	List<Book>   books       	  = service.readBooks(null, null);
%>
<div>
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal" aria-label="Close">
		<span aria-hidden="true">&times;</span></button>
		<h4 class="modal-title">Update Publisher Information</h4>
	</div>
	<form action="updatePublisher" method="post">
		<div class="modal-body">
			<p>Enter the name of this publisher:</p>
			<input type="text" class="form-control" name="publisherName" value="<%=publisher.getPublisherName()%>">
			<br>
			<p>Enter the address of this publisher:</p>
			<input type="text" class="form-control" name="publisherAddress" value="<%=publisher.getPublisherAddress()%>">
			<br>
			<p>Enter the phone number of this publisher:</p>
			<input type="text" class="form-control" name="publisherPhone" value="<%=publisher.getPublisherPhone()%>">
			<br>
			<input type="hidden" class="form-control" name="publisherNameOriginal" value="<%=publisher.getPublisherName() %>">
			<input type="hidden" class="form-control" name="publisherId" value="<%=publisherId%>">
			<br>
			<p>You can also select books published by this publisher: </p>
			<div class = "form-group" align = "center">
				<select class = "form-control" id="bookList" multiple="multiple" size = 5 name="bookIds" style = "width:500px">
					<% for (Book book : books) { %>
						<% if (authorBooks.contains(book)){ %>
							<option value="<%=book.getBookId()%>" selected><%=book.getTitle()%></option>
						<% }else { %>
							<option value="<%=book.getBookId()%>"><%=book.getTitle()%></option>
						<%} %>
					<% } %>
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