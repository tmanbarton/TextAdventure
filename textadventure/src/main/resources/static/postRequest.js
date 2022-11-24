$(document).ready(
		function() {

			// SUBMIT FORM
			$("#form").submit(function(event) {
				// Prevent the form from submitting via the browser.
				event.preventDefault();
				ajaxPost();
			});

			function ajaxPost() {

				// PREPARE FORM DATA
				var formData = {
					input : $("#user-input").val()
				}

				// DO POST
				$.ajax({
					type : "POST",
					contentType : "application/json",
					url : "userInput",
					data : JSON.stringify(formData),
					dataType : 'json',
					success : function(result) {
					    console.log(result);
						if (result.status == "success") {
						    $('#user-input').val('')
						    $('#caret')[0].style.transform = 'translateX(40px) translateY(-46px)';
						    let currentDisplay = $('#display').html();
							$('#display').html(currentDisplay + result.data.input + '<br>' + result.data.response + '<br>');
						} else {
							let currentDisplay = $('#display').html();
							$('#display').html(currentDisplay + '<strong>Error</strong><br>')
						}
					},
					error : function(e) {
						alert("Error!")
						console.log("ERROR: ", e);
					}
				});

			}

		})