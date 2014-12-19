 <#list tagsPager.resultList as entity>
 	<span id="show_fav_tags_${entity.name}" class="fav_tag" style="margin-right:5px;">
 		<a href="javascript:setTags('${entity.name?html}')"  class="minitag">
 			<span class="badge badge-info tagwrap">${entity.name?html}</span>
 		</a>
 	</span>
 </#list>
