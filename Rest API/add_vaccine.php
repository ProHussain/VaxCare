<?php
    require "conn.php";
    $name = $_POST['name'];
    $image = $_POST['image'];
    $description = $_POST['description'];

    $query = "INSERT INTO vaccines (name,image, description) VALUES ('$name','$image','$description')";
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