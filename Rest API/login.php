<?php
    require "conn.php";
    $email = $_POST['email'];
    $password = $_POST['password'];
    $status = $_POST['status'];

    $query = "SELECT * FROM users WHERE email = '$email' AND password = '$password' AND status = '$status'";
    $result = mysqli_query($con, $query);

    if (mysqli_num_rows($result) > 0) {
        $row = mysqli_fetch_assoc($result);
            $response = array(
                'message' => 'Login successful',
                'success' => true,
                'id' => $row['id'],
            );
    } else {
        $response = array(
            'message' => 'email or password is incorrect',
            'success' => false,
        );
    }
    echo json_encode($response);
?>