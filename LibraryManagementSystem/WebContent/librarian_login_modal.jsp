<%@page import="com.gcit.libmgmtsys.service.LibrarianService"%>
<%@page import="com.gcit.libmgmtsys.entity.LibraryBranch"%>
<%@page import="java.util.List"%>
<%
	LibrarianService service = new LibrarianService();
	List<LibraryBranch> libraryBranches   = service.readLibraryBranches(null, null);
%>
<div>
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal" aria-label="Close">
		<span aria-hidden="true">&times;</span></button>
		<h4 class="modal-title">Please select your library branch and enter your card number: </h4>
	</div>
	<form action="librarianLogin" method="post">
		<div class="modal-body">
			<p>Please select your library branch: </p>
			<div class = "form-group" align = "center">
				<select class = "form-control" id="branchList" name="branchId" style = "width:500px">
					<% for (LibraryBranch branch : libraryBranches) { %>
						<option value="<%=branch.getBranchId()%>"><%=branch.getBranchName()%></option>
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