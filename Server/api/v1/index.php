<?php
require 'config.php';
require 'Slim/Slim.php';

\Slim\Slim::registerAutoloader();
$app = new \Slim\Slim();

$app->post('/login','login');
$app->post('/register','register');
$app->post('/homepage','homepage');
$app->post('/allcategory','allcategory'); 
$app->post('/getlist','getlist');
$app->post('/newProduct','newProduct');
$app->post('/placeorder','placeorder');
$app->post('/orderDetails','getOrders');
$app->post('/updateUser','updateUser');

$app->run();

/*
Author: Quintus Labs
Author URL: http://quintuslabs.com
date: 12/11/2019
Github URL: https://github.com/quintuslabs/GroceryStore-with-server/
*/

/************************* USER LOGIN *************************************/
/* ### User login ### */
function login() {
    
    $request = \Slim\Slim::getInstance()->request();
    $data = json_decode($request->getBody());
    
    try {
        
        $db = getDB();
        $userData ='';
        $sql = "SELECT * FROM users WHERE mobile=:mobile and password = :password";
        $stmt = $db->prepare($sql);
        $stmt->bindParam("mobile", $data->mobile,PDO::PARAM_STR);
        $password=hash('sha256',$data->password);
        $stmt->bindParam("password", $password,PDO::PARAM_STR);
        $stmt->execute();
        $mainCount=$stmt->rowCount();
        $userData = $stmt->fetch(PDO::FETCH_OBJ);
        
        if(!empty($userData))
        {
            $id=$userData->id;
            $userData->token = apiToken($id);
        }
        
        $db = null;
         if($userData){
               $userData = json_encode($userData);
                echo '{"code": 200,"status": "Login Successfull !!","userData": ' .$userData . '}';
            } else {
               echo '{"code": 400,"status": "Bad request wrong credential"}';
            }

           
    }
    catch(PDOException $e) {
        echo '{"code": 500,"status": '. $e->getMessage() .'}';
    }
}

/* ### User registration ### */
function register() {
    $request = \Slim\Slim::getInstance()->request();
    $data = json_decode($request->getBody());
    $fname=$data->fname;
    $lname=$data->lname;
    $mobile=$data->mobile;
    $password=$data->password;
    
    try {
        
        $mobile_check = preg_match('~^[A-Za-z0-9_]{3,20}$~i', $mobile);
        $password_check = preg_match('~^[A-Za-z0-9!@#$%^&*()_]{6,20}$~i', $password);
        
        
        if (strlen(trim($mobile))>0 && strlen(trim($password))>0 && $mobile_check>0 && $password_check>0)
        {
            $db = getDB();
            $userData = '';
            $sql = "SELECT id FROM users WHERE mobile=:mobile";
            $stmt = $db->prepare($sql);
            $stmt->bindParam("mobile", $mobile,PDO::PARAM_STR);
            $stmt->execute();
            $mainCount=$stmt->rowCount();
            $created=time();
            if($mainCount==0)
            {
                
                /*Inserting user values*/
                $sql1="INSERT INTO users(mobile,password,fname,lname)VALUES(:mobile,:password,:fname,:lname)";
                $stmt1 = $db->prepare($sql1);
                $stmt1->bindParam("mobile", $mobile,PDO::PARAM_STR);
                $password=hash('sha256',$data->password);
                $stmt1->bindParam("password", $password,PDO::PARAM_STR);
                $stmt1->bindParam("fname", $fname,PDO::PARAM_STR);
                $stmt1->bindParam("lname", $lname,PDO::PARAM_STR);
                $stmt1->execute();
                
                $userData=internalUserDetails($mobile);
                if($userData){
                    $userData = json_encode($userData);
                     echo '{"code": 201,"status": "Registeration Successfull !!","userData":' .$userData . '}';
                 } else {
                    echo '{"code": 400,"status": "Some error in register"}';
                 }
                
            }else {
                echo '{"code": 403,"status": "User Already Exist"}';
             }
            
            $db = null;

           
        }
        else{
            echo '{"code": 400,"status": "Invalid data provided"}';
        }
    }
    catch(PDOException $e) {
        echo '{"code": 500,"status": "Fail"}';
    }
}

//Update User

/* ### User registration ### */
function updateUser() {
    $request = \Slim\Slim::getInstance()->request();
    $data = json_decode($request->getBody());
    $token=$data->token; 
    $id=$data->id; 
    $email=$data->email;
    $address=$data->address;
    $state=$data->state;
    $city=$data->city;
    $zip=$data->zip;

    $systemToken=apiToken(); // server token
    try {     
        if($systemToken == $token){
            $db = getDB();
            $userAddress = '';
            $sql = "UPDATE users SET name=:name, surname=:surname, sex=:sex WHERE id=:id";
                /*Inserting user values*/
                $sql1="UPDATE users SET email = :email, address = :address, state = :state, city = :city, zip = :zip WHERE id=:id ";
                $stmt1 = $db->prepare($sql1);
                $stmt1->bindParam("email", $email,PDO::PARAM_STR);
                $stmt1->bindParam("address", $address,PDO::PARAM_STR);
                $stmt1->bindParam("state", $state,PDO::PARAM_STR);
                $stmt1->bindParam("city", $city,PDO::PARAM_STR);
                $stmt1->bindParam("zip", $zip,PDO::PARAM_STR);
                $stmt1->bindParam("id", $id,PDO::PARAM_STR);             
                
                if( $stmt1->execute()){
                     echo '{"code": 200,"status": "User Updated Successfull !!"}';
                 } else {
                    echo '{"code": 400,"status": "Some error in register"}';
                 }
                
            
            $db = null;

           
        }
        else{
            echo '{"code": 401,"status": "UnAuthorised"}';
        }
    }
    catch(PDOException $e) {
        echo '{"code": 500,"status": "Fail"}';
    }
}


