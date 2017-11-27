//server init
var restify = require('restify');

var server = restify.createServer({ name: 'sudo' });
server.use(restify.bodyParser());

var port = process.env.ATM_PORT ||1337;

server.listen(port, function() {
  console.log('%s listening at %s', server.name, server.url);
});


//functions
function send(req, res, next) {
	res.writeHead(200, { 'Content-Type': 'text/plain' })
   	res.write('hello ' + req.params.name);
   	res.end();
 }

 function getCredit(req, res, next) {
 	res.writeHead(200, { 'Content-Type': 'application/javascript' });
 	res.write(
 		JSON.stringify({
 			'customer':req.params.id,
 			'credit': (Math.random() * 100000).toFixed(2)
 		})
 	);
 	res.end();
 }

 function withdraw(req, res, next){
 	res.status(418);
 	res.write('Not found');
 	res.end();
 }



//routes
  server.get('/hello/:name', send, send);

  server.get('/customer/:id/credit',getCredit);

  server.get('/customer/:id/withdraw', withdraw);

