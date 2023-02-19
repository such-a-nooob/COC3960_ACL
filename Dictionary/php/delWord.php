<?php
    $conn = mysqli_connect("localhost","root","","dictionary")or die("Connection failed: " . mysqli_connect_error());

    $word = $_COOKIE['word'];
    
    $sql = "DELETE FROM `data` WHERE `word`='$word';";
    mysqli_query($conn, $sql);
    
    mysqli_close($conn);
?>