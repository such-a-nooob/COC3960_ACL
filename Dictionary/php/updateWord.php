<?php
    $conn = mysqli_connect("localhost","root","","dictionary")or die("Connection failed: " . mysqli_connect_error());

    $word = $_COOKIE['word'];
    $type = $_POST['type'];
    $def = $_POST['def'];
    $example = $_POST['example'];
    
    if($type!='' && $def!='') {
    $sql = "UPDATE `data` SET `type`='$type',`definition`='$def',`example`='$example' WHERE `word`='$word';";
    mysqli_query($conn, $sql);
    }
    mysqli_close($conn);
?>