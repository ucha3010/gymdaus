function getDomain(fullUrl) {
    const myArray = fullUrl.split("/");
    let textOut = "";
    for (let i = 0; i < 3; i++) {
        textOut += myArray[i] + "/";
    }
    return textOut;
}

function buttonBlock(button) {
    button.disabled = true;
    button.style.opacity = 0.7;
    button.textContent = 'Procesando...';
}

function nullOrEmpty(myStr){
    return (myStr === null || myStr.trim() === "");
}

function logout() {
    document.getElementById("formLogout").submit();
}