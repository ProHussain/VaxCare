<?php
    require "conn.php";
    $name = $_POST['name'];
    $email = $_POST['email'];
    $phone = $_POST['phone'];
    $password = $_POST['password'];
    $status = $_POST['status'];

    $query = "INSERT INTO users (name, email, phone, password, status) VALUES ('$name', '$email', '$phone', '$password', '$status')";
    if (mysqli_query($con,$query)) {
        $user_id = mysqli_insert_id($con);
        $response = array(
            'message' => 'Registration successful',
            'success' => true,
            'id' => $user_id,
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