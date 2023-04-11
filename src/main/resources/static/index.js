document.getElementById("mySubmitButton").addEventListener('click', function() {
    const fileInput = document.querySelector("#myFileInput");
    const textArea = document.querySelector("#myTextArea");

    const formData = new FormData();
    formData.append("file", fileInput.files[0]);
    formData.append("text", textArea.value);


    if (fileInput.files[0] && textArea.value) {
        alert('Registration can only be done by adding or writing files.');
        return ;
    }

    if (textArea.value) {
        fetch('http://localhost:8080/employee', {
            method: 'POST', // 또는 'PUT'
            headers: {
                'Content-Type': isJSONString(textArea.value) ? 'application/json' : 'text/plain',
            },
            body: textArea.value,
        })
        .then((response) => {
            console.log(response)
        })
        .then((data) => {
            alert('registration success');
        })
        .catch((error) => {
            alert('registration fail : ' + error);
        });
    } else {
        fetch("http://localhost:8080/employee", {
            method: "POST",
            body: formData
        }).then(response => response.json())
        .then(data => {
            console.log(data);
        }).catch(error => {
            console.error(error);
        });
    }

});

function isJSONString(str) {
    try {
        JSON.parse(str);
    } catch (e) {
        return false;
    }
    return true;
}
