<%@page import="java.util.*"%>
<%@page import="com.gcit.libmgmtsys.entity.Book"%>
<%@page import="com.gcit.libmgmtsys.entity.Author"%>
<%@page import="com.gcit.libmgmtsys.service.AdminService"%>

<% 
AdminService service        = new AdminService();
Integer 	 totalPageCount = service.getAuthorsCount();
Integer		 itemsPerPage   = 10;
Integer	   	 numOfPages	    = totalPageCount % itemsPerPage == 0 ? totalPageCount / itemsPerPage : 
 					   							         		   totalPageCount / itemsPerPage + 1;
Integer 	 currentPageNo  = 1;
List<Author> authors   	    = new ArrayList<>();

if (request.getAttribute("authors") != null) {
	currentPageNo = (Integer)request.getAttribute("currentPageNo");
	authors 	  = (List<Author>)request.getAttribute("authors");
} else {
	authors = service.readAuthors(null, 1);
}
%>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>Manage Author</title>
    <!-- Bootstrap Core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <!-- Custom CSS -->
    <link href="css/sb-admin.css" rel="stylesheet">
    <!-- Custom Fonts -->
    <link href="font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>

<body>
    <div id="wrapper">
    
        <!-- Navigation -->
        <%@include file = "NavSideBar.jsp" %>

        <div id="page-wrapper">
            <div class="container-fluid">

                <!-- Page Heading -->
                <div class="row">
                    <div class="col-lg-12">
                        <h1 class="jumbotron" style = "margin-bottom:5px">Manage Authors</h1>
                        <ol class="breadcrumb">
                            <li>
                                <i class="fa fa-dashboard"></i> Administrator</li>
                            <li class="active">
                                <i class="fa fa-table"></i> Manage Authors
                            </li>
                        </ol>
                    </div>
                </div>
                <!-- /.row -->
				${statusMessage}
                <div class="row">
                    <div class="col-lg-12">
                        <div class="table-responsive">
                        	
                        	<!----------- PAGINATION ------------>
                        	<!-- POPULATE PREVIOUS PAGE BUTTON -->
                        	<nav aria-label="...">
                        		<ul class="pagination">
                        		
							  	<%if (currentPageNo > 1) {%>
							  		<li class="page-item">
								      <a class="page-link" href="pageAuthors?pageNo=<%=currentPageNo - 1%>">Previous</a>
								    </li>
							  	<%} else {%>
							  		<li class="page-item disabled">
								      <span class="page-link">Previous</span>
								    </li>
							  	<%}%>
							  	<!-- POPULATE PAGE NUMS -->
							    <%for (int i = 1; i <= numOfPages; i++) { %>
							    	<% if (currentPageNo == i) {%>
							    		<li class="page-item active"><a class="page-link" href="pageAuthors?pageNo=<%=i%>"><%=i%></a></li>
							    	<%} else {%>
							    		<li class="page-item"><a class="page-link" href="pageAuthors?pageNo=<%=i%>"><%=i%></a></li>
							    	<%}%>
							    <%} %>
							    <!-- POPULATE NEXT PAGE BUTTON -->
							    <%if (currentPageNo < numOfPages) {%>
							  		<li class="page-item">
								      <a class="page-link" href="pageAuthors?pageNo=<%=currentPageNo + 1%>">Next</a>
								    </li>
							  	<%} else {%>
							  		<li class="page-item disabled">
								      <span class="page-link">Next</span>
								    </li>
							  	<%}%>
							  	</ul>
							</nav>
							
							<!-- TABLE -->
                            <table class="table table-striped">
                                <thead>
                                    <tr class = "info">
                                        <th>#Count</th>
                                        <th>Author Name</th>
                                        <th>Books Written</th>
                                        <th>Update</th>
                                        <th>Delete</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <%for (Author a : authors) {%>
									<tr>
										<td> <%= authors.indexOf(a) + 1 %> </td>
										<td> <%= a.getAuthorName() %> </td>
										<td> <%for (Book b : a.getBooks()) { %>
												<span class="label label-info" style = "margin: 5px 5px 5px 5px"><%out.println(b.getTitle()); %></span>
										     <%}
										%>
										</td>
						          				
										<td><button type = "button" class="btn btn-warning btn-xs" data-toggle = "modal" data-target = "#updateAuthorModal" 
										            href = "updateAuthorModal.jsp?authorId=<%=a.getAuthorId() %>" >Update</button></td>
										<td><button type = "button" class="btn btn-danger btn-xs"  onclick = "javascript:location.href ='deleteAuthor?authorId=<%=a.getAuthorId() %>'" >Delete</button></td>
									</tr>
									<%}%>
                                    
                                </tbody>
                            </table>
                            <!-- ADD AUTHOR BUTTON -->
                            <div class = "container" style = "text-align: center">
	                            <button type="button" class="btn btn-success btn-lg" data-toggle="modal"
										data-target="#updateAuthorModal" href = "addAuthorModal.jsp">Add New Author</button>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- /.row -->
                <!-- MODAL WRAPPER -->
                <div class="modal fade" id="updateAuthorModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
				  <div class="modal-dialog" role="document">
				    <div class="modal-content">
				    </div>
				  </div>
				</div>

            </div>
            <!-- /.container-fluid -->

        </div>
        <!-- /#page-wrapper -->

    </div>
    <!-- /#wrapper -->
	
    <!-- jQuery -->
    <script src="js/jquery.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="js/bootstrap.min.js"></script>
    
</body>

</html>
