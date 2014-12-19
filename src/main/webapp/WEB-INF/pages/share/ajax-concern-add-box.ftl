<style type="text/css">
.ajax_dialog_box {
	height: 40px;
	width: 450px;
	margin-top: 0px;
	margin-right: auto;
	margin-bottom: 10px;
	margin-left: auto;
	padding-top: 10px;
	padding-right: 0px;
	padding-bottom: 0px;
	padding-left: 0px;
}

.ajax_dialog_box a{
	color: #46B;
	text-decoration: none;
}

.amsg {
	float:right;
	font-weight:bold;
	color: #900;
}

.ajax_dialog_box textarea {
	background: #FFD;
	border: 1px solid #CCC;
	padding: 2px;
}
.ajax_dialog_box .ajax_dialog_inputtext {
	width: 450px;
	margin-top: 5px;
	margin-right: auto;
	margin-left: auto;
	height: auto;
}
</style>
<div class="ajax_dialog_box">
	<div class="ajax_dialog_inputtext">
	  <div>请输入昵称：
	      <#if islogin==0>
	      	<input type="text" name="name" id="name" placeholder="请输入对方昵称" disabled="disabled"/>
	      	<span class="amsg" >&nbsp;&nbsp;您尚未登录,请先<a href="javascript:ajax_box_login()">登录</a></span>
	      <#else>
	      	<input type="text" name="name" id="name" placeholder="请输入对方昵称"/>
	      </#if>
	  </div>
	</div>
</div>