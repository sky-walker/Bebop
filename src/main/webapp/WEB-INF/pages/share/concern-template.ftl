<#-- 查询我所关注的人 -->
<#if type="follows">
	<div class="concern-item">
		<table>
		  <tr>
		    <td rowspan="2" valign="top" width="50px">
		    	<div  class="concern-item-head">
		        	<a href="/blog/${entity.follow.name}" target="blank">
		            	<img src="${entity.follow.portraint}" class="img-rounded"  style="width:35px;height:35px;">
		        	</a>
		        </div>
		    </td>
		    <td colspan="2" width="800px">
		         <div class="concern-item-summary">
		            <p>
		            	<a href="/blog/${entity.follow.name}" target="blank">
			    			@${entity.follow.nickname}
			    		</a>
			    		${entity.follow.province} ${entity.follow.city}
			    		<span style="float:right">
			    			<a href="javascript:send_msg(${entity.follow.id})">+发送消息</a> | 
		    				<a href="javascript:follow(${entity.follow.id},false)">取消关注</a>
			    		</span>
		            </p>
		            <p>
		            	<span class="timeago muted" title="${entity.lastFeed.optTime?string('yyyy-MM-dd HH:mm:ss')}">${entity.lastFeed.optTime}</span>
		            	<#--加入Bebeop-->
				    	<#if entity.lastFeed.optType=0>
				    		加入了 Bebeop
				    	</#if>
				    	<#--文章的发表-->
				    	<#if entity.lastFeed.optType=1>
				    		发表了文章:<a href="/blog/${entity.lastFeed.whose.name}/${entity.lastFeed.posts.id}" target="blank">&nbsp;${entity.lastFeed.posts.title}</a>
				    	</#if>
				    	<#--评论-->
				    	<#if entity.lastFeed.optType=2>
				    		评论了文章  <a href="/blog/${entity.lastFeed.whose.name}/${entity.lastFeed.posts.id}" target="blank">${entity.lastFeed.posts.title}</a>
				    		<#assign ose_htmlSummary=JspTaglibs["/WEB-INF/HtmlSummaryTag.tld"]/>
					    	<@ose_htmlSummary.htmlSummary html='${entity.lastFeed.comments.content}'/>
				    	</#if>
				    	<#--关注的添加-->
				    	<#if entity.lastFeed.optType=3>
				    		关注了<a href="/blog/${entity.lastFeed.whose.name}" target="blank">@${entity.lastFeed.whose.nickname} </a>的动态
				    	</#if>
				    	<#--收藏-->
				    	<#if entity.lastFeed.optType=4>
				    		收藏了博客文章：<a href="/blog/${entity.lastFeed.whose.name}/${entity.lastFeed.posts.id}" target="blank">${entity.lastFeed.posts.title}</a>
				    	</#if>
				    	<#--评论回复-->
				    	<#if entity.lastFeed.optType=5>
				    		在博客文章 <a href="/blog/${entity.lastFeed.whose.name}/${entity.lastFeed.posts.id}" target="blank">${entity.lastFeed.posts.title}</a> 中回复了 <a href="/blog/${entity.lastFeed.whose.blogs.name}" target="blank">@${entity.lastFeed.whose.blogs.name}</a> 的评论：
				    		<#assign ose_htmlSummary=JspTaglibs["/WEB-INF/HtmlSummaryTag.tld"]/>
					    	<@ose_htmlSummary.htmlSummary html='${entity.lastFeed.comments.content}'/>
				    	</#if>
		            </p>
		        </div>
		    </td>
		  </tr>
		</table>
	</div>
