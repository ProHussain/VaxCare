<?php
    require "conn.php";
    $name = $_POST['name'];
    $email = $_POST['email'];
    $phone = $_POST['phone'];
    $password = $_POST['password'];

    $query = "INSERT INTO users (name, email, phone, password) VALUES ('$name', '$email', '$phone', '$password')";
    if (mysqli_query($con,$query)) {
        $response = array(
            'message' => 'Sign up successful',
            'success' => true,
        );
    } else {
        $response = array(
            'message' => 'Sign up failed',
            'success' => false,
            );
    }

    echo json_encode($response);
    mysqli_close($conn);
?>