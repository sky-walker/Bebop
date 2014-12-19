<#--
	分页标签：用于显示数据分页链接。
	pagination：分页对象。
	url：链接地址
	showPageLinkCount:显示的页数链接数
	isShowMoreLI:是否显示“<li>...</li>”提示更多还有更多页数
	isNeedPageTo：是否显示转到指定页数的表单
-->
<#macro pager pagination url="#" showPageLinkCount=10 isShowMoreLI=true isNeedPageTo=true>
	<#if (pagination.totalCount)??>
		<#if (pagination.totalCount>1)>
			<#assign firstPageUrl=url+"?page=1">
			<#assign prePageUrl=url+"?page="+(pagination.pageNo-1)>
			<#assign nextPageUrl=url+"?page="+(pagination.pageNo+1)>
			<#assign lastPageUrl=url+"?page="+pagination.totalCount>
			
			
			<div class="holder">
			<ul class="pager">  
			
                <li class="firstPage">  
                    <#if (pagination.pageNo>1)>  
                        <a href="${firstPageUrl}">首页</a>  
                    <#else>  
                        <span>首页</span>  
                    </#if>  
                </li>  
              
                <li class="prePage">  
                    <#if (pagination.pageNo>1)>  
                        <a href="${prePageUrl}">上一页</a>  
                    <#else>  
                        <span>上一页</span>  
                    </#if>  
                </li>  
				
				<@outPutPageNo pagination=pagination url=url showPageLinkCount=showPageLinkCount isShowMoreLI=isShowMoreLI/>
				
				<li class="nextPage">
					<#if (pagination.pageNo<pagination.totalCount)>
						<a href="${nextPageUrl}">下一页</a>
					<#else>
						<span>下一页</span>
					</#if>
				</li>
			
				<li class="lastPage">
					<#if (pagination.pageNo<pagination.totalCount)>
						<a href="${lastPageUrl}">尾页</a>
					<#else>
						<span>尾页</span>
					</#if>
				</li>
				
				<li class="pageInfo">
					共 ${pagination.totalCount} 页
				</li>
				
				<#if isNeedPageTo>
					<li class="pageTo">
						<form id="pagerForm" action="" method="get" onsubmit="return $.formValidator.pageIsValid('pagerForm');">
							转到第&nbsp;<input type="text" id="pageToNum" name="page" class="pageToNum" maxlength=4>&nbsp;页
							<input type="submit" id="submitButton4Page" class="formButton" value="确定" hidefocus="">
						</form>
						<script type="text/javascript">
							jQuery(document).ready(function(){
								//监听键盘，只允许输入数字和小数点
								$("#pageToNum").keypress(function(event) {
									var keyCode = event.which;
									if (keyCode == 46 || (keyCode >= 48 && keyCode <=57) || keyCode==8){
										return true;
									}
									return false;
								}).focus(function() {
									this.style.imeMode='disabled';
								});
							});
						</script>
					</li>
				</#if>
			</ul>
		</#if>
		<style>
		.pager{float: right; clear: both; margin-top: 5px;}
		.pager li{line-height: 18px; display: block; float: left; padding: 0px 5px; margin: 0px 3px; font-size: 12px; border: 1px solid #cccccc;}
		.pager li:hover{color: #ff9900; border: 1px solid #ff9900;}
		.pager li:hover a{color: #ff9900;}
		.pager li a{color: #464646;}
		.pager li span{color: #cfcfcf;}
		.pager li.currentPage{border: 1px solid #ff9900; background-color: #ff9900;}
		.pager li.currentPage span{font-weight: bold; color: #ffffff;}
		.pager li.pageInfo{color: #464646; border: none; background: none;}
		.pager li.pageTo{height:20px; color: #464646; border: none; background: none;}
		.pager li.pageTo input{line-height: 20px;}
		.pager li.pageTo input.pageToNum{width: 20px; height: 18px; margin-top: 0px; border: 1px solid #cccccc; display:table-cell; vertical-align:top;}
		.pager li.pageTo input.formButton {width: 40px; background: #F3F3F3;  height: 20px;}
		.pager li.pageTo input.formButton:hover {background: #ff9900; color: #ffffff;}
		</style>
	</#if>
</#macro>

<#--
	输出分页链接。如果当前页超过 显示的页数链接数 的一半，则当前页居中显示。例如：当前第10页，总共20页，那么显示第6~第15页分页链接，且第10页居中。
	pagination：分页对象。
	url：链接地址
	showPageLinkCount:显示的页数链接数
	isShowMoreLI:是否显示“<li>...</li>”提示更多还有更多页数0
-->
<#macro outPutPageNo pagination url showPageNum isShowMoreLI>
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
	<#--如果显示页数的连接数是2的倍数-->
	<#if (showPageNum%2==1)>
		<#assign StartIndexToCurrentPage=((showPageNum+1)/2)-1>
	<#else>
		<#assign StartIndexToCurrentPage=(showPageNum/2)-1>
	</#if>
	
	<#assign StartIndexToPageShowNum=(showPageNum-1)>
	
	<#if ((pagination.pageNo-StartIndexToCurrentPage) <= 1) || (pagination.totalCount<=showPageNum)>
		<#assign startIndex=1>
		<#assign isNeedStartMore=false>
	<#else>
		<#assign startIndex=(pagination.pageNo-StartIndexToCurrentPage)>
		<#assign isNeedStartMore=true>
	</#if>
	
	<#if ((startIndex+StartIndexToPageShowNum) < pagination.totalCount)>
		<#assign endIndex=startIndex+StartIndexToPageShowNum>
		<#assign isNeedEndMore=true>
	<#else>
		<#assign endIndex=(pagination.totalCount)>
		<#assign isNeedEndMore=false>
	</#if>

	<#if isNeedStartMore&&isShowMoreLI><li>...</li></#if>
	<#list startIndex..endIndex as i>
		<#if pagination.pageNo != i>
			<li>
				<a href="${url+"?page="+i}">${i}</a>
			</li>
		<#else>
			<li class="currentPage">
				<span>${i}</span>
			</li>
		</#if>
	</#list>
	<#if isNeedEndMore&&isShowMoreLI><li>...</li></#if>
</#macro>
