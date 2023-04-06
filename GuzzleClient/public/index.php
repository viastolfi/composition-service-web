<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Loan Approval</title>
</head>
<body>
    <form name="form" method="get" id="form">
        <input type="text" name="accountId" value="id du compte">
        <input type="text" name="somme" value="somme à crditer">
        <input type="submit" value="send request"/>
    </form> 

    <script>
        document.getElementById('#form').
    </script>
</body>
</html>

<?php
use GuzzleHttp\Psr7\Request;
require 'vendor/autoload.php';

$id = $_GET['accountId'];
$somme = $_GET['somme'];

echo($id.' '.$somme);

$client = new GuzzleHttp\Client(['base_uri' => 'http://localhost:8083/loan']);

//$response = $client->request('GET', '?id=');
//echo $response->getBody();
php?>