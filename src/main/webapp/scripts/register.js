$(document).ready(function(){
	$("#province").change();
	$.formValidator.initConfig({theme:"126",submitOnce:true,formID:"regform",
		errorFocus:"true",
		submitAfterAjaxPrompt : '有数据正在异步验证，请稍等...'
	});
	
	$("#email").formValidator({onShowFixText:"请填写真实邮箱,用于激活您的账号.",onShow:"请输入邮箱",onFocus:"邮箱6-100个字符,输入正确了才能离开焦点",onCorrect:"邮箱格式正确",defaultValue:"@"}).inputValidator({min:6,max:100,onError:"你输入的邮箱长度非法,请确认"}).regexValidator({regExp:"email",dataType:"enum",onError:"你输入的邮箱格式不正确"})
	.ajaxValidator({
		type:"post",
		dataType : "json",
		async : true,
		url : "/user/check-email-exist",
		success : function(data){
           if(data.status) {
				return true;
			}else {
				return false;
			}
		},
		buttons: $("#regbutton"),
		error: function(jqXHR, textStatus, errorThrown){alert("服务器没有返回数据，可能服务器忙，请重试"+errorThrown);},
		onError : "该邮箱地址已被使用",
		onWait : "正在进行合法性校验,请稍候..."
	});
	$("#username").formValidator({onShowFixText:"6~20个字符，包括字母、数字、下划线，必须以字母开头",onShowText:"全站唯一,慎重填写",onShow:"请输入用户名",onCorrect:"该用户名可以注册"}).inputValidator({min:6,max:20,onError:"你输入的用户长度不正确,请确认"}).regexValidator({regExp:"username",dataType:"enum",onError:"用户名格式不正确"})
	.ajaxValidator({
		type:"post",
		dataType : "json",
		async : true,
		url : "/user/check-user-exist",
		success : function(data){
			if(data.status) {
				return true;
			}else {
				return false;
			}
		},
		buttons: $("#regbutton"),
		error: function(jqXHR, textStatus, errorThrown){alert("服务器没有返回数据，可能服务器忙，请重试"+errorThrown);},
		onError : "该用户名已被使用",
		onWait : "正在进行合法性校验,请稍候..."
	});
	
	$("#password").formValidator({onShowFixText:"6~16个字符，包括字母、数字、特殊符号，区分大小写",onShow:"请输入密码",onFocus:"至少1个长度",onCorrect:"密码合法"}).inputValidator({min:6,max:16,empty:{leftEmpty:false,rightEmpty:false,emptyError:"密码两边不能有空符号"},onError:"密码长度错误,请确认"}).passwordValidator({compareID:"username"});
	$("#repassword").formValidator({onShowFixText:"请再次输入密码",onShow:"输再次输入密码",onFocus:"至少1个长度",onCorrect:"密码一致"}).inputValidator({min:1,empty:{leftEmpty:false,rightEmpty:false,emptyError:"重复密码两边不能有空符号"},onError:"重复密码不能为空,请确认"}).compareValidator({desID:"password",operateor:"=",onError:"2次密码不一致,请确认"});
	
	$("#nickname").formValidator({onShowFixText:"1~16个字符",onShowText:"请输入昵称",onShow:"请输入昵称",onCorrect:"该昵称可以使用"}).inputValidator({min:1,max:16,onError:"你输入的昵称长度不正确,请确认"}).regexValidator({regExp:"nickname",dataType:"enum",onError:"昵称名格式不正确"})
	    .ajaxValidator({
	    type:"post",
		dataType : "json",
		async : true,
		url : "/user/check-nickname-exist",
		success : function(data){
			if(data.status) {
				return true;
			}else {
				return false;
			}
		},
		buttons: $("#regbutton"),
		error: function(jqXHR, textStatus, errorThrown){alert("服务器没有返回数据，可能服务器忙，请重试"+errorThrown);},
		onError : "该昵称已被使用",
		onWait : "正在进行合法性校验,请稍候..."
	});
	
	$("#province").formValidator({onShowFixText:"请选择您的居住区域!",onShow:"请选择您的居住区域",onFocus:"居住地必须选择!",onCorrect:"谢谢配合"}).inputValidator({min:1,onError:"请选择您的居住区域!"});
	$("#vcode").formValidator({onShow:"请输入验证码",onFocus:"至少4个长度",onCorrect:"验证码合法"}).inputValidator({min:4,max:5,onError:"验证码错误,请确认"}).passwordValidator({compareID:"username"})
		.ajaxValidator({
		type:"post",
		dataType : "json",
		async : true,	
		url : "/user/check-vcode",
		success : function(data){
			if(data.status) {
				return true;
			}
           return data.msg;
		},
		buttons: $("#regbutton"),
		error: function(jqXHR, textStatus, errorThrown){alert("服务器没有返回数据，可能服务器忙，请重试"+errorThrown);},
		onError : "验证码错误!",
		onWait : "正在进行合法性校验,请稍候..."
	});
});