function transferData() {
    document.getElementById("here").innerHTML = "Hello User empId: " + localStorage.getItem("getEmp") + "  ";
}

var xhr = new XMLHttpRequest();
var url = "http://192.168.225.172:8090/employee/grantPermission";

function decline(){
    var empId = localStorage.getItem("getEmp");
        $('#commentBox').show();
        $('#hidebutton').hide();
}


function postrequest(accept) {
    var empId = localStorage.getItem("getEmp");
    xhr.open("POST", url, true);
    xhr.setRequestHeader("Content-type", "application/json");
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            var json = xhr.responseText;
            console.log(json);
            document.getElementById("demo").innerHTML = json
            document.getElementById("demo").style.fontSize = "25px";
            document.getElementById("demo").style.fontStyle = "sans-serif";
        }

        if (xhr.status === 400) {
            var error = "No such file Exists";
            console.log(error);
            document.getElementById("demo").innerHTML = error;
            document.getElementById("demo").style.fontSize = "25px";
            document.getElementById("demo").style.fontStyle = "sans-serif";
        }
    };
    var reason=document.getElementById("commentTextbox").value;
    var data = JSON.stringify({ "empId": empId, "accept": accept, "reason":reason });
    xhr.send(data);
}