<?php
session_start();
error_reporting(0);
include('includes/config.php');
if(strlen($_SESSION['alogin'])==0)
{	
header('location:index.php');
}
else{ 
if(isset($_GET['edititem']))
{
$itemid=$_GET['edititem'];
}
if(isset($_POST['submit']))
{
$itemprice=$_POST['itemprice'];
$discount=$_POST['discount'];
$sql="UPDATE items SET price=(:itemprice),  discount=(:discount) WHERE id=(:editid)";
$query = $dbh->prepare($sql);
$query->bindParam(':itemprice',$itemprice,PDO::PARAM_STR);
$query->bindParam(':discount',$discount,PDO::PARAM_STR);
$query->bindParam(':editid',$itemid,PDO::PARAM_STR);
$query->execute();
$lastInsertId = $dbh->lastInsertId();
$msg="Price Updated successfully";
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
    <title>Update Items
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
    <link rel="icon" href="img/logo.png" type="image/gif" sizes="16x16">
    <!-- Admin Stye -->
    <link rel="stylesheet" href="css/style.css">
    <script type= "text/javascript" src="../vendor/countries.js">
    </script>
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
      <?php $sqltemp = "SELECT * from items where id = (:id)";
$querytemp = $dbh -> prepare($sqltemp);
$querytemp->bindParam(':id',$itemid,PDO::PARAM_STR);
$querytemp->execute();
$resulttemp=$querytemp->fetch(PDO::FETCH_OBJ);         
?>
      <div class="content-wrapper">
        <div class="container-fluid">
          <div class="row">
            <div class="col-md-12">
              <h2 class="page-title">
                <a href="manage-items.php">
                  <i class="glyphicon glyphicon-circle-arrow-left" style="color:#3b3b3b">
                  </i>
                </a>&nbsp; &nbsp; Update Price
              </h2>
              <div class="row">
                <div class="col-md-12">
                  <div class="panel panel-default">
                    <div class="panel-heading">Basic Info
                    </div>
                    <?php if($error){?>
                    <div class="errorWrap">
                      <?php echo htmlentities($error); ?> 
                    </div>
                    <?php } 
else if($msg){?>
                    <div class="succWrap">
                      <?php echo htmlentities($msg); ?> 
                    </div>
                    <?php }?>
                    <div class="panel-body">
                      <form method="post" class="form-horizontal">
                        <div class="form-group">
                          <label class="col-sm-2 control-label">Name
                          </label>
                          <div class="col-sm-4">
                            <input class="form-control" required readonly value="<?php echo htmlentities($resulttemp->name); ?>">
                          </div>
                          <label class="col-sm-2 control-label">Price
                            <span style="color:red">* 
                            </span>(Rs)
                          </label>
                          <div class="col-sm-4">
                            <input type="number" name="itemprice" class="form-control" required placeholder="Per kg or piece" value="<?php echo htmlentities($resulttemp->price); ?>">
                          </div>
                        </div>

                        <div class="form-group">
                        <label class="col-sm-2 control-label">Discount Price
                            <span style="color:red"> 
                            </span>(Rs)
                          </label>
                          <div class="col-sm-4">
                            <input type="number" name="discount" class="form-control"  placeholder="Per kg or piece" value="<?php echo htmlentities($resulttemp->discount); ?>">
                          </div>
                          <div class="col-sm-4 col-sm-offset-2">
                            <button class="btn btn-primary" name="submit" type="submit">Update Changes
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
