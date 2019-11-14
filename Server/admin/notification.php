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
if(isset($_REQUEST['del']))
{
$delid=intval($_GET['del']);
$sqldel = "DELETE FROM notification WHERE id=:delid";
$querydel = $dbh->prepare($sqldel);
$querydel-> bindParam(':delid',$delid, PDO::PARAM_STR);
$querydel -> execute();
unset($sqldel);
$msg="Deleted Sucessfully";
}
if(isset($_POST['submit']))
{
$title=$_POST['title'];
$description=$_POST['desc'];
$sql="INSERT INTO notification(title,description) VALUES(:title,:description)";
$query = $dbh->prepare($sql);
$query->bindParam(':title',$title,PDO::PARAM_STR);
$query->bindParam(':description',$description,PDO::PARAM_STR);
$query->execute();
$lastInsertId = $dbh->lastInsertId();
if($lastInsertId)
{
$msg="Added Sucessfully";
}
else 
{
$error="Something went wrong. Please try again";
}
}
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
    <title>Notification
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
    <style>
      .errorWrap {
        padding: 10px;
        margin: 0 0 20px 0;
        background: #dd3d36;
        color:#fff;
        -webkit-box-shadow: 0 1px 1px 0 rgba(0,0,0,.1);
        box-shadow: 0 1px 1px 0 rgba(0,0,0,.1);
      }
      .succWrap{
        padding: 10px;
        margin: 0 0 20px 0;
        background: #5cb85c;
        color:#fff;
        -webkit-box-shadow: 0 1px 1px 0 rgba(0,0,0,.1);
        box-shadow: 0 1px 1px 0 rgba(0,0,0,.1);
      }
    </style>
  </head>
  <body>
    <?php include('includes/header.php');?>
    <div class="ts-main-content">
      <?php include('includes/leftbar.php');?>
      <div class="content-wrapper">
        <div class="container-fluid">
          <div class="row">
            <div class="col-md-12">
              <div class="row">
                <div class="col-md-12">
                  <div class="panel panel-default">
                    <div class="panel-heading">
                      <h4>Notification
                      </h4>
                    </div>
                    <div class="panel-body">
                      <form method="post" class="form-horizontal">
                        <?php if($error){?>
                        <div class="errorWrap">
                          <strong>ERROR
                          </strong>:
                          <?php echo htmlentities($error); ?> 
                        </div>
                        <?php } 
else if($msg){?>
                        <div class="succWrap">
                          <strong>SUCCESS
                          </strong>:
                          <?php echo htmlentities($msg); ?> 
                        </div>
                        <?php }?>
                        <div class="form-group">
                          <label class="col-sm-1 control-label">Title
                          </label>
                          <div class="col-sm-3">
                            <input type="text" class="form-control" name="title" required>
                          </div>
                          <label class="col-sm-1 control-label">Description
                          </label>
                          <div class="col-sm-5">
                            <input type="text" class="form-control" name="desc" required>
                          </div>
                          <div class="col-sm-2">
                            <button class="btn btn-primary" name="submit" type="submit">ADD
                            </button>
                          </div>
                        </div>
                      </form>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="panel panel-default">
            <div class="panel-heading">Notification List
            </div>
            <div class="panel-body">
              <table class="display table table-striped table-bordered table-hover" cellspacing="0" width="100%">
                <thead>
                  <tr>
                    <th>#
                    </th>
                    <th>Title
                    </th>
                    <th>Description
                    </th>
                    <th>Action
                    </th>
                  </tr>
                </thead>
                <tbody>
                  <?php $sql = "SELECT * from notification ORDER BY id DESC";
                      $query = $dbh -> prepare($sql);
                      $query->execute();
                      $results=$query->fetchAll(PDO::FETCH_OBJ);
                      $cnt=1;
                      if($query->rowCount() > 0)
                      {
                      foreach($results as $result)
                      {				?>	
                  <tr>
                    <td>
                      <?php echo htmlentities($cnt);?>
                    </td>
                    <td>
                      <?php echo htmlentities($result->title);?>
                    </td>
                    <td>
                      <?php echo htmlentities($result->description);?>
                    </td>
                    <td>
                      <a href="notification.php?del=<?php echo $result->id;?>" onclick="return confirm('Do you want to delete');">
                        <i class="fa fa-close" style="color:red">
                        </i>
                      </a>
                    </td>
                  </tr>
                  <?php $cnt=$cnt+1; }} ?>
                </tbody>
              </table>
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
    <script type="text/javascript">
      $(document).ready(function () {
        setTimeout(function() {
          $('.succWrap').slideUp("slow");
        }
                   , 3000);
      }
                       );
    </script>
  </body>
</html>
<?php } ?>
