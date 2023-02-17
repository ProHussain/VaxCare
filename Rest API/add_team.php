<?php
    require "conn.php";
    $name = $_POST['name'];
    $email = $_POST['email'];
    $pass = $_POST['password'];


    $query = "INSERT INTO users (name,email,password,status) VALUES ('$name','$email','$pass','team')";
    if (mysqli_query($con,$query)) {
        $response = array(
            'message' => 'Worker added successful',
            'success' => true,
        );
    } else {
        $response = array(
            'message' => 'Worker Added failed',
            'success' => false,
            );
    }

    echo json_encode($response);
    mysqli_close($conn);
?>