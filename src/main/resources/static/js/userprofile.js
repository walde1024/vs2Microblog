$('#followButton').click(function() {

    var that = this;
    var userEmail = $('#followButton').data("email");
    var followAction = $('#followButton').data("followButtonState");

    var follow = (followAction === "follow") ? true : false;

    $.ajax({
        url: "/userprofile/follow",
        method: "POST",
        data: {"userEmail": userEmail, "follow": follow},
        success: onFollowOrUnfollowSuccess,
        error: function(result) {console.log("Error");},
        context: that
    });
});

function onFollowOrUnfollowSuccess(result) {
    var followAction = $('#followButton').data("followbuttonstate");
    var followText = (followAction === "follow") ? "Nicht mehr folgen" : "Folgen";

    var followMeCount = parseInt($('#followMeCount').text());
    var followMeAdd = (followAction === "follow") ? 1 : -1;

    var oppositeAction = (followAction === "follow") ? "unfollow" : "follow";

    $('#followButton').text(followText);
    $('#followButton').data("followbuttonstate", oppositeAction);
    $('#followMeCount').text(followMeCount + followMeAdd);
}