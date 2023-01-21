$(document).ready(
		function() {

			// GET REQUEST
			$("#getAllBooks").click(function(event) {
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
							$.each(result.data,
									function(i, book) {
										var user = "Book Name: "
												+ book.bookName
												+ ", Author: " + book.author
												+ "<br>";
										$('#getResultDiv .list-group').append(
												user)
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