/* ### internal mobile Details ### */
function internalUserDetails($input) {
    
    try {
        $db = getDB();
        $sql = "SELECT * FROM users WHERE mobile=:input";
        $stmt = $db->prepare($sql);
        $stmt->bindParam("input", $input,PDO::PARAM_STR);
        $stmt->execute();
        $mobileDetails = $stmt->fetch(PDO::FETCH_OBJ);
        $mobileDetails->token = apiToken($mobileDetails->id);
        $db = null;
        return $mobileDetails;
        
    } catch(PDOException $e) {
        echo '{"error":{"text":'. $e->getMessage() .'}}';
    }
    
}

// Get All Category List

function allcategory(){
    $request = \Slim\Slim::getInstance()->request();
    $data = json_decode($request->getBody());
    $token=$data->token; //get app token
    $systemToken=apiToken(); // server token
    try {     
        if($systemToken == $token){
            $feedData = '';
            $db = getDB();
            $sql = "SELECT * FROM category";
            $stmt = $db->prepare($sql);
            $stmt->execute();
            $feedData = $stmt->fetchAll(PDO::FETCH_OBJ);    
            $db = null;
            echo '{"code": 200,"status": "success","categories": ' . json_encode($feedData) . '}';
        } else{
            echo '{"code": 401,"status": "UnAuthorised"}';
        }
       
    } catch(PDOException $e) {
        echo '{"code": 500,"status": '. $e->getMessage() .'}';
    }    
}

// Get All Category List 

function getlist(){
    $request = \Slim\Slim::getInstance()->request();
    $data = json_decode($request->getBody());
    $token=$data->token; //get app token
    $catgoryname=$data->categry; //get categoryid
    $systemToken=apiToken(); // server token
    try {     
        if($systemToken == $token){
            $feedData = '';
            $db = getDB();
            $sql = "SELECT * FROM items WHERE category = :categoryname";
            $stmt = $db->prepare($sql);
            $stmt->bindParam("categoryname", $catgoryname,PDO::PARAM_STR);
            $stmt->execute();
            $feedData = $stmt->fetchAll(PDO::FETCH_OBJ);    
            $db = null;
            echo '{"code": 200,"status": "success", "products": ' . json_encode($feedData) . '}';
        } else{
            echo '{"code": 401,"status": "UnAuthorised", }';
        }
       
    } catch(PDOException $e) {
        echo '{"error":{"text":'. $e->getMessage() .'}}';
    }    
}

//New Product
function newProduct(){
    $request = \Slim\Slim::getInstance()->request();
    $data = json_decode($request->getBody());
    $token=$data->token; //get app token
    $catgoryname=$data->categoryname; //get categoryid
    $systemToken=apiToken(); // server token
    try {     
        if($systemToken == $token){
            $feedData = '';
            $db = getDB();
            $sql = "SELECT * FROM items ORDER BY id DESC LIMIT 10";
            $stmt = $db->prepare($sql);
            $stmt->execute();
            $feedData = $stmt->fetchAll(PDO::FETCH_OBJ);    
            $db = null;
            echo '{"code": 200,"status": "Success", "products": ' . json_encode($feedData) . '}';
        } else{
            echo '{"code": 401,"status": "UnAuthorised",}';
        }
       
    } catch(PDOException $e) {
        echo '{"error":{"text":'. $e->getMessage() .'}}';
    }    
}

// Save Customer Data


// Get Homepage Products

function homepage() {
    $request = \Slim\Slim::getInstance()->request();
    $data = json_decode($request->getBody());
    $token=$data->token;
    $systemToken=apiToken();
    $homepage = "YES";
    try {
         
        if($systemToken == $token){
            $feedData = '';
            $db = getDB();
            $sql1="SELECT * FROM items WHERE homepage = :homepage";
            $stmt1 = $db->prepare($sql1);
            $stmt1->bindParam("homepage", $homepage,PDO::PARAM_STR);
            $stmt1->execute();
            $feedData = $stmt1->fetchAll(PDO::FETCH_OBJ);    
            $db = null;
            echo '{"code": 200,"status": "Success", "products": ' . json_encode($feedData) . '}';
        } else{
            echo '{"code": 401,"status": "UnAuthorised"}';
        }
       
    } catch(PDOException $e) {
        echo '{"code": 500,"status":'. $e->getMessage() .'}';
    }
}

// Place Order

