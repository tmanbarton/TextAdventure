$(document).ready(
		function() {
			$("#form").submit(function(event) {
				event.preventDefault();
				ajaxPost();
			});

			function ajaxPost() {
				var formData = {
					input: $("#user-input").val()
				}
				$.ajax({
					type : "POST",
					contentType : "application/json",
					url : "/api/vots/game/gameRequest",
					data : JSON.stringify(formData),
					dataType : 'json',
					success : function(result) {
						if (result.status == "success") {
						    $('#user-input').val('');
						    $('#caret')[0].style.transform = 'translateX(46px) translateY(-46px)';
						    let currentDisplay = $('#display').html();
						    let response = result.data.response.replaceAll(/(?:\r\n|\r|\n)/g, '<br>');
							$('#display').html(currentDisplay + '<p><span style="padding-right:10px;">~</span>' + result.data.input + '</p>' + response);
						} else {
							let currentDisplay = $('#display').html();
							$('#display').html(currentDisplay + 'Failed response from the server. Report to xyzzy.<br>');
						}
					},
					error : function(e) {
						console.log("ERROR: ", e);
					}
				});

			}

		})