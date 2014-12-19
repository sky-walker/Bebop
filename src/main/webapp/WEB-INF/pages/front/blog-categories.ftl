<#if categories?size==0>
	<div class="datanull">暂无分类...</div>
<#else>
       <#list categories as entity>
	      <li>
		    	<i class="icon-tag"></i> <a href="/blog/${user.name}?category=${entity.id}"  title="${entity.name}">${entity.name}(${entity.postNum})</a>
		  </li>
	   </#list>
</#if>