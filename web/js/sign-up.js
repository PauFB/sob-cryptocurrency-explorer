function signUpCustomer() {
    let req = new XMLHttpRequest();
    req.open("POST", "http://localhost:8080/sob-cryptocurrency-explorer/rest/api/v1/customer");
    req.setRequestHeader("Content-Type", "application/json");
    req.onreadystatechange = function() {
        if (req.readyState === XMLHttpRequest.DONE) {
            if (req.status >= 200 && req.status <= 299)
                window.alert("Sign up successful.\n\nstatus:\n" + req.status + "\n\nresponseText:\n" + req.responseText);
            else
                window.alert("Error\n\nstatus:\n" + req.status + "\n\nresponseText:\n" + req.responseText);
        }
    };
    let bodyAttributes = {
        email: new String(document.signup_customer_form.email.value),
        name: new String(document.signup_customer_form.name.value),
        password: new String(document.signup_customer_form.password.value),
        phone: new String(document.signup_customer_form.phone.value)
    };
    req.send(JSON.stringify(bodyAttributes));
}