</#if>
<#if type="fans">
	<div class="concern-item">
		<table>
		  <tr>
		    <td rowspan="2" valign="top" width="50px">
		    	<div  class="concern-item-head">
		        	<a href="/blog/${entity.fans.name}" target="blank">
		            	<img src="${entity.fans.portraint}" class="img-rounded"  style="width:35px;height:35px;">
		        	</a>
		        </div>
		    </td>
		    <td colspan="2" width="800px">
		         <div class="concern-item-summary">
		            <p>
		            	<a href="/blog/${entity.fans.name}" target="blank">
			    			@${entity.fans.nickname}
			    		</a>
			    		${entity.fans.province} ${entity.fans.city}
			    		<span style="float:right">
			    			<a href="javascript:send_msg(${entity.fans.id})">+发送消息</a> | 
		    				<#if entity.isFriends=1>
					    		<a href="javascript:follow(${entity.fans.id},false)">取消互粉</a>
					    	<#else>
					    		<a href="javascript:follow(${entity.fans.id},true)">+添加关注</a>
					    	</#if>
			    		</span>
		            </p>
		            <p>
		            	<span class="timeago muted" title="${entity.lastFeed.optTime?string('yyyy-MM-dd HH:mm:ss')}">${entity.lastFeed.optTime}</span>
		            	<#--加入Bebeop-->
				    	<#if entity.lastFeed.optType=0>
				    		加入了 Bebeop
				    	</#if>
				    	<#--文章的发表-->
				    	<#if entity.lastFeed.optType=1>
				    		发表了文章:<a href="/blog/${entity.lastFeed.whose.name}/${entity.lastFeed.posts.id}" target="blank">&nbsp;${entity.lastFeed.posts.title}</a>
				    	</#if>
				    	<#--评论-->
				    	<#if entity.lastFeed.optType=2>
				    		评论了文章  <a href="/blog/${entity.lastFeed.whose.name}/${entity.lastFeed.posts.id}" target="blank">${entity.lastFeed.posts.title}</a>
				    		<#assign ose_htmlSummary=JspTaglibs["/WEB-INF/HtmlSummaryTag.tld"]/>
					    	<@ose_htmlSummary.htmlSummary html='${entity.lastFeed.comments.content}'/>
				    	</#if>
				    	<#--关注的添加-->
				    	<#if entity.lastFeed.optType=3>
				    		关注了<a href="/blog/${entity.lastFeed.whose.name}" target="blank">@${entity.lastFeed.whose.nickname} </a>的动态
				    	</#if>
				    	<#--收藏-->
				    	<#if entity.lastFeed.optType=4>
				    		收藏了博客文章：<a href="/blog/${entity.lastFeed.whose.name}/${entity.lastFeed.posts.id}" target="blank">${entity.lastFeed.posts.title}</a>
				    	</#if>
				    	<#--评论回复-->
				    	<#if entity.lastFeed.optType=5>
				    		在博客文章 <a href="/blog/${entity.lastFeed.whose.name}/${entity.lastFeed.posts.id}" target="blank">${entity.lastFeed.posts.title}</a> 中回复了 <a href="/blog/${entity.lastFeed.whose.blogs.name}" target="blank">@${entity.lastFeed.whose.blogs.name}</a> 的评论：
				    		<#assign ose_htmlSummary=JspTaglibs["/WEB-INF/HtmlSummaryTag.tld"]/>
					    	<@ose_htmlSummary.htmlSummary html='${entity.lastFeed.comments.content}'/>
				    	</#if>
		            </p>
		        </div>
		    </td>
		  </tr>
		</table>
	</div>
</#if>
<#if type="friends">
	<div class="concern-item">
		<table>
		  <tr>
		    <td rowspan="2" valign="top" width="50px">
		    	<div  class="concern-item-head">
		        	<a href="/blog/${entity.follow.name}" target="blank">
		            	<img src="${entity.follow.portraint}" class="img-rounded"  style="width:35px;height:35px;">
		        	</a>
		        </div>
		    </td>
		    <td colspan="2" width="800px">
		         <div class="concern-item-summary">
		            <p>
		            	<a href="/blog/${entity.follow.name}" target="blank">
			    			@${entity.follow.nickname}
			    		</a>
			    		${entity.follow.province} ${entity.follow.city}
			    		<span style="float:right">
			    			<a href="javascript:send_msg(${entity.follow.id})">+发送消息</a> | 
		    				<a href="javascript:follow(${entity.follow.id},false)">取消互粉</a>
			    		</span>
		            </p>
		            <p>
		            	<span class="timeago muted" title="${entity.lastFeed.optTime?string('yyyy-MM-dd HH:mm:ss')}">${entity.lastFeed.optTime}</span>
		            	<#--加入Bebeop-->
				    	<#if entity.lastFeed.optType=0>
				    		加入了 Bebeop
				    	</#if>
				    	<#--文章的发表-->
				    	<#if entity.lastFeed.optType=1>
				    		发表了文章:<a href="/blog/${entity.lastFeed.whose.name}/${entity.lastFeed.posts.id}" target="blank">&nbsp;${entity.lastFeed.posts.title}</a>
				    	</#if>
				    	<#--评论-->
				    	<#if entity.lastFeed.optType=2>
				    		评论了文章  <a href="/blog/${entity.lastFeed.whose.name}/${entity.lastFeed.posts.id}" target="blank">${entity.lastFeed.posts.title}</a>
				    		<#assign ose_htmlSummary=JspTaglibs["/WEB-INF/HtmlSummaryTag.tld"]/>
					    	<@ose_htmlSummary.htmlSummary html='${entity.lastFeed.comments.content}'/>
				    	</#if>
				    	<#--关注的添加-->
				    	<#if entity.lastFeed.optType=3>
				    		关注了<a href="/blog/${entity.lastFeed.whose.name}" target="blank">@${entity.lastFeed.whose.nickname} </a>的动态
				    	</#if>
				    	<#--收藏-->
				    	<#if entity.lastFeed.optType=4>
				    		收藏了博客文章：<a href="/blog/${entity.lastFeed.whose.name}/${entity.lastFeed.posts.id}" target="blank">${entity.lastFeed.posts.title}</a>
				    	</#if>
				    	<#--评论回复-->
				    	<#if entity.lastFeed.optType=5>
				    		在博客文章 <a href="/blog/${entity.lastFeed.whose.name}/${entity.lastFeed.posts.id}" target="blank">${entity.lastFeed.posts.title}</a> 中回复了 <a href="/blog/${entity.lastFeed.whose.blogs.name}" target="blank">@${entity.lastFeed.whose.blogs.name}</a> 的评论：
				    		<#assign ose_htmlSummary=JspTaglibs["/WEB-INF/HtmlSummaryTag.tld"]/>
					    	<@ose_htmlSummary.htmlSummary html='${entity.lastFeed.comments.content}'/>
				    	</#if>
		            </p>
		        </div>
		    </td>
		  </tr>
		</table>
	</div>
</#if>
