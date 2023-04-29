<?php
  require "conn.php";
  
  $query = "SELECT * FROM users WHERE status = 'user'";
  $result = mysqli_query($con, $query);

  if (mysqli_num_rows($result) > 0) {
    $users = array();
    
    while ($row = mysqli_fetch_assoc($result)) {
        $users[] = $row;
    }

    $response = array(
      'message' => 'Teams retrieved successfully',
      'success' => true,
      'data' => $users
    );
  } else {
    $response = array(
      'message' => 'Teams data not found',
      'success' => false
    );
  }
  
  echo json_encode($response);
?>