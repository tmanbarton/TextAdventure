$(document).ready(
		function() {
		    let gameStarted = false;
			$("#form").submit(function(event) {
				event.preventDefault();
				ajaxPost();
			});

			function ajaxPost() {
				var formData = {
					input: $("#user-input").val(),
					gameStarted: gameStarted
				}
				$.ajax({
					type : "POST",
					contentType : "application/json",
					url : "userInput",
					data : JSON.stringify(formData),
					dataType : 'json',
					success : function(result) {
						if (result.status == "success") {
						    $('#user-input').val('');
						    $('#caret')[0].style.transform = 'translateX(40px) translateY(-46px)';
						    let currentDisplay = $('#display').html();
							$('#display').html(currentDisplay + result.data.input + '<br>' + result.data.response + '<br>');
						} else {
							let currentDisplay = $('#display').html();
							$('#display').html(currentDisplay + 'Failed response from the server. Report to xyz.<br>');
						}
						gameStarted = true;
					},
					error : function(e) {
						console.log("ERROR: ", e);
					}
				});

			}

		})