<?php
  require "conn.php";
  
  $query = "SELECT id, name FROM appointments WHERE appointment_status = 'Completed'";
  $result = mysqli_query($con, $query);

  if (mysqli_num_rows($result) > 0) {
    $appointments = array();
    
    while ($row = mysqli_fetch_assoc($result)) {
        $appointments[] = $row;
    }

    $response = array(
      'message' => 'Requests retrieved successfully',
      'success' => true,
      'data' => $appointments
    );
  } else {
    $response = array(
      'message' => 'Requests data not found',
      'success' => false
    );
  }
  
  echo json_encode($response);
?>