<%@page import="com.gcit.libmgmtsys.service.LibrarianService"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.libmgmtsys.entity.LibraryBranch"%>
<%@page import="com.gcit.libmgmtsys.service.AdminService"%>
<%
	LibrarianService  service       = new LibrarianService();
	Integer           branchId      = Integer.parseInt(request.getParameter("branchId"));
	LibraryBranch     libraryBranch = service.readOneBranch(branchId);
%>
<div>
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal" aria-label="Close">
		<span aria-hidden="true">&times;</span></button>
		<h4 class="modal-title">Update Branch Information</h4>
	</div>
	<form action="updateBranchInfo" method="post">
		<div class="modal-body">
			<p>Enter the new name of this branch: </p>
			<input type="text" class="form-control" name="branchName" value="<%=libraryBranch.getBranchName()%>">
			<br>
			<p>Enter the new address of this branch:</p>
			<input type="text" class="form-control" name="branchAddress" value="<%=libraryBranch.getBranchAddress()%>">
			<br>
			<input type="hidden" class="form-control" name="branchNameOriginal" value="<%=libraryBranch.getBranchName()%>">
			<input type="hidden" class="form-control" name="branchId" value="<%=branchId%>">
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