<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-ex1-collapse">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
        </button>
        <a class="navbar-brand" href="index.jsp">GCIT Library Management System</a>
    </div>
    <!-- Top Menu Items -->
    <ul class="nav navbar-right top-nav">
        <li class="dropdown">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-user"></i> Stuart Zhang <b class="caret"></b></a>
            <ul class="dropdown-menu">
                <li>
                    <a href="#"><i class="fa fa-fw fa-user"></i> Profile</a>
                </li>
                <li>
                    <a href="#"><i class="fa fa-fw fa-envelope"></i> Inbox</a>
                </li>
                <li>
                    <a href="#"><i class="fa fa-fw fa-gear"></i> Settings</a>
                </li>
                <li class="divider"></li>
                <li>
                    <a href="#"><i class="fa fa-fw fa-power-off"></i> Log Out</a>
                </li>
            </ul>
        </li>
    </ul>
    <!-- Sidebar Menu Items - These collapse to the responsive navigation menu on small screens -->
    <div class="collapse navbar-collapse navbar-ex1-collapse">
        <ul class="nav navbar-nav side-nav">
            <li class="active">
                <a href="index.jsp"><i class="fa fa-fw fa-dashboard"></i> Home</a>
            </li>
            <li>
                <a href="javascript:;" data-toggle="collapse" data-target="#librarian"><i class="fa fa-fw fa-arrows-v"></i> Librarian <i class="fa fa-fw fa-caret-down"></i></a>
                <ul id="librarian" class="collapse">
                    <li>
                        <a href="#">View/Update Branch Info</a>
                    </li>
                    <li>
                        <a href="#">Update Book Copies</a>
                    </li>
                </ul>
            </li>
             <li>
                <a href="javascript:;" data-toggle="collapse" data-target="#borrower"><i class="fa fa-fw fa-arrows-v"></i> Borrower <i class="fa fa-fw fa-caret-down"></i></a>
                <ul id="borrower" class="collapse">
                    <li>
                        <a href="#">Check-Out Books</a>
                    </li>
                    <li>
                        <a href="#">Check-In Books</a>
                    </li>
                </ul>
            </li>
             <li>
                <a href="javascript:;" data-toggle="collapse" data-target="#admin"><i class="fa fa-fw fa-arrows-v"></i> Administrator <i class="fa fa-fw fa-caret-down"></i></a>
                <ul id="admin" class="collapse">
                    <li>
                        <a href="admin_book.jsp">Manage Books</a>
                    </li>
                    <li>
                        <a href="admin_author.jsp">Manage Authors</a>
                    </li>
                    <li>
                        <a href="admin_genre.jsp">Manage Genres</a>
                    </li>
                    <li>
                        <a href="admin_publisher.jsp">Manage Publishers</a>
                    </li>
                    <li>
                        <a href="admin_library_branch.jsp">Manage Library Branches</a>
                    </li>
                    <li>
                        <a href="#">Manage Borrowers</a>
                    </li>
                    <li>
                        <a href="#">Manage Book Loans</a>
                    </li>
                </ul>
            </li>
            <li>
                <a href="blank-page.html"><i class="fa fa-fw fa-file"></i> Link</a>
            </li>
        </ul>
    </div>
    <!-- /.navbar-collapse -->
</nav>