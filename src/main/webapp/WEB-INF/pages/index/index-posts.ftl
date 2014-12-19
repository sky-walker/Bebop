<#if (postsPage.resultList?size>0)>
	<#list postsPage.resultList as post>
		<div class="list-widget">
			<p></p>
			 <li>
	        	<p><i class="icon-file"></i><a href="/blog/${post.blogs.users.name}/${post.id}" target="_blank">${post.title}</a> </p>
	            <p>
	            	<span>
	                	<span class="timeago muted" title="${post.createTime?string('yyyy-MM-dd HH:mm:ss')}">${post.createTime}</span>
	                	<span class="muted">by @${post.blogs.users.nickname}</span>
	                </span>
	            	<span class="muted pull-right">${post.commentsNum}回/${post.viewsNum}阅</span>
	            </p> 
		      </li>
	      </div>
	     <p></p>
	</#list>
	<script type="text/javascript">
		$(function() {$(".timeago").prettyDate();});
	</script>
</#if>