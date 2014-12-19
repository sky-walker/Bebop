<#if (visits?size>0)>
	<#list visits as entity>
		<li>
	    	<a href="/blog/${entity.visitor.name}" class="thumbnail" title="${entity.visitor.nickname}" target="_blank"><img style="width: 30px; height: 30px; " src="${entity.visitor.portraint}" ></a>
	    </li>
	</#list>
<#else>
	　　　　<div class="datanull">暂无访客..</div>
</#if>
<div class="clearfix"></div>