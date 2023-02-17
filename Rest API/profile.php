<?php
  require "conn.php";

  $id = $_GET['id'];
  
  $query = "SELECT * FROM users WHERE id = '$id'";
  $result = mysqli_query($con, $query);

  if (mysqli_num_rows($result) > 0) {
    $user = mysqli_fetch_assoc($result);
    $response = array(
      'message' => 'User profile data retrieved successfully',
      'success' => true,
      'data' => $user
    );
  } else {
    $response = array(
      'message' => 'User profile data not found',
      'success' => false
    );
  }
  
  echo json_encode($response);
?>