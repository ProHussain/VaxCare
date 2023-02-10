<?php
    require "conn.php";
    $name = $_POST["name"]; //within square bracket should be same as Utils.imageName & Utils.image
    $image = $_POST["image"];
    $email = $_POST["email"];
    $decodedImage = base64_decode("$image");
    $return = file_put_contents("/home2/hashmac/public_html/vxcare//uploads/".$email.".JPG", $decodedImage);
 
    if($return !== false){

        $query = "UPDATE users SET image = 'https://hashmac.com/vxcare//uploads/".$email.".JPG' WHERE email = '$email'";
        error_log("Update query: ".$query);
        if (mysqli_query($con,$query)) {
            $response = array(
                'message' => 'Image Upload Successful',
                'success' => true,
            );
        } else {
            $response = array(
                'message' => 'Image Upload Failed',
                'success' => false,
                );
        }
        mysqli_close($con);
        
    }else{
        $response = array(
            'message' => 'Image Upload Failed',
            'success' => false,
            );
    }
 
    echo json_encode($response);
?>