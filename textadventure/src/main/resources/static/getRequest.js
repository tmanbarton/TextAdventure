$(document).ready(
		function() {

			// GET REQUEST
			$("#game-code-container").click(function(event) {
				event.preventDefault();
				ajaxGet();
			});

			// DO GET
			function ajaxGet() {
//			    $.ajax({
//                    type : "GET",
//                    contentType : "application/json",
//                    url : "api/getUsers",
//                    data : JSON.stringify(formData),
//                    dataType : 'json',
//                    success : function(result) {
//                        if (result.status == "success") {
//                            $('#getResultDiv ul').empty();
//                            $.each(result.data,
//                                    function(i, user) {
////                                        var user = "User Name: "
////                                                + book.bookName
////                                                + ", Author: " + book.author
////                                                + "<br>";
//                                        $('#getResultDiv .list-group').append(
//                                                user.username)
//                                    });
//
//                            console.log("Success: ", result);
//                        } else {
//                            let currentDisplay = $('#display').html();
//                            $('#display').html(currentDisplay + 'Failed response from the server. Report to xyz.<br>');
//                        }
//                    },
//                    error : function(e) {
//                        console.log("ERROR: ", e);
//                    }
//                });

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