<#if pager.totalRecords=0>
	<div class="datanull">暂无关注..</div>
<#else>
	<#list pager.resultList as entity>
		<li>
	    	<a href="/blog/${entity.follow.name}" class="thumbnail" title="${entity.follow.nickname}" target="_blank"><img style="width: 30px; height: 30px; " src="${entity.follow.portraint}" ></a>
	    </li>
	</#list>
	</br>
	<div style="font: 0px/0px sans-serif;clear: both;display: block"> </div>
	<div style="text-align:center;" class="clearfix"><a href="/blog/${user.name}/follows">显示所有关注&nbsp;<span class="badge badge-info">${pager.totalRecords}</span></a></div>
</#if>
