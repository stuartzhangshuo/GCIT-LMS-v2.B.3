<%@page import="java.util.List"%>
<%@page import="com.gcit.libmgmtsys.entity.Book"%>
<%@page import="com.gcit.libmgmtsys.entity.Genre"%>
<%@page import="com.gcit.libmgmtsys.service.AdminService"%>
<%
	AdminService service     = new AdminService();
	Integer      genreId     = Integer.parseInt(request.getParameter("genreId"));
	Genre		 genre		 = service.readOneGenre(genreId);
	String       genreName   = genre.getGenreName();
	List<Book>   genreBooks  = genre.getBooks();
	List<Book>   books       = service.readBooks(null, null);
%>
<div>
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal" aria-label="Close">
		<span aria-hidden="true">&times;</span></button>
		<h4 class="modal-title">Update Genre Information</h4>
	</div>
	<form action="updateGenre" method="post">
		<div class="modal-body">
			<p>Enter the name of this genre:</p>
			<input type="text" class="form-control" name="genreName" value="<%=genre.getGenreName()%>">
			<input type="hidden" class="form-control" name="genreNameOriginal" value="<%=genre.getGenreName() %>">
			<input type="hidden" class="form-control" name="genreId" value="<%=genre.getGenreId()%>">
			<br>
			<p>You can also select books of this genre: </p>
			<div class = "form-group" align = "center">
				<select class = "form-control" id="bookList" multiple="multiple" size = 5 name="bookIds" style = "width:500px">
					<% for (Book book : books) { %>
						<% if (genreBooks.contains(book)){ %>
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