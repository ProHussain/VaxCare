<?php
    require "conn.php";
    if ($_SERVER['REQUEST_METHOD'] == 'PUT') {

        $data = json_decode(file_get_contents("php://input"), true);
        $user = new User($data);
        
        $query = "UPDATE users SET name = '".$user->name."', phone = '".$user->phone."', dob = '".$user->dob."', address = '".$user->address."', image = '".$user->image."' WHERE email = '" .$user->email."'";
        error_log("Update query: ".$query);
        if (mysqli_query($con,$query)) {
            $response = array(
                'message' => 'Profile Update Successful',
                'success' => true,
            );
        } else {
            $response = array(
                'message' => 'Profile Update Failed',
                'success' => false,
                );
        }
        
    } else {
            $response = array(
                'message' => 'Invallid request',
                'success' => false,
                );
    }

    echo json_encode($response);
    mysqli_close($con);
    
    class User {
        public $name;
        public $email;
        public $phone;
        public $password;
        public $dob;
        public $address;
        public $image;
        public $status;

        function __construct($data) {
            $this->name = $data['name'];
            $this->email = $data['email'];
            $this->phone = $data['phone'];
            $this->password = $data['password'];
            $this->dob = $data['dob'];
            $this->address = $data['address'];       
            $this->image = $data['image'];       
            $this->status = $data['status'];
        }
    }
?>