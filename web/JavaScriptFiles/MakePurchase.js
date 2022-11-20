function makePurchase() {
    let req = new XMLHttpRequest();
    req.open("POST", "http://localhost:8080/sob-cryptocurrency-explorer/rest/api/v1/purchase?cryptocurrency=" + document.make_purchase_form.id.value, true);
    req.setRequestHeader("Content-Type", "application/json");
    req.setRequestHeader("Authorization", "Basic "+btoa(`${document.make_purchase_form.email.value}:${document.make_purchase_form.password.value}`) + " ==");
    let body =
    `{
        "purchasedAmount": ${document.make_purchase_form.purchasedAmount.value}
}`;
    console.log(req);
    req.onreadystatechange = function() {
        if (req.readyState == req.DONE) {
            let response = req.responseText;
            console.log(response);
        }
    };


    req.send(body);
}