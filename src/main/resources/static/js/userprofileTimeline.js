var TimelineController = {
    readFirstMessages: function() {
        var that = this;
        var userEmail = $('#followButton').data("email");

        $.ajax({
          url: "/messages",
          data: {"timeline": "personal", "fromMessage": "0", "toMessage": "14", "userEmail": userEmail},
          success: jQuery.proxy(function(data) {that.showMessagesOnPage(data)}, that),
          error: that.onReadMessagesError,
        });
    },

    showMessagesOnPage: function(data) {
        for (var i = 0; i < data.length; i++) {

            var messageHtml =
            '<div class="panel panel-default message-panel">' +
                '<div class="panel-body">' +
                    '<div>' +
                        '<p class="message-author">' +
                            '<a href="/userprofile?userEmail=' + data[i].authorEmail + '">' + data[i].author + '</a>' +
                        '</p>' +
                        '<p class="message-time">' +
                            this.formatDate(data[i].dateTime) +
                        '</p>' +
                        '<p class="message-body">' +
                            data[i].body +
                        '</p>' +
                    '</div>' +
                '</div>' +
            '</div>'

            $('#message-container').append(messageHtml);
        }
    },

    onReadMessagesError: function(e) {
        console.log(e);
    },

    formatDate: function(millis) {

        var date = new Date(millis);

        var yyyy = date.getFullYear().toString();
        var mm = (date.getMonth()+1).toString(); // getMonth() is zero-based
        var dd  = date.getDate().toString();
        var hh = date.getHours().toString();
        var min = date.getMinutes().toString();

        return (dd[1]?dd:"0"+dd[0]) + '.' + (mm[1]?mm:"0"+mm[0]) + '.' + yyyy + ' ' + (hh[1]?hh:"0"+hh[0]) + ':' + (min[1]?min:"0"+min[0]); // padding
    },

    getUrlParameter: function(sParam) {
        var sPageURL = decodeURIComponent(window.location.search.substring(1)),
        sURLVariables = sPageURL.split('&'),
        sParameterName,
        i;

        for (i = 0; i < sURLVariables.length; i++) {
            sParameterName = sURLVariables[i].split('=');

            if (sParameterName[0] === sParam) {
                return sParameterName[1] === undefined ? true : sParameterName[1];
            }
        }
    },
}

TimelineController.readFirstMessages();