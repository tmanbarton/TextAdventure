$(document).ready(
		function() {

			// GET REQUEST
			$("#game-code-container").click(function(event) {
				event.preventDefault();
				ajaxGet();
			});

			// DO GET
			function ajaxGet() {

				$.ajax({
					type : "GET",
					url : "api/getUsers",
					success : function(result) {
						if (result.status == "success") {
							$('#getResultDiv ul').empty();
							$.each(result.data.user,
									function(user, userInfo) {
									    console.log(user + " : " + userInfo);
									    console.log("NAME: " + userInfo.username);
									    console.log("USER ID: " + userInfo.userId);
									    console.log();
									    $('#getResultDiv ul').append('<li>' +
                                            userInfo.username + '</li>');
									});
							console.log("Success: ", result);
						} else {
							$("#getResultDiv").html("<strong>Error</strong>");
							console.log("Fail: ", result);
						}
					},
					error : function(e) {
						$("#getResultDiv").html("<strong>Error</strong>");
						console.log("ERROR: ", e);
					}
				});
			}
		})