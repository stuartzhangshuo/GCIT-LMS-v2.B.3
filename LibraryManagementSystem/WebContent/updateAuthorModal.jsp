<%@page import="java.util.List"%>
<%@page import="com.gcit.libmgmtsys.entity.Book"%>
<%@page import="com.gcit.libmgmtsys.entity.Author"%>
<%@page import="com.gcit.libmgmtsys.service.AdminService"%>
<%
	AdminService service     = new AdminService();
	Integer      authorId    = Integer.parseInt(request.getParameter("authorId"));
	Author       author      = service.readOneAuthor(authorId);
	String       authorName  = author.getAuthorName();
	List<Book>   authorBooks = author.getBooks();
	List<Book>   books       = service.readBooks(null, null);
%>
<div>
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal" aria-label="Close">
		<span aria-hidden="true">&times;</span></button>
		<h4 class="modal-title">Update Author Information</h4>
	</div>
	<form action="updateAuthor" method="post">
		<div class="modal-body">
			<p>Enter the name of your author:</p>
			<input type="text" class="form-control" name="authorName" value="<%=author.getAuthorName()%>">
			<input type="hidden" class="form-control" name="authorNameOriginal" value="<%=author.getAuthorName() %>">
			<input type="hidden" class="form-control" name="authorId" value="<%=author.getAuthorId()%>">
			<br>
			<p>You can also select books written by this author: </p>
			<div class = "form-group" align = "center">
				<select class = "form-control" id="authorList" multiple="multiple" size = 5 name="bookIds" style = "width:500px">
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