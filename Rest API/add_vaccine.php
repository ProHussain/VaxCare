<?php
    require "conn.php";
    $name = $_POST['name'];

    $query = "INSERT INTO vaccines (id,name) VALUES (NULL,'$name')";
    if (mysqli_query($con,$query)) {
        $response = array(
            'message' => 'Vaccine add successful',
            'success' => true,
        );
    } else {
        $response = array(
            'message' => 'Vaccine Add failed',
            'success' => false,
            );
    }

    echo json_encode($response);
    mysqli_close($conn);
?>