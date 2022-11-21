function createOrder() {
    let req = new XMLHttpRequest();
    req.open("POST", "http://localhost:8080/sob-cryptocurrency-explorer/rest/api/v1/order?cryptocurrency=" + document.make_purchase_form.id.value, true);
    req.setRequestHeader("Content-Type", "application/json");
    req.setRequestHeader("Authorization", "Basic "+btoa(`${document.make_purchase_form.email.value}:${document.make_purchase_form.password.value}`) + " ==");
    req.onreadystatechange = function() {
        if (req.readyState === req.DONE) {
            if (req.status >= 200 && req.status <= 299)
                window.alert("Order successful.\n\nstatus:\n" + req.status + "\n\nresponseText:\n" + req.responseText);
            else
                window.alert("Error\n\nstatus:\n" + req.status + "\n\nresponseText:\n" + req.responseText);
        }
    };
    let bodyAttributes = {
        purchasedAmount: new Number(document.make_purchase_form.purchasedAmount.value)
    };
    req.send(JSON.stringify(bodyAttributes));
}
