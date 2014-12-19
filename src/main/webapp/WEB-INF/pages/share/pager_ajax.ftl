<#-- 
	freemarker分页宏:
	pager:页面对象;
	link:请求链接(注:类似于这样的链接 /admin/posts?,必须加?);
	showPageNum:显示的分页按钮数;
-->
<#macro page pager link showPageNum>
	<#if (pager.pageSize>0 && pager.totalRecords>0)>
		<#if (pager.totalPages>=2) >
			<#assign queryLink = "">
			<#-- 如果查询带参数的话-->
			<#if (pager.queryBy?size>0)>
				<#list pager.queryBy?keys as queryKey>
					<#assign queryValue=pager.queryBy[queryKey]>
					<#if queryValue!="">
						<#assign queryLink=queryLink+queryKey+"="+queryValue+"&">
					</#if>
				</#list>
			</#if>
			<#assign finalLink = link+queryLink+"page=">
			<#assign prePage=(pager.currentPage-1)>
			<#assign nextPage=(pager.currentPage+1)>
			<#assign prePageLink=finalLink+(pager.currentPage-1)>
			<#assign nextPageLink=finalLink+(pager.currentPage+1)>			
			<h3>
			<div class="pager_holder">
				<#-- 上一页 -->
				<#if (pager.currentPage<=1)>
					<a class="jp-previous jp-disabled">← previous</a>
				<#else>
					<a id="pager_pre" href = ${prePageLink} class="jp-previous">← previous</a>
				</#if>
				
				<@writePageNo pager=pager link=finalLink showPageNum=showPageNum/>
				
				<#-- 下一页 -->
				<#if (pager.currentPage == pager.totalPages)>
					<a class="jp-next jp-disabled">next →</a>
				<#else>
					<a id="pager_next" href = ${nextPageLink} class="jp-next">next →</a>
				</#if>
			</div>
			</h3>
			<link href="/styles/jPages.css" rel="stylesheet" type="text/css" />
		</#if>
	</#if>
</#macro>
<#macro writePageNo pager link showPageNum>
	<#--
		└────────────────────────────────────────────────┘
		A												 B             
		
		└───────┴────────┘
		A1	currengPage	 B1
		
		A->B:totalCount
		A1:startIndex
		B1:endIndex
		A1->B1:showPageNum
		A1->currengPage:StartIndexToCurrentPage
		A1->B1 -1:StartIndexToPageShowNum
	-->
	<#if (showPageNum%2==1)>
		<#assign StartIndexToCurrentPage=((showPageNum+1)/2)-1>
	<#else>
		<#assign StartIndexToCurrentPage=(showPageNum/2)-1>
	</#if>
	
	<#assign StartIndexToPageShowNum=(showPageNum-1)>
	
	<#if ((pager.currentPage-StartIndexToCurrentPage) <= 1) || (pager.totalPages<=showPageNum)>
		<#assign startIndex=1>
		<#assign isNeedStartMore=false>
	<#else>
		<#assign startIndex=(pager.currentPage-StartIndexToCurrentPage)>
		<#assign isNeedStartMore=true>
	</#if>
	
	<#if ((startIndex+StartIndexToPageShowNum) < pager.totalPages)>
		<#assign endIndex=startIndex+StartIndexToPageShowNum>
		<#assign isNeedEndMore=true>
	<#else>
		<#assign endIndex=(pager.totalPages)>
		<#assign isNeedEndMore=false>
	</#if>

	<#if isNeedStartMore><a href=${link}1>...</a></#if>
	<#list startIndex..endIndex as i>
		<#if pager.currentPage != i>
			<a href=${link}${i}>${i}</a>
		<#else>
			<a class="jp-current">${i}</span>
		</#if>
	</#list>
	<#if isNeedEndMore><a href=${link}${pager.totalPages}>...</a></#if>
</#macro>