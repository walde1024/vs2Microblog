var TimelineController = {
    readFirstMessages: function() {
        var that = this;

        $.ajax({
          url: "/messages",
          data: {"timeline": "global", "fromMessage": "0", "toMessage": "14"},
          success: that.onReadFirstMessagesSuccess,
          error: that.onReadFirstMessagesError,
        });
    },

    onReadFirstMessagesSuccess: function(data) {
        console.log(data);
        for (var i = 0; i < data.length; i++) {
            $('#message-container').append("<p>" + data[i].body + "</p>")
        }
    },

    onReadFirstMessagesError: function(e) {
        console.log(e);
    }
}

TimelineController.readFirstMessages();