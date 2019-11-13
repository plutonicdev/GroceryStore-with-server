# Grocery Store CMS PHP Restful API

Grocery Store CMS PHP Restful API is an online grocery shop. The project is developed by using PHP/MySQL/Slim Restful API. The project has powerful backend CMS to manage grocery shop online. it has features like add items, remove items, update price, manage orders etc. Restful API ready to embed in Application using JSON data.

### Features

- Powerful Dashboard
- Add , Manage Items
- Add , Manage Category
- Update Price
- View Orders (Confirmed, Preparing, On Way, Dilivered)
- Generate Bills
- Manage Customers
- App Token Authentication

| Screenshot | Screenshot |
| --------------------- | -------------------- |
| <img src="/sc/1.PNG"> | <img src="/sc/2.PNG"> |
| <img src="/sc/3.PNG">| <img src="/sc/4.PNG"> |

### Config

- Config Admin CMS. admin\includes\config.php and set your database server configurations.

```
<?php 
// DB credentials.
define('DB_HOST','localhost');
define('DB_USER','root');
define('DB_PASS','');
define('DB_NAME','grocery');
// Establish database connection.
try
{
$dbh = new PDO("mysql:host=".DB_HOST.";dbname=".DB_NAME,DB_USER, DB_PASS,array(PDO::MYSQL_ATTR_INIT_COMMAND => "SET NAMES 'utf8'"));
}
catch (PDOException $e)
{
exit("Error: " . $e->getMessage());
}
?>
```

- Config App API. app\config.php and set your database server configurations.

```
/* DATABASE CONFIGURATION */
define('DB_SERVER', 'localhost');
define('DB_USERNAME', 'root');
define('DB_PASSWORD', '');
define('DB_DATABASE', 'grocery');
define("BASE_URL", "http://localhost/app/");
define("SITE_KEY", 'yourSecretKey');
```

- Database file included in Repo. (healthykitchendb.sql)

### App API Requests

#### Get all Category :  
Link : yoursite.com/app/allcategory 

```
Request Body :
{ 
 "token":"app963" 
} 

Response: 
{ 
    "feedData": [ 
        { 
            "id": "9", 
            "categry": "Fruits" 
        }, 
        { 
            "id": "10", 
            "categry": "Juice" 
        }, 
        { 
            "id": "11", 
            "categry": "Vegetables" 
        }, 
        { 
            "id": "12", 
            "categry": "Salad" 
        } 
    ] 
} 
```
#### Get Items Click on Category :  

Link : yoursite.com/app/getlist  

```
Request Body : 
{ 
 "token":"app963", 
 "categoryname":"Fruits" 
}  

Response 
{ 
    "feedData": [ 
        { 
            "id": "2", 
            "name": "Kiwi", 
            "category": "Fruits", 
            "description": "no des", 
            "price": "630", 
            "image": "3.png", 
            "homepage": "YES" 
        }, 
        { 
            "id": "3", 
            "name": "Apple", 
            "category": "Fruits", 
            "description": "No Des", 
            "price": "110", 
            "image": "2.png", 
            "homepage": "YES" 
        } 
    ] 
} 

```
#### Save Customer Information :  
Link : yoursite.com/app/savecustomer 

```
Request Body : 
{ 
 "token":"app963", 
 "fname" : "Bhinderjit", 
 "lname" : "Singh", 
 "mobile" : "7307258973", 
 "area" : "Rayya", 
 "address" : "lohgarh" 
} 
Response 
{ 
    "success": { 
        "text": "Saved Sucessfully" 
    } 
}
```
#### Get Homepage Products :  
Link : yoursite.com/app/homepage 

```
Request Body : 
{ 
 "token":"app963" 
} 
Response 
{ 
    "feedData": [ 
        { 
            "id": "2", 
            "name": "Kiwi", 
            "category": "Fruits", 
            "description": "no des", 
            "price": "630", 
            "image": "3.png", 
            "homepage": "YES" 
        }, 
        { 
            "id": "3", 
            "name": "Apple", 
            "category": "Fruits", 
            "description": "No Des", 
            "price": "110", 
            "image": "2.png", 
            "homepage": "YES" 
        } 
    ] 
} 
```
#### Place Order :  
Link : yoursite.com/app/placeorder 

```
Request Body : 
{ 
 "token":"app963", 
 "fname":"bhinderjit", 
 "lname":"Singh", 
 "mobile":"9915248596", 
 "area":"Rayya", 
 "address":"Lohgarh", 
 "orderitems":[ 
  { 
   "itemname":"Apple", 
   "itemquantity":"6", 
   "itemprice":"50", 
   "itemtotal":"300" 
  }, 
  { 
   "itemname":"Lichi", 
   "itemquantity":"1", 
   "itemprice":"40", 
   "itemtotal":"40" 
  } 
  ] 
} 
 
Response 
{ 
    "success": { 
        "text": "Order Placed Sucessfully" 
    } 
} 
```

### Happy Coding...
### Happy Open Source..
