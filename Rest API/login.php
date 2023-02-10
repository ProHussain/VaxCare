<?php
    require "conn.php";
    $email = $_POST['email'];
    $password = $_POST['password'];

    $query = "SELECT * FROM users WHERE email = '$email' AND password = '$password'";
    $result = mysqli_query($con, $query);

    if (mysqli_num_rows($result) > 0) {
        $response = array(
            'message' => 'Login successful',
            'success' => true,
        );
    } else {
        $response = array(
            'message' => 'email or password is incorrect',
            'success' => false,
        );
    }
    echo json_encode($response);
?>