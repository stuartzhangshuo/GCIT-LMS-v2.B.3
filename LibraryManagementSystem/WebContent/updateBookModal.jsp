<%@page import="java.util.List"%>
<%@page import="com.gcit.libmgmtsys.entity.*"%>
<%@page import="com.gcit.libmgmtsys.service.AdminService"%>
<%
	AdminService    service       = new AdminService();
	Integer 		bookId	   	  = Integer.parseInt(request.getParameter("bookId"));
	Book			book	   	  = service.readOneBook(bookId);
	Publisher 	    bookPublisher = book.getPublisher();
	List<Author>    bookAuthors   = book.getAuthors();
	List<Genre>     bookGenres    = book.getGenres();
	
	List<Author>    authors    = service.readAuthors(null, null);
	List<Publisher> publishers = service.readPublishers(null, null);
	List<Genre>     genres     = service.readGenres(null, null);
%>
<div>
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal" aria-label="Close">
		<span aria-hidden="true">&times;</span></button>
		<h4 class="modal-title">Update Book Information</h4>
	</div>
	<form action="updateBook" method="post">
		<div class="modal-body">
			<p>Enter the title of your book:</p>
			<input type="text" class="form-control" name="bookTitle" value = "<%=book.getTitle()%>">
			<input type="hidden" class="form-control" name="bookTitleOriginal" value="<%=book.getTitle() %>">
			<input type="hidden" class="form-control" name="bookId" value="<%=book.getBookId()%>">
			<br>
			<p>You can modify publisher for this book: </p>
			<div class = "form-group" align = "center">
				<select class = "form-control" id="publisherList" name="publisherId" style = "width:500px">
					<% for (Publisher publisher : publishers) { %>
						<% if (bookPublisher != null && bookPublisher.equals(publisher)){ %>
							<option value="<%=publisher.getPublisherId()%>" selected><%=publisher.getPublisherName()%></option>
						<% }else { %>
							<option value="<%=publisher.getPublisherId()%>"><%=publisher.getPublisherName()%></option>
						<%} %>
					<% } %>
				</select>
			</div>
			<br>
			<p>You can modify authors for this book: </p>
			<div class = "form-group" align = "center">
				<select class = "form-control" id="authorList" multiple="multiple" size = 5 name="authorIds" style = "width:500px">
					<% for (Author author : authors) { %>
						<% if (bookAuthors.contains(author)){ %>
							<option value="<%=author.getAuthorId()%>" selected><%=author.getAuthorName()%></option>
						<% }else { %>
							<option value="<%=author.getAuthorId()%>"><%=author.getAuthorName()%></option>
						<%} %>
					<% } %>
				</select>
			</div>
			<br>
			<p>You can modify genres for this book: </p>
			<div class = "form-group" align = "center">
				<select class = "form-control" id="genreList" multiple="multiple" size = 5 name="genreIds" style = "width:500px">
					<% for (Genre genre : genres) { %>
						<% if (bookGenres.contains(genre)){ %>
							<option value="<%=genre.getGenreId()%>" selected><%=genre.getGenreName()%></option>
						<% }else { %>
							<option value="<%=genre.getGenreId()%>"><%=genre.getGenreName()%></option>
						<%} %>
					<% } %>
				</select>
			</div>
			<br>
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