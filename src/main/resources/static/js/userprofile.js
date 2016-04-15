$('#followButton').click(function() {

    var that = this;
    var userEmail = $('#followButton').data("email");
    var followAction = $('#followButton').data("followbuttonstate");

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
    updateButtonText();
    updateFollowMeCount();
    toggleFollowAction();
}

function updateButtonText() {
    var followAction = $('#followButton').data("followbuttonstate");
    var followText = (followAction === "follow") ? "Nicht mehr folgen" : "Folgen";
    $('#followButton').text(followText);
}

function updateFollowMeCount() {
    var followAction = $('#followButton').data("followbuttonstate");
    var followMeCount = parseInt($('#followMeCount').text());
    var followMeAdd = (followAction === "follow") ? 1 : -1;

    $('#followMeCount').text(followMeCount + followMeAdd);
}

function toggleFollowAction() {
    var followAction = $('#followButton').data("followbuttonstate");
    var oppositeAction = (followAction === "follow") ? "unfollow" : "follow";

    $('#followButton').data("followbuttonstate", oppositeAction);
}