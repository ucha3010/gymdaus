function validarCif(cif){
	if (!cif || cif.length !== 9) {
		return false;
	}

	var letters = ['J', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I'];
	var digits = cif.substr(1, cif.length - 2);
	var letter = cif.substr(0, 1);
	var control = cif.substr(cif.length - 1);
	var sum = 0;
	var i;
	var digit;

	if (!letter.match(/[A-Z]/)) {
		return false;
	}
	
	for (i = 0; i < digits.length; ++i) {
		digit = parseInt(digits[i]);

		if (isNaN(digit)) {
			return false;
		}

		if (i % 2 === 0) {
			digit *= 2;
			if (digit > 9) {
				digit = parseInt(digit / 10) + (digit % 10);
			}

			sum += digit;
		} else {
			sum += digit;
		}
	}

	sum %= 10;
	if (sum !== 0) {
		digit = 10 - sum;
	} else {
		digit = sum;
	}

	if (letter.match(/[ABEH]/)) {
		return String(digit) === control;
	}
	if (letter.match(/[NPQRSW]/)) {
		return letters[digit] === control;
	}

	return String(digit) === control || letters[digit] === control;
}

function validarEmail(email) {
	var regex = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	return regex.test(email) ? true : false;
}

function validarNifNie(nifnie) {
	  var validChars = 'TRWAGMYFPDXBNJZSQVHLCKET';
	  var nifRexp = /^[0-9]{8}[TRWAGMYFPDXBNJZSQVHLCKET]{1}$/i;
	  var nieRexp = /^[XYZ]{1}[0-9]{7}[TRWAGMYFPDXBNJZSQVHLCKET]{1}$/i;
	  var str = nifnie.toString().toUpperCase();

	  if (!nifRexp.test(str) && !nieRexp.test(str)) return false;

	  var nie = str
	    .replace(/^[X]/, '0')
	    .replace(/^[Y]/, '1')
	    .replace(/^[Z]/, '2');

	  var letter = str.substr(-1);
	  var charIndex = parseInt(nie.substr(0, 8)) % 23;

	  if (validChars.charAt(charIndex) === letter) return true;

	  return false;
	}

function validarDecimal(valor) {
    var RE = /^\d*\.?\d*$/;
    if (RE.test(valor)) {
        return true;
    } else {
        return false;
    }
}

function cambiarComaPorPunto(valor) {
	return valor.replace(",",".");
}

function getAge(dateString) {
    var today = new Date();
    var birthDate = new Date(dateString);
    var age = today.getFullYear() - birthDate.getFullYear();
    var m = today.getMonth() - birthDate.getMonth();
    if (m < 0 || (m === 0 && today.getDate() < birthDate.getDate())) {
        age--;
    }
    return age;
}

function mayorEdad(fechaNacimiento, edadLimite) {
    var edad = getAge(fechaNacimiento);
    if(edad >= edadLimite) {
        return true;
    } else {
        return false;
    }
}