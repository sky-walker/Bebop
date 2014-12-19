/**
 * ajax提交数据
 */
function ajax_post_json(url,params,success_callback){
	$.ajax({
		type	: 'POST',
		cache	: false,
		url		: url,
		dataType: "json",
		data	: params,
		success	: success_callback,
		error	: function(html){
			alert("提交数据失败，代码:" +html.status+ "，请稍候再试");
		}
	});
}

function ajax_post_html(url,params,success_callback){
	$.ajax({
		type	: 'POST',
		cache	: false,
		url		: url,
		dataType: "html",
		data	: params,
		success	: success_callback,
		error	: function(html){
			alert("提交数据失败，代码:" +html.status+ "，请稍候再试");
		}
	});
}

function ajax_get_html(url,params,success_callback){
	$.ajax({
		type	: 'GET',
		cache	: false,
		url		: url,
		dataType: "html",
		data	: params,
		success	: success_callback,
		error	: function(html){
			alert("提交数据失败，代码:" +html.status+ "，请稍候再试");
		}
	});
}

/**
 * ajax获取数据
 */
function ajax_get(url,error_tip,success_callback){
	$.ajax({
		type	: 'GET',
		cache	: true,
		url		: the_url,
		success	: succ_callback,
		error	: function(html){
			if(error_tip)
			alert("获取数据失败，代码:" +html.status+ "，请稍候再试");
		}
	});
}

var send_msg_submit = function (v, h, f) {
    if (v == 'ok') {
    	var fname = $(".ajax_dialog_inputtext input[id=fname]").val();
    	var content = $(".ajax_dialog_inputtext textarea[id=mcontent]").val();
        ajax_post_json("/messages/private/dosend",{name:fname,content:content},function(data) {
			if(data.status) {
				$.jBox.tip("发送成功!");
				window.setTimeout(function () { location.reload(); }, 800);
			}else {
				$.jBox.tip(data.msg,'error');
				return false;
			}
		});
    }
};

function send_msg(code) {
	$.jBox.open("post:/messages/private/send?fid="+code,"发送消息",480,'auto',{buttons:{'发送' : 'ok' , '关闭': 'cancel'},submit:send_msg_submit});
}

var login_submit = function (v, h, f) {
    if (v == 'ok') {
    	var name = $("#ajax_login_box input[id=username]").val();
    	var password = $("#ajax_login_box input[id=password]").val();
    	var vcode = $("#ajax_login_box input[id=vcode]").val();
    	var rememberme = $("#ajax_login_box input[id=rememberme]").val();
        ajax_post_json("/user/loginactive",{name:name,pwd:password,vcode:vcode,rememberme:rememberme},function(data) {
			if(data.status) {
				$.jBox.tip("登录成功!",'success');
				window.setTimeout(function () { location.reload(); }, 800);
			}else {
				$.jBox.tip(data.msg,'error');
				window.setTimeout(function () { ajax_box_login();}, 500);
				return false;
			}
		});
    }
};

function ajax_box_login() {
	$.jBox.close(true);
	$.jBox.open("post:/user/box-login","用户登陆",480,'auto',{buttons:{'登录' : 'ok' , '关闭': 'cancel'},submit:login_submit});
}


function follow(uid,flag) {
	if(!flag) {
		if(!confirm("确认取消关注吗?")) {
			return;
		}
	}
	ajax_post_json("/concern/followopt",{id:uid,type:flag},function(data) {
		if(data.status==0) {
			$.jBox.tip(data.msg);
			window.setTimeout(function () { ajax_box_login(); }, 500);
			return;
		}
		if(data.status==1) {
			$.jBox.tip(data.msg,'success');
			window.setTimeout(function () { location.reload(); }, 800);
			return;
		}
		if(data.status=2) {
			$.jBox.tip(data.msg);
			return;
		}
	});
}


function ajax_box_concern_add() {
	ajax_post_json("/concern/add-box",function(data) {
		if(data.status==0) {
			$.jBox.tip(data.msg);
			window.setTimeout(function () { ajax_box_login(); }, 500);
			return;
		}
		if(data.status==-1) {
			
		}
	});
}

var add_concern_submit = function (v, h, f) {
    if (v == 'ok') {
    	var fname = $(".ajax_dialog_inputtext input[id=name]").val();
        ajax_post_json("/concern/box/adddo",{name:fname},function(data) {
			if(data.status) {
				$.jBox.tip("添加成功!",'success');
				window.setTimeout(function () { location.reload(); }, 1000);
			}else {
				$.jBox.tip(data.msg,'error');
				return false;
			}
		});
    }
};

function add_concern() {
	$.jBox.open("post:/concern/box/add","添加关注",480,'auto',{buttons:{'添加' : 'ok' , '关闭': 'cancel'},submit:add_concern_submit});
}

var add_favorite_submit = function (v, h, f) {
    if (v == 'ok') {
    	var id = $("#fav_hidden_id").val();
    	var type =  $("#fav_hidden_type").val();
    	var tags =  $.trim($("#favorite_add_tags").val());
        ajax_post_json("/favorite/adddo",{id:id,type:type,tags:tags},function(data) {
        	if(data.status) {
				$.jBox.tip("收藏添加成功!",'success');
			}else {
				$.jBox.tip(data.msg,'error');
				return false;
			}
		});
    }
};

function add_favorite(type,id) {
	$.jBox.open("post:/favorite/add?type="+type+"&id="+id,"添加收藏",450,'auto',{buttons:{'添加' : 'ok' , '关闭': 'cancel'},submit:add_favorite_submit});
}
function getTags() {
	var status = $("#TagsSwitcher").attr("status");
	if(status=='off') {
		$("#fav_tags_list").show();
		$("#fav_tags_list").load("/favorite_tags/list",{id:'${currentUser.id}'},function(){});
		$("#TagsSwitcher").attr("status","on");
		$("#TagsSwitcher").html("收起标签↑");
		$("#favorite_add_tags").focus();
	}else if(status=='on') {
		$("#TagsSwitcher").attr("status","off");
		$("#fav_tags_list").hide();
		$("#TagsSwitcher").html("选取标签↓");
	}
}

function _getTags(tagsSwitcher,tagslister,tagstext) {
	var status = $("#"+tagsSwitcher).attr("status");
	if(status=='off') {
		$("#"+tagslister).show();
		$("#"+tagslister).load("/favorite_tags/list",{id:'${currentUser.id}'},function(){});
		$("#"+tagsSwitcher).attr("status","on");
		$("#"+tagsSwitcher).html("收起标签↑");
		$("#"+tagstext).focus();
	}else if(status=='on') {
		$("#"+tagsSwitcher).attr("status","off");
		$("#"+tagslister).hide();
		$("#"+tagsSwitcher).html("选取标签↓");
	}
}

function setTags(tag) {
	$("#favorite_add_tags").focus();
	var tags = $.trim($("#favorite_add_tags").val());
	var tag = $.trim(tag);
	var newtags = (tags=="") ? (tag+" ") : (tags+" "+tag+" ");
	$("#favorite_add_tags").attr("value",newtags);
}

function _setTags(tagstext,tag) {
	$("#"+tagstext).focus();
	var tags = $.trim($("#"+tagstext).val());
	var tag = $.trim(tag);
	var newtags = (tags=="") ? (tag+" ") : (tags+" "+tag+" ");
	$("#"+tagstext).attr("value",newtags);
}

function init_select(fieldname, fieldvalue) {
    for(var i = 0; i < fieldname.options.length; i++) {
        if (fieldname.options[i].value==fieldvalue)
        {
        	fieldname.selectedIndex = i;
        }
    }
}