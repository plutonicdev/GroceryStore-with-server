<?php

session_start(); 

$_SESSION = array();

/*
Author: Quintus Labs
Author URL: http://quintuslabs.com
date: 12/11/2019
Github URL: https://github.com/quintuslabs/GroceryStore-with-server/
*/

if (ini_get("session.use_cookies")) {

    $params = session_get_cookie_params();

    setcookie(session_name(), '', time() - 60*60,

        $params["path"], $params["domain"],

        $params["secure"], $params["httponly"]

    );

}

unset($_SESSION['login']);

session_destroy(); // destroy session

header("location:index.php"); 

?>



