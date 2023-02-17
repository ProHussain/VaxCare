<?php
  require "conn.php";
  $id = $_POST['id'];
  
  $query = "DELETE FROM vaccines WHERE id = '$id'";
  if (mysqli_query($con, $query)) {
    $response = array(
      'message' => 'Vaccines deleted successfully',
      'success' => true
    );
  } else {
    $response = array(
      'message' => 'Vaccines data not found'. mysqli_error($conn),
      'success' => false
    );
  }
  
  echo json_encode($response);
?>