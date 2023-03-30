<?php
use Psr\Http\Message\ResponseInterface as Response;
use Psr\Http\Message\ServerRequestInterface as Request;
use Slim\Factory\AppFactory;

$loader = require __DIR__ . '/../vendor/autoload.php';
//for namespace BL PSR4 here namespace ; dir
$loader->addPsr4('BL\\', __DIR__);
$app = AppFactory::create();

$app->get('/', function (Request $request, Response $response, $args) {
    $response->getBody()->write("Hello world!");
    return $response;
});

$app->get('/loan', function (Request $request, Response $response, $args) {
    $params = $request->getQueryParams();
    $id = $params['id'];
    $somme = $params['somme'];
    if($somme < 10000){
        $url = "http://localhost:8085/accounts/account/".$id;
        $data = file_get_contents($url);
        $json = (array) json_decode($data);
        if($json['risk'] == "Low"){
            $responseApp = "approved";
        }else{
            $url = "http://localhost:8081/approvals/approval/".$id;
            $data = file_get_contents($url);
            $json = (array) json_decode($data);
            $responseApp = $json['response'];
        }
    }else{
        $url = "http://localhost:8081/approvals/approval/".$id;
        $data = file_get_contents($url);
        $json = (array) json_decode($data);
        $responseApp = $json['response'];
    }

    if($responseApp == "approved"){
        $url = "http://localhost:8085/accounts/account/".$id."/".$somme;
        $ch = curl_init($url);
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
        curl_setopt($ch, CURLOPT_CUSTOMREQUEST, "PUT");
        $out = curl_exec($ch);
        curl_close($ch);
    }else{
        $out = json_encode("Vous ne pouvez pas faire ce crédit");
    }

    $response->getBody()->write($out);
    return $response->withHeader('Content-Type', 'application/json');
});

$app->run();