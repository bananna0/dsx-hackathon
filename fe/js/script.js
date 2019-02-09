function myFunc () {
    alert("Up yo ass");
};

function lockFunds(clientID, amount) {
    var url = CONFIG.serverUrl + "/lockFunds";
    var postData = {
        clientID: clientID,
        amount: amount
    };
    console.log(postData);
    $.post(url, postData, function (data, status) {
        console.log(data);
        console.log(status);
    })
}

function placeNewOrder(clientID, amount, rate, instrument, direction) {
    var url = CONFIG.serverUrl + "/order";
    var postData = {
        clientID: clientID,
        amount: amount,
        rate: rate,
        instrument: instrument,
        direction: direction
    };
    console.log(postData);
    $.post(url, postData, function (data, status) {
        console.log(data);
        console.log(status);
    })
}

function cancelOrder(clientID, orderID) {
    var url = CONFIG.serverUrl + "/cancelOrder";
    var postData = {
        clientID: clientID,
        orderID: orderID
    };
    console.log(postData);
    $.post(url, postData, function (data, status) {
        console.log(data);
        console.log(status);
    })
}
