<#-- 用户注册加入-->
	<#if entity.optType=0>
		<div class="ml-15">
		    	<table>
		          <tr>
		            <td rowspan="2" valign="top">
		            	<div  class="feed-item-head">
		                	<a href="/blog/${entity.who.name}" target="blank"  class="thumbnail">
		                		<img src="${entity.who.portraint}?imageView/1/w/25/h/25">
		                	</a>
		                </div>
		            </td>
		            <td colspan="2" width="100%">
		                 <div class="pm-item-summary">
		                  <p>
			                   <a href="/blog/${entity.who.name}" target="_blank" >@${entity.who.nickname}</a> 
			                   &nbsp;加入了Bebop
		                  </P> 
		                  <p>
		                  	<span class="timeago muted" title="${entity.optTime?string('yyyy-MM-dd HH:mm:ss')}">${entity.optTime}</span>
		                  </p>
		                </div>
		            </td>
		          </tr>
				</table>
		 </div>
	</#if>
	
	<#-- 用户发表文章-->
	<#if entity.optType=1>
		<div class="ml-15">
		    	<table>
		          <tr>
		            <td rowspan="2" valign="top" width="25">
		            	<div  class="feed-item-head">
		                	<a href="/blog/${entity.who.name}" target="blank" class="thumbnail">
		                		<img src="${entity.who.portraint}?imageView/1/w/25/h/25">
		                	</a>
		                </div>
		            </td>
		            <td colspan="2" width="100%">
		                 <div class="pm-item-summary">
		                  <p>
			                   <a href="/blog/${entity.who.name}" target="_blank">@${entity.who.nickname}</a> 
			                   &nbsp;发表了博客文章：<a href="/blog/${entity.posts.blogs.users.name}/${entity.posts.id}" target="_blank">${entity.posts.title}</a>
		                  </P> 
		                  <p>
		                  	<span class="timeago muted" title="${entity.optTime?string('yyyy-MM-dd HH:mm:ss')}">${entity.optTime}</span>
		                  </p>
		                </div>
		            </td>
		          </tr>
				</table>
		 </div>
	</#if>
	
	<#-- 用户发表评论-->
	<#if entity.optType=2>
		<div class="ml-15">
		    	<table>
		          <tr>
		            <td rowspan="2" valign="top" width="35">
		            	<div  class="feed-item-head">
		                	<a href="/blog/${entity.who.name}" target="blank" class="thumbnail">
		                		<img src="${entity.who.portraint}?imageView/1/w/25/h/25">
		                	</a>
		                </div>
		            </td>
		            <td colspan="2" width="100%">
		                 <div class="pm-item-summary">
		                  <p>
			                   <a href="/blog/${entity.who.name}" target="_blank">@${entity.who.nickname}</a> 
			                   &nbsp;在博客：<a href="/blog/${entity.posts.blogs.users.name}/${entity.posts.id}#comments" target="_blank">${entity.posts.title}</a>
			                   中发表了评论
		                  </P> 
		                  <p>
		                  	${entity.comments.content}
		                  </p>
		                  <p>
		                  	<span class="timeago muted" title="${entity.optTime?string('yyyy-MM-dd HH:mm:ss')}">${entity.optTime}</span>
		                  </p>
		                </div>
		            </td>
		          </tr>
				</table>
		 </div>
	</#if>
	
	<#-- 用户关注-->
	<#if entity.optType=3>
		<div class="ml-15">
		    	<table>
		          <tr>
		            <td rowspan="2" valign="top" width="35">
		            	<div  class="feed-item-head">
		                	<a href="/blog/${entity.who.name}" target="blank" class="thumbnail">
		                		<img src="${entity.who.portraint}?imageView/1/w/25/h/25">
		                	</a>
		                </div>
		            </td>
		            <td colspan="2" width="100%">
		                 <div class="pm-item-summary">
		                  <p>
			                   <a href="/blog/${entity.who.name}" target="_blank">@${entity.who.nickname}</a> 
			                   &nbsp;关注了  <a href="/blog/${entity.whose.name}" target="_blank">@${entity.whose.nickname}</a> 的动态
		                  </P> 
		                  <p>
		                  	<span class="timeago muted" title="${entity.optTime?string('yyyy-MM-dd HH:mm:ss')}">${entity.optTime}</span>
		                  </p>
		                </div>
		            </td>
		          </tr>
				</table>
		 </div>
	</#if>
	
	<#-- 用户收藏文章-->
	<#if entity.optType=4>
		<div class="ml-15">
		    	<table>
		          <tr>
		            <td rowspan="2" valign="top" width="35">
		            	<div  class="feed-item-head">
		                	<a href="/blog/${entity.who.name}" target="blank" class="thumbnail">
		                		<img src="${entity.who.portraint}?imageView/1/w/25/h/25">
		                	</a>
		                </div>
		            </td>
		            <td colspan="2" width="100%">
		                 <div class="pm-item-summary">
		                  <p>
			                   <a href="/blog/${entity.who.name}" target="_blank">@${entity.who.nickname}</a> 
			                   &nbsp;收藏了  <a href="/blog/${entity.whose.name}" target="_blank">@${entity.whose.nickname}</a> 
			                   的博客文章  <a href="/blog/${entity.posts.blogs.users.name}/${entity.posts.id}" target="_blank">${entity.posts.title}</a> 
		                  </P> 
		                  <p>
		                  	<span class="timeago muted" title="${entity.optTime?string('yyyy-MM-dd HH:mm:ss')}">${entity.optTime}</span>
		                  </p>
		                </div>
		            </td>
		          </tr>
				</table>
		 </div>
	</#if>
	
	<#-- 用户回复评论-->
	<#if entity.optType=5>
		<div class="ml-15">
		    	<table>
		          <tr>
		            <td rowspan="2" valign="top" width="35">
		            	<div  class="feed-item-head">
		                	<a href="/blog/${entity.who.name}" target="blank" class="thumbnail">
		                		<img src="${entity.who.portraint}?imageView/1/w/25/h/25">
		                	</a>
		                </div>
		            </td>
		            <td colspan="2" width="100%">
		                 <div class="pm-item-summary">
		                  <p>
			                   <a href="/blog/${entity.who.name}" target="_blank">@${entity.who.nickname}</a> 
			                   &nbsp;在博客：<a href="/blog/${entity.posts.blogs.users.name}/${entity.posts.id}#comments" target="_blank">${entity.posts.title}</a>
			                  	 中回复了 <a href="/blog/${entity.whose.name}" target="_blank">@${entity.whose.nickname}</a> 的评论
		                  </P>
		                  <p>
		                  	<a href="/blog/${entity.whose.name}" target="_blank">@${entity.whose.nickname}</a>说:
					    	${entity.comments.parent.content}
		                  </p> 
		                  <p>
		                  	<a href="/blog/${entity.who.name}" target="_blank">@${entity.who.nickname}</a>回复:
					    	${entity.comments.content}
		                  </p>
		                  <p>
		                  	<span class="timeago muted" title="${entity.optTime?string('yyyy-MM-dd HH:mm:ss')}">${entity.optTime}</span>
		                  </p>
		                </div>
		            </td>
		          </tr>
				</table>
			 </div>
	</#if>
