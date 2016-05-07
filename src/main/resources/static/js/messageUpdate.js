MessageUpdateController = {

    init: function() {
        this.connect();

        document.getElementById("newMessagesButton").onclick = function () {
            location.href = "/?timeline=global";
        };
    },

    connect: function() {
        var that = this;
        var socket = new SockJS('/update');
        stompClient = Stomp.over(socket);

        stompClient.connect({}, function(frame) {

            console.log('Connected: ' + frame);

            stompClient.subscribe('/message/update', function(update){
                that.showMessageUpdateAvailable();
            });
        });
    },

    disconnect: function() {
        if (stompClient != null) {
            stompClient.disconnect();
        }
        setConnected(false);
        console.log("Disconnected");
    },

    showMessageUpdateAvailable: function() {
        document.getElementById("newMessagesButton").style.display = "block";
        var count = parseInt(document.getElementById("newMessagesCounter").innerHTML);
        document.getElementById("newMessagesCounter").innerHTML = count += 1;
    }
}

MessageUpdateController.init();
