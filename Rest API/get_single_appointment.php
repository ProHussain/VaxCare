<?php
  require "conn.php";

  $id = $_GET['id'];
  
  $query = "SELECT * FROM appointments WHERE id = '$id'";
  $result = mysqli_query($con, $query);

  if (mysqli_num_rows($result) > 0) {
    $appointment = mysqli_fetch_assoc($result);
    $response = array(
      'message' => 'Appointment data retrieved successfully',
      'success' => true,
      'data' => $appointment
    );
  } else {
    $response = array(
      'message' => 'Appointment data not found',
      'success' => false
    );
  }
  
  echo json_encode($response);
?>