<!--
 Author: Quintus Labs
  Author URL: http://quintuslabs.com
  date: 12/11/2019
  Github URL: https://github.com/quintuslabs/GroceryStore-with-server/
-->
<?php
session_start();
error_reporting(0);
include('includes/config.php');
if(strlen($_SESSION['alogin'])==0)
{	
header('location:index.php');
}
else{
?>
<!doctype html>
<html lang="en" class="no-js">
  <head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <meta name="theme-color" content="#3e454c">
    <title>Grocery Store | Admin Dashboard
    </title>
    <!-- Font awesome -->
    <link rel="stylesheet" href="css/font-awesome.min.css">
    <!-- Sandstone Bootstrap CSS -->
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <!-- Bootstrap Datatables -->
    <link rel="stylesheet" href="css/dataTables.bootstrap.min.css">
    <!-- Bootstrap social button library -->
    <link rel="stylesheet" href="css/bootstrap-social.css">
    <!-- Bootstrap select -->
    <link rel="stylesheet" href="css/bootstrap-select.css">
    <!-- Bootstrap file input -->
    <link rel="stylesheet" href="css/fileinput.min.css">
    <!-- Awesome Bootstrap checkbox -->
    <link rel="stylesheet" href="css/awesome-bootstrap-checkbox.css">
    <!-- Admin Stye -->
    <link rel="stylesheet" href="css/style.css">
  </head>
  <body>
    <?php include('includes/header.php');?>
    <div class="ts-main-content">
      <?php include('includes/leftbar.php');?>
      <div class="content-wrapper">
        <div class="container-fluid">
          <div class="row">
            <div class="col-md-12">
              <h2 class="page-title">Dashboard
              </h2>
              <div class="row">
                <div class="col-md-12">
                  <div class="row">
                    <div class="col-md-3">
                      <div class="panel panel-default">
                        <div class="panel-body bk-primary text-light">
                          <div class="stat-panel text-center">
                            <?php 
								$sql ="SELECT id from orders";
								$query = $dbh -> prepare($sql);
								$query->execute();
								$results=$query->fetchAll(PDO::FETCH_OBJ);
								$bg=$query->rowCount();
								?>
                            <div class="stat-panel-number h1 ">
                              <?php echo htmlentities($bg);?>
                            </div>
                            <div class="stat-panel-title text-uppercase">Orders
                            </div>
                          </div>
                        </div>
                        <a href="manage-orders.php" class="block-anchor panel-footer">Full Detail 
                          <i class="fa fa-arrow-right">
                          </i>
                        </a>
                      </div>
                    </div>
					<div class="col-md-3">
                      <div class="panel panel-default">
                        <div class="panel-body bk-danger text-light">
                          <div class="stat-panel text-center">
					<?php 
						$sql1 ="SELECT id from items";
						$query1 = $dbh -> prepare($sql1);;
						$query1->execute();
						$results1=$query1->fetchAll(PDO::FETCH_OBJ);
						$regbd=$query1->rowCount();
						?>
                            <div class="stat-panel-number h1 ">
                              <?php echo htmlentities($regbd);?>
                            </div>
                            <div class="stat-panel-title text-uppercase">Items
                            </div>
                          </div>
                        </div>
                        <a href="manage-items.php" class="block-anchor panel-footer text-center">Full Detail &nbsp; 
                          <i class="fa fa-arrow-right">
                          </i>
                        </a>
                      </div>
                    </div>
					
                    <div class="col-md-3">
                      <div class="panel panel-default">
                        <div class="panel-body bk-success text-light">
                          <div class="stat-panel text-center">
					<?php 
						$sql1 ="SELECT id from message";
						$query1 = $dbh -> prepare($sql1);;
						$query1->execute();
						$results1=$query1->fetchAll(PDO::FETCH_OBJ);
						$regbd=$query1->rowCount();
						?>
                            <div class="stat-panel-number h1 ">
                              <?php echo htmlentities($regbd);?>
                            </div>
                            <div class="stat-panel-title text-uppercase">Messages
                            </div>
                          </div>
                        </div>
                        <a href="message.php" class="block-anchor panel-footer text-center">Full Detail &nbsp; 
                          <i class="fa fa-arrow-right">
                          </i>
                        </a>
                      </div>
                    </div>
                    <div class="col-md-3">
                      <div class="panel panel-default">
                        <div class="panel-body bk-info text-light">
                          <div class="stat-panel text-center">
                            <?php 
							$sql6 ="SELECT id from users";
							$query6 = $dbh -> prepare($sql6);;
							$query6->execute();
							$results6=$query6->fetchAll(PDO::FETCH_OBJ);
							$query=$query6->rowCount();
							?>
                            <div class="stat-panel-number h1 ">
                              <?php echo htmlentities($query);?>
                            </div>
                            <div class="stat-panel-title text-uppercase">Users
                            </div>
                          </div>
                        </div>
                        <a href="manage-user.php" class="block-anchor panel-footer text-center">Full Detail &nbsp; 
                          <i class="fa fa-arrow-right">
                          </i>
                        </a>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- Loading Scripts -->
    <script src="js/jquery.min.js">
    </script>
    <script src="js/bootstrap-select.min.js">
    </script>
    <script src="js/bootstrap.min.js">
    </script>
    <script src="js/jquery.dataTables.min.js">
    </script>
    <script src="js/dataTables.bootstrap.min.js">
    </script>
    <script src="js/Chart.min.js">
    </script>
    <script src="js/fileinput.js">
    </script>
    <script src="js/chartData.js">
    </script>
    <script src="js/main.js">
    </script>
  </body>
</html>
<?php } ?>