var http = require('http');

var server = http.createServer(function (req, res) {
    
    if (req.method.toLowerCase() == 'get') {
        displayForm(req, res);
    } else if (req.method.toLowerCase() == 'post') {
        processPostRequest(req, res);
    }
});

function processPostRequest(req, res) {
    let data = [];
    req.on('data', (chunk) => {
        data.push(chunk);
    }).on('end', () => {
        data = JSON.parse(Buffer.concat(data).toString());
        
        if(req.url.indexOf("/signup") != -1)
            signup(req, res, data);
        else if(req.url.indexOf("/login")!= -1)
            login(req,res,data);    
        
    });
    return;
}