function placeorder() {
    $request = \Slim\Slim::getInstance()->request();
    $data = json_decode($request->getBody());

    $token=$data->token;
    $fname=$data->fname;
    $lname=$data->lname;
    $mobile=$data->mobile;
    $status="Pending";
    $area=$data->area;
    $address=$data->address;
    $user_id=$data->user_id;

    $orderitems=$data->orderitems;

    $systemToken=apiToken();
    try {
         
        if($systemToken == $token){
            $db = getDB();
            $total = totalPrice($orderitems, 'itemtotal');
            
            $sqlorder = "INSERT INTO orders(fname, lname, area, address, mobile,user_id,total,status)VALUES(:fname,:lname,:area,:address,:mobile,:user_id,:total,:status)";
            $stmtorder = $db->prepare($sqlorder);
            $stmtorder->bindParam("fname", $fname,PDO::PARAM_STR);
            $stmtorder->bindParam("lname", $lname,PDO::PARAM_STR);
            $stmtorder->bindParam("mobile", $mobile,PDO::PARAM_STR);
            $stmtorder->bindParam("area", $area,PDO::PARAM_STR);
            $stmtorder->bindParam("address", $address,PDO::PARAM_STR);
            $stmtorder->bindParam("user_id", $user_id,PDO::PARAM_STR);
            $stmtorder->bindParam("total", $total,PDO::PARAM_STR);
            $stmtorder->bindParam("status", $status,PDO::PARAM_STR);
            $stmtorder->execute();
            $lastid = $db->lastInsertId();
            $totalPrice=0;

            foreach ($orderitems as $orderitem) {

                $itemname = $orderitem->itemname;
                $itemquantity = $orderitem->itemquantity;
                $attribute = $orderitem->attribute;
                $currency = $orderitem->currency;
                $itemImage = $orderitem->itemImage;
                $itemprice = $orderitem->itemprice;
                $itemtotal = $orderitem->itemtotal;
                $totalPrice = $totalPrice+$itemtotal;

                $sqlitem = "INSERT INTO orderslist (orderid,itemname,itemquantity,attribute,currency,itemImage,itemprice,itemtotal) VALUES (:orderid,:itemname,:itemquantity,:attribute,:currency,:itemImage,:itemprice,:itemtotal)";   
                $stmtitem = $db->prepare($sqlitem);
                $stmtitem->bindParam("orderid", $lastid, PDO::PARAM_STR);
                $stmtitem->bindParam("itemname", $itemname, PDO::PARAM_STR);
                $stmtitem->bindParam("itemquantity", $itemquantity, PDO::PARAM_STR);
                $stmtitem->bindParam("attribute",$attribute, PDO::PARAM_STR);
                $stmtitem->bindParam("currency", $currency, PDO::PARAM_STR);
                $stmtitem->bindParam("itemImage", $itemImage, PDO::PARAM_STR);
                $stmtitem->bindParam("itemprice", $itemprice, PDO::PARAM_STR);
                $stmtitem->bindParam("itemtotal", $itemtotal, PDO::PARAM_STR);
                $stmtitem->execute();
          }
         
        

            $db = null;
            echo '{"code": 200,"status": "Success"}';
        } else{
            echo '{"code": 401,"status": "UnAuthorised"}';
        }
       
    } catch(PDOException $e) {
        echo '{"code": 500,"status": '. $e->getMessage() .'}';
    }
}

function getOrders() {
    $request = \Slim\Slim::getInstance()->request();
    $data = json_decode($request->getBody());

    $token=$data->token;
    $user_id=$data->user_id;
    $systemToken=apiToken();
    try {     
        if($systemToken == $token){
            $feedData = '';
            $db = getDB();
            $sql = "SELECT id,status,user_id,date,total FROM orders WHERE user_id=".$user_id;
            $stmt = $db->prepare($sql);
            $stmt->execute();
            $feedData = $stmt->fetchAll(PDO::FETCH_OBJ);    
            $db = null;
            echo '{"code": 200,"status": "Success","orders": ' . json_encode($feedData) . '}';
        } else{
            echo '{"code": 401,"status": "UnAuthorised"}';
        }
       
    } catch(PDOException $e) {
        echo '{"error":{"text":'. $e->getMessage() .'}}';
    }    
    


}

/* ### internal mobile Details ### */
function internalContactDetails($input) {
    
    try {
        $db = getDB();
        $sql = "SELECT id, fname, lname, mobile FROM users WHERE mobile=:input";
        $stmt = $db->prepare($sql);
        $stmt->bindParam("input", $input,PDO::PARAM_STR);
        $stmt->execute();
        $mobileDetails = $stmt->fetch(PDO::FETCH_OBJ);
        $mobileDetails->token = apiToken($mobileDetails->id);
        $db = null;
        return $mobileDetails;
        
    } catch(PDOException $e) {
        echo '{"error":{"text":'. $e->getMessage() .'}}';
    }
    
}


function totalPrice(array $arr, $property) {

    $sum = 0;

    foreach($arr as $object) {
        $sum += isset($object->{$property}) ? $object->{$property} : 0;
    }

    return $sum;
}

?>