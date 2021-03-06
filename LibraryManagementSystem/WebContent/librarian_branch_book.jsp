<%@page import="com.gcit.libmgmtsys.service.LibrarianService"%>
<%@page import="java.util.*"%>
<%@page import="com.gcit.libmgmtsys.entity.*"%>

<% 
LibrarianService service = new LibrarianService();
Integer branchId = Integer.parseInt(request.getParameter("branchId"));

List<Book> books = service.readBooks(null, null);
LibraryBranch libraryBranch = service.readOneBranch(branchId);

// Integer 	 totalPageCount = service.getAuthorsCount();
// Integer		 itemsPerPage   = 10;
// Integer	   	 numOfPages	    = totalPageCount % itemsPerPage == 0 ? totalPageCount / itemsPerPage : 
//  					   							         		   totalPageCount / itemsPerPage + 1;
// Integer 	 currentPageNo  = 1;
// List<Author> authors   	    = new ArrayList<>();

// if (request.getAttribute("authors") != null) {
// 	currentPageNo = (Integer)request.getAttribute("currentPageNo");
// 	authors 	  = (List<Author>)request.getAttribute("authors");
// } else {
// 	authors = service.readAuthors(null, 1);
// }
%>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>Check out books</title>
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
                        <h1 class="jumbotron" style = "margin-bottom:5px">Branch Management</h1>
                        <ol class="breadcrumb">
                            <li>
                                <i class="fa fa-dashboard"></i> Librarian</li>
                            <li class="active">
                                <i class="fa fa-table"></i> View/Update Branch Information
                            </li>
                        </ol>
                    </div>
                </div>
                <!-- /.row -->
				${statusMessage}
                <div class="row">
                    <div class="col-lg-12">
                    	<div class = "panel panel-primary">
                    		<div class = "panel-heading">Branch Info</div>
                    		<div class="table-responsive">
                        	
	                        	<!----------- PAGINATION ------------>
	                        	<!-- POPULATE PREVIOUS PAGE BUTTON -->
	<!--                         	<nav aria-label="..."> -->
	<!--                         		<ul class="pagination"> -->
	                        		
	<%-- 							  	<%if (currentPageNo > 1) {%> --%>
	<!-- 							  		<li class="page-item"> -->
	<%-- 								      <a class="page-link" href="pageAuthors?pageNo=<%=currentPageNo - 1%>">Previous</a> --%>
	<!-- 								    </li> -->
	<%-- 							  	<%} else {%> --%>
	<!-- 							  		<li class="page-item disabled"> -->
	<!-- 								      <span class="page-link">Previous</span> -->
	<!-- 								    </li> -->
	<%-- 							  	<%}%> --%>
	<!-- 							  	POPULATE PAGE NUMS -->
	<%-- 							    <%for (int i = 1; i <= numOfPages; i++) { %> --%>
	<%-- 							    	<% if (currentPageNo == i) {%> --%>
	<%-- 							    		<li class="page-item active"><a class="page-link" href="pageAuthors?pageNo=<%=i%>"><%=i%></a></li> --%>
	<%-- 							    	<%} else {%> --%>
	<%-- 							    		<li class="page-item"><a class="page-link" href="pageAuthors?pageNo=<%=i%>"><%=i%></a></li> --%>
	<%-- 							    	<%}%> --%>
	<%-- 							    <%} %> --%>
	<!-- 							    POPULATE NEXT PAGE BUTTON -->
	<%-- 							    <%if (currentPageNo < numOfPages) {%> --%>
	<!-- 							  		<li class="page-item"> -->
	<%-- 								      <a class="page-link" href="pageAuthors?pageNo=<%=currentPageNo + 1%>">Next</a> --%>
	<!-- 								    </li> -->
	<%-- 							  	<%} else {%> --%>
	<!-- 							  		<li class="page-item disabled"> -->
	<!-- 								      <span class="page-link">Next</span> -->
	<!-- 								    </li> -->
	<%-- 							  	<%}%> --%>
	<!-- 							  	</ul> -->
	<!-- 							</nav> -->
								
								<!-- TABLE BRANCH INFO -->
	                            <table class="table table-striped">
	                                <thead>
	                                    <tr class = "info">
	                                        <th>Branch Name</th>
	                                        <th>Branch Address</th>
	                                        <th>Update</th>
	                                    </tr>
	                                </thead>
	                                <tbody>
										<tr>
											<td> <%= libraryBranch.getBranchName() %> </td>
											<td> <%= libraryBranch.getBranchAddress() %> </td>
											<td><button type = "button" class="btn btn-primary btn-xs" data-toggle = "modal" data-target = "#updateBranchInfoModal"
													href = "updateBranchInfoModal.jsp?branchId=<%=branchId%>" >Update</button>
											</td>
										</tr>
	                                </tbody>
	                            </table>
	                        </div>
                    	</div>
                    	
                    	<div class = "panel panel-primary">
                    		<div class = "panel-heading">Book Copies In This Branch</div>
                    		<div class="table-responsive">
                    			<!-- TABLE BRANCH BOOK INFO -->
	                            <table class="table table-striped">
	                                <thead>
	                                    <tr class = "info">
	                                        <th>#Count</th>
	                                        <th>Book Title</th>
	                                        <th>Publisher</th>
	                                        <th>Authors</th>
	                                        <th>Genres</th>
	                                        <th>No. Of Copies Available</th>
	                                        <th>Update</th>
	                                    </tr>
	                                </thead>
	                                <tbody>
	                                    <%for (Book book : books) {%>
										<tr>
											<td> <%= books.indexOf(book) + 1 %> </td>
											<td> <%= book.getTitle() %> </td>
											<td> <%= book.getPublisher().getPublisherName() %> </td>
											<td> <%for (Author author : book.getAuthors()) { %>
												 	<span class="label label-info" style = "margin: 5px 5px 5px 5px"><%=author.getAuthorName()%></span>
												 <%} %>
											</td>
											<td> <%for (Genre genre : book.getGenres()) { %>
												 	<span class="label label-info" style = "margin: 5px 5px 5px 5px"><%=genre.getGenreName()%></span>
												 <%} %>
											</td>
											<%if (book.getBranchCopies() != null && book.getBranchCopies().containsKey(branchId)) {%>
												<td><%=book.getBranchCopies().get(branchId) %></td>
											<%} else { %>
												<td>0</td>
											<%} %>
											<td><button type = "button" class="btn btn-primary btn-xs" data-toggle = "modal" data-target = "#updateBranchInfoModal"
												href ="addBookCopiesModal.jsp?branchId=<%=branchId%>&bookId=<%=book.getBookId()%>" >Add Copies</button>
											</td>
										</tr>
										<%}%>
	                                    
	                                </tbody>
	                            </table>
                    		</div>
                    	</div>
                        
                    </div>
                </div>
                <!-- /.row -->

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
<!-- MODAL WRAPPER -->
                <div class="modal fade" id="updateBranchInfoModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
				  <div class="modal-dialog" role="document">
				    <div class="modal-content">
				    </div>
				  </div>
				</div>

</html>
