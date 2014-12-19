<#if pager.totalRecords=0>
	<div class="datanull">暂无粉丝..</div>
<#else>	
	<#list pager.resultList as entity>
		<li>
	    	<a href="/blog/${entity.fans.name}" class="thumbnail" title="${entity.fans.nickname}" target="_blank"><img style="width: 30px; height: 30px; " src="${entity.fans.portraint}" ></a>
	    </li>
	</#list>
	</br>
	<div style="font: 0px/0px sans-serif;clear: both;display: block"> </div>
	<div style="text-align:center;" class="clearfix"><a href="/blog/${user.name}/fans">显示所有粉丝&nbsp;<span class="badge badge-info">${pager.totalRecords}</span></a></div>
	</br></br></br>
</#if>