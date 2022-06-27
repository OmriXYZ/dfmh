/*$(document).ready(function() {
	$.ajax({
		url: "http://rest-service.guides.spring.io/greeting"
	}).then(function(data) {
	   $('.greeting-id').append("fwe");
	   $('.greeting-content').append(data.content);
	});
});*/

/*
$(document).ready(function(){
	$("#subBtn").click(function(){
		$("#myToast").toast("show");
	});
});*/

function cucucu() {
	$("#validation_toast").toast("show");
}

function errmsg() {
	$("#msg_toast").toast("show");
}

function regmsg() {
	$("#reg_toast").toast("show");
}

$('#subBtn').click(function(e) {
					e.preventDefault();
					e.stopPropagation();

				});


function checkLastDonation() {
	$.ajax({
		type: "GET",
		url: "/donations/check",
		data: {
		},
		success: function(data) {
			if (data == true) {
				$("#validation_toast").toast("show");
				/*				$(document).on('submit', '#donationform', function(event) {
									event.preventDefault();
									event.stopPropagation();
				
								});*/

				$('#donationform').submit(function(event) {
					if (data == true) {
						event.preventDefault();
						event.stopPropagation();
					}
				});

				

				return true;
			} else {

				/*if ($('#donationform').valid() == true) {
					document.getElementById("donationform").submit();
				}*/

				if ($('#donationform')[0].checkValidity()) {

					$("#validation_toast").toast("show");
				}

				/*alert( "Valid: " + $("#phonevalid").prop('required') );
							if ($("#donationform").valid) {
								$("#validation_toast").toast("show");
							}*/
				return false;
			}
		}
	});
}

