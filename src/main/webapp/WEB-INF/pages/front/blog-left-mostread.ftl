<#if pager.totalRecords=0 >
	  <div class="datanull">暂无文章..</div>
<#else>
		<#list pager.resultList as entity>
			<#if ((entity.title?length) > 12)>
				<li>
		        	<i class="icon-file"></i><a href="/blog/${user.name}/${entity.id}"  title="${entity.title}">${entity.title[0..12]}...(${entity.viewsNum})</a>
		        </li>
			<#else>
				<li><i class="icon-file"></i> <a href="/blog/${user.name}/${entity.id}" title="${entity.title}">${entity.title}(${entity.viewsNum})</a></li>
			</#if>
		</#list>
</#if>