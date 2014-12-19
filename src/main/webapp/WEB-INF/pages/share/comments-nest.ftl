<#macro commentnest comment>
	<#if comment.parent.id?exists>
		<div class="quotetop">引用<a href="/blog/${comment.parent.users.name}" target="_blank">@${comment.parent.users.nickname}</a>  (${comment.parent.createTime?string('yyyy-MM-dd HH:mm')})</div>
		<div class="quotemain">
			<@commentnest comment=comment.parent/>
			<#if comment.parent.visible==0>
			 	<span style="text-align:center;">此评论已被隐藏..</span>
			 <#else>
			 	 <#if comment.parent.delStatus==0>
			 		<span style="text-align:center;">此评论已被删除..</span>
				 <#else>
				 	 ${comment.parent.content}
				 </#if>
			 </#if>
		</div>
	</#if>
</#macro>