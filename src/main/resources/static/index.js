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

    function getResponse(response) {
        if (!response.ok) { // 응답 코드가 2xx가 아닌 경우
            return response.json().then(res => {throw new Error(res[0].detail);});
        }

        window.location.href = response.headers.get('Location');
    }

    if (textArea.value) {
        fetch('http://localhost:8080/api/employee', {
            method: 'POST', // 또는 'PUT'
            headers: {
                'Content-Type': isJSONString(textArea.value) ? 'application/json' : 'text/plain',
            },
            body: textArea.value,
        }).then(response => {
            return getResponse(response);
        }).catch(error => {
            alert(error);
        });
    } else {
        fetch("http://localhost:8080/api/employee", {
            method: "POST",
            body: formData
        }).then(response => {
            return getResponse(response);
        }).catch(error => {
            alert(error);
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
