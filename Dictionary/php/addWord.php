<?php
    $conn = mysqli_connect("localhost","root","","dictionary")or die("Connection failed: " . mysqli_connect_error());

    $word = $_POST['new-word'];
    $type = $_POST['type'];
    $def = $_POST['def'];
    $example = $_POST['example'];

    if($word!='' && $type!='' && $def!='') {
    $sql = "INSERT INTO `data`(`word`, `type`, `definition`, `example`) VALUES ('$word','$type','$def','$example')";
    mysqli_query($conn, $sql);
    }
    
    mysqli_close($conn);
?>