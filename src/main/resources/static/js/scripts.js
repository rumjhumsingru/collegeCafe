/**
 * 
 */

function checkBillingAddress() {
	if($("#theSameAsShippingAddress").is(":checked")) {
		$(".billingAddress").prop("disabled", true);
	} else {
		$(".billingAddress").prop("disabled", false);
	}
}

function checkPasswordMatch() {
	var password = $("#txtNewPassword").val();
	var confirmPassword = $("#txtConfirmPassword").val();
	
	if(password == "" && confirmPassword =="") {
		$("#checkPasswordMatch").html("");
		$("#updateUserInfoButton").prop('disabled', false);
	} else {
		if(password != confirmPassword) {
			$("#checkPasswordMatch").html("Passwords do not match!");
			$("#updateUserInfoButton").prop('disabled', true);
		} else {
			$("#checkPasswordMatch").html("Passwords match");
			$("#updateUserInfoButton").prop('disabled', false);
		}
	}
}

setTimeout(function() { // start a delay
	  var fade = document.getElementById("fade"); // get required element
	  fade.style.opacity = 1; // set opacity for the element to 1
	  var timerId = setInterval(function() { // start interval loop
	    var opacity = fade.style.opacity; // get current opacity
	    if (opacity == 0) { // check if its 0 yet
	      clearInterval(timerId); // if so, exit from interval loop
	    } else {
	      fade.style.opacity = opacity - 0.05; // else remove 0.05 from opacity
	    }
	  }, 100); // run every 0.1 second
	}, 5000);


$(document).ready(function(){
	$(".cartItemQty").on('change', function(){
		var id=this.id;
		
		$('#update-item-'+id).css('display', 'inline-block');
	});
	$("#theSameAsShippingAddress").on('click', checkBillingAddress);
	$("#txtConfirmPassword").keyup(checkPasswordMatch);
	$("#txtNewPassword").keyup(checkPasswordMatch);
});

function validateAddress() {
	var x = document.forms["myForm"]["shippingAddressName"].value;
	if (x == "" || !((/^[a-zA-Z ]{2,30}$/).test(x))) {
		document.getElementById('address_validate').innerHTML = "Employee name is empty or invalid";

		var fade = document.getElementById("address_validate"); // get required element
		fade.style.opacity = 1; // set opacity for the element to 1
		var timerId = setInterval(function() { // start interval loop
			var opacity = fade.style.opacity; // get current opacity
			if (opacity == 0) { // check if its 0 yet
				clearInterval(timerId); // if so, exit from interval loop
			} else {
				fade.style.opacity = opacity - 0.05; // else remove 0.05 from opacity
			}
		}, 100); // run every 0.1 second

		return false;
	}
	var x = document.forms["myForm"]["shippingAddressStreet1"].value;
	if (x == "") {
		document.getElementById('address_validate').innerHTML = "Desk location is required";

		var fade = document.getElementById("address_validate"); // get required element
		fade.style.opacity = 1; // set opacity for the element to 1
		var timerId = setInterval(function() { // start interval loop
			var opacity = fade.style.opacity; // get current opacity
			if (opacity == 0) { // check if its 0 yet
				clearInterval(timerId); // if so, exit from interval loop
			} else {
				fade.style.opacity = opacity - 0.05; // else remove 0.05 from opacity
			}
		}, 100); // run every 0.1 second

		return false;
	}
	var x = document.forms["myForm"]["shippingAddressZipcode"].value;
	if (x == "" || !((/^[0-9]{5}$/).test(x))) {
		document.getElementById('address_validate').innerHTML = "Employee ID is empty or Invalid";

		var fade = document.getElementById("address_validate"); // get required element
		fade.style.opacity = 1; // set opacity for the element to 1
		var timerId = setInterval(function() { // start interval loop
			var opacity = fade.style.opacity; // get current opacity
			if (opacity == 0) { // check if its 0 yet
				clearInterval(timerId); // if so, exit from interval loop
			} else {
				fade.style.opacity = opacity - 0.05; // else remove 0.05 from opacity
			}
		}, 100); // run every 0.1 second

		return false;
	}
	var x = document.forms["myForm"]["shippingAddressCity"].value;
	if (x == "") {
		document.getElementById('address_validate').innerHTML = "Branch city is required";

		var fade = document.getElementById("address_validate"); // get required element
		fade.style.opacity = 1; // set opacity for the element to 1
		var timerId = setInterval(function() { // start interval loop
			var opacity = fade.style.opacity; // get current opacity
			if (opacity == 0) { // check if its 0 yet
				clearInterval(timerId); // if so, exit from interval loop
			} else {
				fade.style.opacity = opacity - 0.05; // else remove 0.05 from opacity
			}
		}, 100); // run every 0.1 second

		return false;
	}
	return true;
}
function validatePayment() {
	var x = document.forms["myForm"]["holderName"].value;
	if (x == "" || !((/^[a-zA-Z ]{3,30}$/).test(x))) {
		document.getElementById('payment_validate').innerHTML = "Card name empty or invalid";

		var fade = document.getElementById("payment_validate"); // get required element
		fade.style.opacity = 1; // set opacity for the element to 1
		var timerId = setInterval(function() { // start interval loop
			var opacity = fade.style.opacity; // get current opacity
			if (opacity == 0) { // check if its 0 yet
				clearInterval(timerId); // if so, exit from interval loop
			} else {
				fade.style.opacity = opacity - 0.05; // else remove 0.05 from opacity
			}
		}, 100); // run every 0.1 second
		return false;
	}
	var x = document.forms["myForm"]["cardNumber"].value;
	if (x == "" || !((/^[0-9]{13,16}$/).test(x))) {
		document.getElementById('payment_validate').innerHTML = "Valid Card number is required";

		var fade = document.getElementById("payment_validate"); // get required element
		fade.style.opacity = 1; // set opacity for the element to 1
		var timerId = setInterval(function() { // start interval loop
			var opacity = fade.style.opacity; // get current opacity
			if (opacity == 0) { // check if its 0 yet
				clearInterval(timerId); // if so, exit from interval loop
			} else {
				fade.style.opacity = opacity - 0.05; // else remove 0.05 from opacity
			}
		}, 100); // run every 0.1 second
		return false;
	}
	var x = document.forms["myForm"]["cvc"].value;
	if (x == "" || !((/^[0-9]{3}$/).test(x))) {
		document.getElementById('payment_validate').innerHTML = "3 digit CVV is required";

		var fade = document.getElementById("payment_validate"); // get required element
		fade.style.opacity = 1; // set opacity for the element to 1
		var timerId = setInterval(function() { // start interval loop
			var opacity = fade.style.opacity; // get current opacity
			if (opacity == 0) { // check if its 0 yet
				clearInterval(timerId); // if so, exit from interval loop
			} else {
				fade.style.opacity = opacity - 0.05; // else remove 0.05 from opacity
			}
		}, 100); // run every 0.1 second
		return false;
	}
	return true;
}

/*function openMenu(evt, menuName) {
	var i, x, tablinks;
	x = document.getElementsByClassName("menu");
	for (i = 0; i < x.length; i++) {
		x[i].style.display = "none";
	}
	tablinks = document.getElementsByClassName("tablink");
	for (i = 0; i < x.length; i++) {
		tablinks[i].className = tablinks[i].className.replace(
				" w3-red", "");
	}
	document.getElementById(menuName).style.display = "block";
	evt.currentTarget.firstElementChild.className += " w3-red";
}
document.getElementById("myLink").click();*/

