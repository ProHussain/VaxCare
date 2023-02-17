<?php
  require "conn.php";
  
  $query = "SELECT * FROM vaccines";
  $result = mysqli_query($con, $query);

  if (mysqli_num_rows($result) > 0) {
    $vaccines = array();
    
    while ($row = mysqli_fetch_assoc($result)) {
        $vaccines[] = $row;
    }

    $response = array(
      'message' => 'Vaccines retrieved successfully',
      'success' => true,
      'data' => $vaccines
    );
  } else {
    $response = array(
      'message' => 'Vaccines data not found',
      'success' => false
    );
  }
  
  echo json_encode($response);
?>