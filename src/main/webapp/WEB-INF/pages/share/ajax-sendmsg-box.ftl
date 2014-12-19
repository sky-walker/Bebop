<style type="text/css">
.ajax_dialog_box {
	height: 220px;
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
	margin-right: auto;
	margin-left: auto;
	height: auto;
}
</style>
<div class="ajax_dialog_box">
  <div class="ajax_dialog_inputtext">
  <div>
      发送给：
      <#if friend?exists>
      	<a href="/blog/${friend.name}" target="blank">@${friend.nickname}</a>
      	<input type="hidden" name="nickname" id="fname" value="${friend.nickname}"/>
      <#else>
      	<input type="text" name="nickname" id="fname" placeholder="请输入对方昵称"/>
      </#if>
  </div>
      <#if islogin==0>
      		<p>留言内容(最多250个字)</p>
	      <textarea name="content" id="mcontent" style="width:440px;height:140px;padding:2px;overflow:hidden;" disabled="disabled" >
		  </textarea>
	  <#else>
	  	<p>留言内容(最多250个字)</p>
	      <textarea name="content" id="mcontent" style="width:440px;height:140px;padding:2px;overflow:hidden;" placeholder="请输入消息内容"></textarea>
      </#if>
      
  </div>
</div>