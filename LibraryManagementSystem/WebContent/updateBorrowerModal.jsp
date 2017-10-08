<%@page import="java.util.List"%>
<%@page import="com.gcit.libmgmtsys.entity.Borrower"%>
<%@page import="com.gcit.libmgmtsys.service.AdminService"%>
<%
	AdminService service = new AdminService();
	Integer cardNo = Integer.parseInt(request.getParameter("cardNo"));
	Borrower borrower = service.readOneBorrower(cardNo);
%>
<div>
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal" aria-label="Close">
		<span aria-hidden="true">&times;</span></button>
		<h4 class="modal-title">Update Borrower Information</h4>
	</div>
	<form action="updateBorrowers" method="post">
		<div class="modal-body">
			<p>Enter the name of this borrower:</p>
			<input type="text" class="form-control" name="borrowerName" value = "<%=borrower.getName() %>">
			<input type="hidden" class="form-control" name="borrowerNameOriginal" value="<%=borrower.getName() %>">
			<input type="hidden" class="form-control" name="cardNo" value="<%=borrower.getCardNo()%>">
			<br>
			<p>Enter the Address of this borrower:</p>
			<input type="text" class="form-control" name="borrowerAddress" value = "<%=borrower.getAddress() %>">
			<br>
			<p>Enter the Phone Number of this borrower:</p>
			<input type="text" class="form-control" name="borrowerPhone" value = "<%=borrower.getPhone() %>">
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