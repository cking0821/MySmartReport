function setCookie() {
//	if($('#rememberMe')[0].checked) {//default expiration time: 7 days
//		$.cookie('hiUserName',$("input[name='userName']").val(), {expires: 7});
//	}
}

/**
 * des加密
 * @param message 待加密消息
 * @param key 加密盐
 * @returns des加密后的字符串
 */
function encryptByDES(message, key) {
    var keyHex = CryptoJS.enc.Utf8.parse(key);
    var encrypted = CryptoJS.DES.encrypt(message, keyHex, {
        mode: CryptoJS.mode.ECB,
        padding: CryptoJS.pad.Pkcs7
    });
    return encrypted.toString();
}

function login() {	
	$.ajax({
		type : 'POST',
		url : $("#basePath").val() + "randomSalt",
		cache : false,
		async : false,
		success : function(salt) {
			console.log(["salt", salt]);
			var passd = $("#password").val();
			var encyptPass = encryptByDES(passd, salt);
			$("#encryptedPwd").val(encyptPass);			
			$("#loginForm").submit();
		},
		error : function(message) {

		}
	});

}