<#if (usersPage.resultList?size>0)>
	<#list usersPage.resultList as user>
	 <div class="list-widget">
	      <li>
	        <p><h6><a href="/blog/${user.name}" target="_blank" title="${user.nickname}">@${user.nickname}</a></h6></p>
	        <a class="thumbnail pull-left" href="/blog/${user.name}" title="${user.nickname}"  target="_blank"><img src="${user.portraint}"  style="width:45px;height:45px;"></a>
	        <p>&nbsp;&nbsp;
	        	<#assign ose_userstatus=JspTaglibs["/WEB-INF/UserStatusTag.tld"]/>
	          	<@ose_userstatus.userStatus uid='${user.id}'/> 
	        </p>
	        <p class="muted">&nbsp;&nbsp;登录：<span class="timeago muted" title="${user.thisLoginTime?string('yyyy-MM-dd HH:mm:ss')}">${user.thisLoginTime}</span></p>
	      </li>
	  </div>
</#list>
<script type="text/javascript">
	$(function() {$(".timeago").prettyDate();});
</script>
</#if>