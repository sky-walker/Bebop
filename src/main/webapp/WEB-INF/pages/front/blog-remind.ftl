<#if (pager.totalRecords==0)>
	<div id="remind_summary">
		<div class="loading" style="display:block;">正在加载中..</div>
		<p></p>
	</div>
<#else>	
<script type="text/javascript">
	$(function(){
		$(".timeago").prettyDate();
		$("div[class*=remind_summary_list]").mouseover(function(){
			$(this).css({"background":"#DBEBF4"});
		}).mouseout(function(){
			$(this).css({"background":"#FFFFFF"});
		});
		$("div[class*=remind_summary_list]").toggle(function() {
			$(this).css({"background":"#DBEBF4"});
			$("#remind_detail_item_"+$(this).attr("id").split("_")[3]).slideDown('fast');
		},function() {
			$(this).css({"background":"#FFFFFF"});
			$("#remind_detail_item_"+$(this).attr("id").split("_")[3]).slideUp('fast');
		});
	});
</script>
<div id="remind_summary">
	<div id="remind_summary_title">通知<em><a href="/admin/reminds" target='_blank'>查看所有»</a></em></div>
	<div id="remind_summary_body">
		<#list pager.resultList as entity>
			 <#-- 用户注册加入-->
			<#if entity.optType=0>
				<div id="remind_summary_item_${entity.id}" class=<#if (entity.readStatus==0)>'remind_summary_list_strong'<#else>'remind_summary_list'</#if> listpageno="${entity_index}">
					<table width="100%" class="remind_tb" align="center">
				  	  <tr><td colspan="3" height="8px;"></td></tr>
				  	  <tr>
					    <td style="width:25px;height:25px;">
					    	<a href="/blog/${entity.who.name}" target="blank" class="thumbnail">
				    			<div class="miniface-25"><img src="${entity.who.portraint}" style="width:25px;height:25px;" border="0"></div>
				    		</a>
					    </td>
					    <td align="left">
					    	<a>@${entity.who.nickname}</a> 欢迎您加入Bebeop
					    </td>
					    <td align="right">
					    	<#if (entity.readStatus==0)>
					    		<img src="/images/new.gif" />
					    	</#if>
					    	<span class="timeago muted" title="${entity.optTime?string('yyyy-MM-dd HH:mm:ss')}">${entity.optTime}</span>
					    </td>
					  </tr>
					  <tr><td colspan="3" height="8px;"></td></tr>
				 	</table>
				</div>
				<div id="remind_detail_item_${entity.id}" class="remind_detail_item" style="display:none;background-color:#FFFFB6;">
					<div style="padding:16px 0px 10px 30px;border-top-width:1px;border-top-color:#ccc;border-top-style:dotted;">
						欢迎您加入Bebeop!&nbsp;&nbsp;&nbsp;&nbsp;<a href="/admin/portraint" target="_blank">+上传个头像呗?</a>
					</div>
				</div>
			</#if>
		   <#-- 博客评论的添加-->
			<#if entity.optType=2>
				<div id="remind_summary_item_${entity.id}" class=<#if (entity.readStatus==0)>'remind_summary_list_strong'<#else>'remind_summary_list'</#if> listpageno="${entity_index}">
					<table width="100%" class="remind_tb" align="center">
				  	  <tr><td colspan="3" height="8px;"></td></tr>
				  	  <tr>
					    <td style="width:25px;height:25px;">
					    	<a href="/blog/${entity.who.name}" target="blank" class="thumbnail">
				    			<div class="miniface-25"><img src="${entity.who.portraint}" style="width:25px;height:25px;" border="0"></div>
				    		</a>
					    </td>
					    <td align="left">
					    	<a>@${entity.who.nickname}</a> 评论了你的博客文章
					    </td>
					    <td align="right">
					    	<#if (entity.readStatus==0)>
					    		<img src="/images/new.gif" />
					    	</#if>
					    	<span class="timeago muted" title="${entity.optTime?string('yyyy-MM-dd HH:mm:ss')}">${entity.optTime}</span>
					    </td>
					  </tr>
					  <tr><td colspan="3" height="8px;"></td></tr>
				 	</table>
				</div>
				<div id="remind_detail_item_${entity.id}" class="remind_detail_item" style="display:none;background-color:#FFFFB6;">
					<div style="padding:16px 0px 10px 30px;border-top-width:1px;border-top-color:#ccc;border-top-style:dotted;">
						<a href="/blog/${entity.whose.name}/${entity.posts.id}#comments" target="_blank"> « ${entity.posts.title} » </a>
					</div>
					<div style="padding-left:20px;padding-right:20px;">
						<div style="padding:10px 0px 16px 10px;border-top-width:1px;border-top-color:#ccc;border-top-style:dotted;line-height: 2em;">
							<a href="/blog/${entity.who.name}" target="_blank">
				    			@${entity.who.nickname}：
				    		</a>
					    		${entity.comments.content}
						</div>
					</div>
				</div>
			</#if>
			<#-- 添加关注-->
			<#if entity.optType=3>
				<div id="remind_summary_item_${entity.id}" class=<#if (entity.readStatus==0)>'remind_summary_list_strong'<#else>'remind_summary_list'</#if> listpageno="${entity_index}">
					<table width="100%" class="remind_tb" align="center">
				  	  <tr><td colspan="3" height="8px;"></td></tr>
				  	  <tr>
					    <td style="width:25px;height:25px;">
					    	<a href="/blog/${entity.who.name}" target="_blank" class="thumbnail">
				    			<div class="miniface-25"><img src="${entity.who.portraint}" style="width:25px;height:25px;" border="0"></div>
				    		</a>
					    </td>
					    <td align="left">
					    	<a>@${entity.who.nickname}</a> 关注了您
					    </td>
					    <td align="right">
					    	<#if (entity.readStatus==0)>
					    		<img src="/images/new.gif" />
					    	</#if>
					    	<span class="timeago muted" title="${entity.optTime?string('yyyy-MM-dd HH:mm:ss')}">${entity.optTime}</span>
					    </td>
					  </tr>
					  <tr><td colspan="3"  height="8px;"></td></tr>
				 </table>
				</div>
				<div id="remind_detail_item_${entity.id}" class="remind_detail_item" style="display:none;background-color:#FFFFB6;">
					<div style="padding:10px 0px 16px 30px;border-top-width:1px;border-top-color:#ccc;border-top-style:dotted;">
						<#assign sex="他">
						<#if entity.conern.fans.gender==1>
							<#assign sex="他">
						<#else>
							<#assign sex="她">
						</#if>
						<#if entity.concern.isFriends==1>
							<a href="/blog/${entity.who.name}" target="_blank">@${entity.who.nickname}</a> 和 你 目前的关系是: 互相关注.
						</#if>
						<#if entity.concern.isFriends==0>
							<a href="/blog/${entity.who.name}" target="_blank">${sex} </a>关注了您 您也去关注<a href="/blog/${entity.who.name}" target="_blank"> ${sex} </a>: <a href="javascript:follow(${entity.who.id},true)">+添加关注</a>
						</#if>
					</div>
				</div>
			</#if>
			<#-- 添加收藏-->
			<#if entity.optType=4>
				<div id="remind_summary_item_${entity.id}" class=<#if (entity.readStatus==0)>'remind_summary_list_strong'<#else>'remind_summary_list'</#if> listpageno="${entity_index}">
					<table width="100%" class="remind_tb" align="center">
				  	  <tr><td colspan="3" height="8px;"></td></tr>
				  	  <tr>
					    <td style="width:25px;height:25px;">
					    	<a href="/blog/${entity.who.name}" target="_blank" class="thumbnail">
				    			<div class="miniface-25"><img src="${entity.who.portraint}" style="width:25px;height:25px;" border="0"></div>
				    		</a>
					    </td>
					    <td align="left">
					    	<a>@${entity.who.nickname}</a> 收藏了您的博客文章
					    </td>
					    <td align="right">
					    	<#if (entity.readStatus==0)>
					    		<img src="/images/new.gif" />
					    	</#if>
					    	<span class="timeago muted" title="${entity.optTime?string('yyyy-MM-dd HH:mm:ss')}">${entity.optTime}</span>
					    </td>
					  </tr>
					  <tr><td colspan="3"  height="8px;"></td></tr>
				 </table>
				</div>
				<div id="remind_detail_item_${entity.id}" class="remind_detail_item" style="display:none;background-color:#FFFFB6;">
					<div style="padding:10px 0px 16px 40px;border-top-width:1px;border-top-color:#ccc;border-top-style:dotted;">
						<a href="/blog/${entity.whose.name}/${entity.posts.id}" target="_blank">  « ${entity.posts.title} » </a>
					</div>
				</div>
			</#if>
			<#-- 回复评论-->
			<#if entity.optType=5>
				<div id="remind_summary_item_${entity.id}" class=<#if (entity.readStatus==0)>'remind_summary_list_strong'<#else>'remind_summary_list'</#if> listpageno="${entity_index}">
					<table width="100%" class="remind_tb" align="center">
				  	  <tr><td colspan="3" height="8px;"></td></tr>
				  	  <tr>
					    <td style="width:25px;height:25px;">
					    	<a href="/blog/${entity.who.name}" target="_blank" class="thumbnail">
				    			<div class="miniface-25"><img src="${entity.who.portraint}" style="width:25px;height:25px;" border="0"></div>
				    		</a>
					    </td>
					    <td align="left">
					    	<a>@${entity.who.nickname}</a> 在文章中回复了你的评论
					    </td>
					    <td align="right">
					    	<#if (entity.readStatus==0)>
					    		<img src="/images/new.gif" />
					    	</#if>
					    	<span class="timeago muted" title="${entity.optTime?string('yyyy-MM-dd HH:mm:ss')}">${entity.optTime}</span>
					    </td>
					  </tr>
					  <tr><td colspan="3"  height="8px;"></td></tr>
				 </table>
				</div>
				<div id="remind_detail_item_${entity.id}" class="remind_detail_item" style="display:none;background-color:#FFFFB6;">
					<div style="padding:10px 0px 16px 30px;border-top-width:1px;border-top-color:#ccc;border-top-style:dotted;">
						<a href="/blog/${entity.posts.blogs.users.name}/${entity.posts.id}#comments" target="_blank"> « ${entity.posts.title} » </a>
					</div>
					<div style="padding-left:20px;padding-right:20px;">
						<div style="padding:10px 0px 16px 10px;border-top-width:1px;border-top-color:#ccc;border-top-style:dotted;line-height: 2em;">
							<a href="/blog/${entity.comments.parent.users.name}">你说</a>：${entity.comments.parent.content}
						</div>
					</div>
					<div style="padding-left:20px;padding-right:20px;">
						<div style="padding:10px 0px 16px 10px;border-top-width:1px;border-top-color:#ccc;border-top-style:dotted;line-height: 2em;">
							<a href="/blog/${entity.who.name}" target="_blank">
				    			@${entity.who.nickname}
				    		</a>回复：
					    		${entity.comments.content}
						</div>
					</div>
				</div>
			</#if>
		</#list>
	</div>
<div>
</#if>

