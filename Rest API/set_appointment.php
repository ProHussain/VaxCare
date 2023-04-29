<?php
require "conn.php";
if ($_SERVER['REQUEST_METHOD'] == 'PUT') {

    $data = json_decode(file_get_contents("php://input"), true);
    $appointment = new Appointment($data);

    $checkQuery = "SELECT * FROM appointments WHERE name = '$appointment->vaccine_name' AND appointment_user = '$appointment->appointment_user' AND appointment_status = 'pending'";
    $result = mysqli_query($con, $checkQuery);
    if (mysqli_num_rows($result) > 0) {
        $response = array(
            'message' => 'Appointment already exists',
            'success' => false,
        );
    } else {

        $query = "INSERT INTO appointments (name, appointment_date, appointment_time, appointment_user, appointment_team, appointment_status) 
            VALUES ('$appointment->vaccine_name', '$appointment->appointment_date', '$appointment->appointment_time', '$appointment->appointment_user',
            '$appointment->appointment_team','$appointment->appointment_status')";

        error_log("Update query: " . $query);
        if (mysqli_query($con, $query)) {
            $response = array(
                'message' => 'Request submit Successful',
                'success' => true,
            );
        } else {
            $response = array(
                'message' => 'Request submit Failed',
                'success' => false,
            );
        }
    }
} else {
    $response = array(
        'message' => 'Invallid request',
        'success' => false,
    );
}

echo json_encode($response);
mysqli_close($con);

class Appointment
{
    public $id;
    public $vaccine_name;
    public $appointment_date;
    public $appointment_time;
    public $appointment_user;
    public $appointment_team;
    public $appointment_status;

    function __construct($data)
    {
        $this->appointment_id = $data['id'];
        $this->vaccine_name = $data['name'];
        $this->appointment_date = $data['appointment_date'];
        $this->appointment_time = $data['appointment_time'];
        $this->appointment_user = $data['appointment_user'];
        $this->appointment_team = $data['appointment_team'];
        $this->appointment_status = $data['appointment_status'];
    }
}
