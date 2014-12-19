<#-- 
	freemarker分页宏:
	pager:页面对象;
	link:请求链接(注:类似于这样的链接 /admin/posts?,必须加?);
	showPageNum:显示的分页按钮数;
	anchor:锚点;
-->
<#macro page pager link showPageNum anchor>
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
			<#assign prePage=(pager.pageNo-1)>
			<#assign nextPage=(pager.pageNo+1)>
			<#assign prePageLink=finalLink+(pager.pageNo-1)>
			<#assign nextPageLink=finalLink+(pager.pageNo+1)>
			<div class="pager_holder">
				<h3>
				<#-- 上一页 -->
				<#if (pager.pageNo<=1)>
					<a class="jp-previous jp-disabled">← previous</a>
				<#else>
					<a id="pager_pre" href = ${prePageLink}${anchor} class="jp-previous">← previous</a>
				</#if>
				
				<@writePageNo pager=pager link=finalLink showPageNum=showPageNum anchor=anchor/>
				
				<#-- 下一页 -->
				<#if (pager.pageNo == pager.totalPages)>
					<a class="jp-next jp-disabled">next →</a>
				<#else>
					<a id="pager_next" href = ${nextPageLink}${anchor} class="jp-next">next →</a>
				</#if>
				</h3>
			</div>
			<link href="/styles/jPages.css" rel="stylesheet" type="text/css" />
		</#if>
	</#if>
</#macro>
<#macro writePageNo pager link showPageNum anchor>
	<#--
		└────────────────────────────────────────────────┘
		A												 B             
		
		└───────┴────────┘
		A1	pageNo	 B1
		
		A->B:totalCount
		A1:startIndex
		B1:endIndex
		A1->B1:showPageNum
		A1->pageNo:StartIndexToCurrentPage
		A1->B1 -1:StartIndexToPageShowNum
	-->
	<#if (showPageNum%2==1)>
		<#assign StartIndexToCurrentPage=((showPageNum+1)/2)-1>
	<#else>
		<#assign StartIndexToCurrentPage=(showPageNum/2)-1>
	</#if>
	
	<#assign StartIndexToPageShowNum=(showPageNum-1)>
	
	<#if ((pager.pageNo-StartIndexToCurrentPage) <= 1) || (pager.totalPages<=showPageNum)>
		<#assign startIndex=1>
		<#assign isNeedStartMore=false>
	<#else>
		<#assign startIndex=(pager.pageNo-StartIndexToCurrentPage)>
		<#assign isNeedStartMore=true>
	</#if>
	
	<#if ((startIndex+StartIndexToPageShowNum) < pager.totalPages)>
		<#assign endIndex=startIndex+StartIndexToPageShowNum>
		<#assign isNeedEndMore=true>
	<#else>
		<#assign endIndex=(pager.totalPages)>
		<#assign isNeedEndMore=false>
	</#if>

	<#if isNeedStartMore><a href=${link}1${anchor}>...</a></#if>
	<#list startIndex..endIndex as i>
		<#if pager.pageNo != i>
			<a href=${link}${i}${anchor}>${i}</a>
		<#else>
			<a class="jp-current">${i}</a>
		</#if>
	</#list>
	<#if isNeedEndMore><a href=${link}${pager.totalPages}${anchor}>...</a></#if>
</#macro>