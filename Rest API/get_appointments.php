<?php
  require "conn.php";

  $email = $_GET['email'];
  
  $query = "SELECT * FROM appointments WHERE appointment_user = '$email' OR appointment_team = '$email'";
  $result = mysqli_query($con, $query);

  if (mysqli_num_rows($result) > 0) {
    $appointments = array();
    
    while ($row = mysqli_fetch_assoc($result)) {
        $appointments[] = $row;
    }

    $response = array(
      'message' => 'Appointments retrieved successfully',
      'success' => true,
      'data' => $appointments
    );
  } else {
    $response = array(
      'message' => 'Appointments data not found',
      'success' => false
    );
  }
  
  echo json_encode($response);
?>