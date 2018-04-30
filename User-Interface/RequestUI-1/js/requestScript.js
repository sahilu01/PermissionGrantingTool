// function postrequest()
// { 
//     var username=document.getElementById("rid").value;
//     var fileName=document.getElementById("name").value;
//     var label=document.getElementById("fileName").value;
//     $.ajax({
//         type:"POST",
//         contentType:'application/json',
//         url:"http://172.16.26.86:8090/employee/getPermission",
//         data:JSON.stringify({"username":username, "fileName":fileName,"label":label }),
//         success: function(){
//             alert("Details sent!!!");
//             console.log("done");
//               }
//     });
// }

// function postrequest()
// {

// var client=new Client();

// var args={
//  data:{"_id":id, "name":name,"fileName":filename },
//  headers: {"Content-Type":"application/json"}
// };

// client.post("http://172.16.26.86:8090/employee/getPermission", args, function (data, response) {
//     // parsed response body as js object 
//     console.log(data);
//     // raw response 
//     console.log(response);
// });

// // registering remote methods 
// client.registerMethod("postMethod", "http://192.168.0.100:8080/employee/getPermission", "POST");

// client.methods.postMethod(args, function (data, response) {
//     // parsed response body as js object 
//     console.log(data);
//     // raw response 
//     console.log(response);
// });
// }


var xhr = new XMLHttpRequest();
var url = "http://192.168.225.172:8090/employee/getPermission";
var jsonLength;
function postrequest() {
    document.getElementById("spinner").className = "loader";
    var userId = document.getElementById("id").value;
    var fileName = document.getElementById("fileName").value;
    var txt = "", x;
    xhr.open("POST", url, true);
    xhr.setRequestHeader("Content-type", "application/json");
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            var json = JSON.parse(xhr.responseText);
            console.log(json);
            jsonLength = json.length;
            document.getElementById("spinner").classList.remove('loader');
            document.getElementById("spinner").innerHTML = "You have to take " + jsonLength + " permissions"
            document.getElementById("spinner").style.fontSize = "25px";
            document.getElementById("spinner").style.fontStyle = "sans-serif";
        }

        if (xhr.status === 400) {
            document.getElementById("spinner").classList.remove('loader');
            var error = "No such File/User Exists";
            console.log(error);
            document.getElementById("spinner").innerHTML = error
            document.getElementById("spinner").style.fontSize = "25px";
            document.getElementById("spinner").style.fontStyle = "sans-serif";
        }
    };
    document.getElementById("submitButton").disabled = true;
    var data = JSON.stringify({ "userId": userId, "fileName": fileName });
    xhr.send(data);
}

var xhttp = new XMLHttpRequest();
var url1 = "http://192.168.225.172:8090/employee/getFile";
var file;
function getrequest() {
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            // Typical action to be performed when the document is ready:
            file = xhttp.responseText;
            localStorage.setItem("filePath", file);
            if (file === "Access Denied") {
                document.getElementById("reasonButton").innerHTML = "<button type='button' onclick='getReason();' class='btn btn-default btn-lg btn-success center-block'>View Reason</button>";
            }
            else {
                document.getElementById("message").innerText = "File ready for download click the icon"
                document.getElementById("message").style.fontSize = "25px";
                document.getElementById("message").style.fontStyle = "sans-serif";
                //document.getElementById("downloadButton").disabled = true;
                var stringToReplace = localStorage.getItem("filePath");
                var desired = stringToReplace.replace(/[^:/.\-/\w\s]/gi, '')
                //document.getElementById("DL").action = desired;
                document.getElementById("filePath").href = desired;
                console.log(desired);
            }
        }
    };
    xhttp.open("GET", url1, true);
    xhttp.send();
}

var xhttp = new XMLHttpRequest();
var url2 = "http://192.168.225.172:8090/employee/getPermissionResponse";
var json;
function myFunction() {
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            // Typical action to be performed when the document is ready:
            json = JSON.parse(xhttp.responseText)
            document.getElementById("permissions").innerText = json.length;
        }
        if (jsonLength === json.length) {
            getrequest();
            document.getElementById("downloadButton").disabled = false;
            document.getElementById("filePath").disabled = false;

        }
        else {
            document.getElementById("downloadButton").disabled = true;
            document.getElementById("filePath").disabled = true;

        }
    };
    xhttp.open("GET", url2, true);
    xhttp.send();

}
var xhttp = new XMLHttpRequest();
var url3 = "http://192.168.225.172:8090/employee/getReasons";
function getReason(){
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            // Typical action to be performed when the document is ready:
            json = JSON.parse(xhttp.responseText)
            document.getElementById("message").innerText='';
            for(var i=0;i<json.length;i++){
                var reason=json[i];
                var node=document.createElement("LI");
                var textnode=document.createTextNode(reason);
                node.appendChild(textnode);
                document.getElementById("message").appendChild(node);
            }
            
        }
    };
    xhttp.open("GET", url3, true);
    xhttp.send();
}
