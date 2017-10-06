<%@page import="java.util.List"%>
<%@page import="com.gcit.libmgmtsys.entity.*"%>
<%@page import="com.gcit.libmgmtsys.service.AdminService"%>
<%
	AdminService    service    = new AdminService();
	List<Book>      books      = service.readBooks(null, 1);
	List<Author>    authors    = service.readAuthors(null, 1);
	List<Publisher> publishers = service.readPublishers(null, 1);
	List<Genre>     genres     = service.readGenres(null, 1);
%>
<div>
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal" aria-label="Close">
		<span aria-hidden="true">&times;</span></button>
		<h4 class="modal-title">Add New Book Information</h4>
	</div>
	<form action="addBook" method="post">
		<div class="modal-body">
			<p>Enter the title of your book:</p>
			<input type="text" class="form-control" name="bookTitle">
			<br>
			<p>You can also select a publisher for this book: </p>
			<div class = "form-group" align = "center">
				<select class = "form-control" id="publisherList" name="publisherId" style = "width:500px">
					<% for (Publisher publisher : publishers) { %>
						<option value="<%=publisher.getPublisherId()%>"><%=publisher.getPublisherName()%></option>
					<% } %>
				</select>
			</div>
			<br>
			<p>You can also select authors for this book: </p>
			<div class = "form-group" align = "center">
				<select class = "form-control" id="authorList" multiple="multiple" size = 5 name="authorIds" style = "width:500px">
					<% for (Author author : authors) { %>
						<option value="<%=author.getAuthorId()%>"><%=author.getAuthorName()%></option>
					<% } %>
				</select>
			</div>
			<br>
			<p>You can also select genres for this book: </p>
			<div class = "form-group" align = "center">
				<select class = "form-control" id="genreList" multiple="multiple" size = 5 name="genreIds" style = "width:500px">
					<% for (Genre genre : genres) { %>
						<option value="<%=genre.getGenreId()%>"><%=genre.getGenreName()%></option>
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