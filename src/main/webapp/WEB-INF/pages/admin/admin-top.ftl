<!-- header start ================================================== -->
<link href="/styles/bootstrap.css" rel="stylesheet" type="text/css" />
<link href="/styles/bebop.css" rel="stylesheet" type="text/css" />
<link href="/styles/jquery.qtip.css" rel="stylesheet" type="text/css" />
<script src="/scripts/jquery.js" type="text/javascript"></script>
<script src="/scripts/jquery.qtip.min.js" type="text/javascript"></script>
<script src="/scripts/global.js" type="text/javascript"></script>
<#assign currentUser = Session["OSEYRUSER"]>
<div id="header">
    <div class="navbar navbar-static-top">
      <div class="navbar-inner">
        <div class="container">
          <a class="brand" href="#">Bebop系统管理后台</a>
          <div class="nav-collapse collapse">
            <ul class="nav pull-right">
              <li><a href="/blog/${currentUser.name}"><div class="miniface-20"><img src='${currentUser.portraint}' width="20px" height="20px" class="img-rounded" /></div>&nbsp;${currentUser.nickname}</a></li>
              <li>
              	<#assign ose_unReadRemindCount=JspTaglibs["/WEB-INF/UnReadRemindCountTag.tld"]/>
   			  	<@ose_unReadRemindCount.unReadRemindCount id='${currentUser.id}'/>
              </li>
              <li>
              	<#assign ose_unReadMessageCount=JspTaglibs["/WEB-INF/UnReadMessageCountTag.tld"]/>
   				<@ose_unReadMessageCount.unReadMessageCount id='${currentUser.id}'/>
              </li>
              <li><a href="/admin/logout?gotopage=home" class="logout"><i class="icon-off"></i>&nbsp;退出</a></li>
            </ul>
          </div>
        </div>
      </div>
    </div>
</div>
<script language='javascript'>
	$(function() {
		$('#remind_count').qtip({position: {my: 'top center', at: 'bottom center'},show: 'click',hide: {event: 'click unfocus'},content: {text: "<div class='remind_loading'></div>",ajax: {url: '/remind/last',type: 'POST',data: {},once: false,success: function(data, status) {this.set('content.text', data);ajax_post_json("/remind/unread",{id:${currentUser.id}},function(data) {if(data.status) {$("#remind_count").html(data.msg);$("#remind_count").attr('class','');}});}}}});
		$('#blog_setting')
		.mouseover(function() {$("#my_toolbar").stop(false, true).show();$('#my_toolbar').show();})
		.mouseout(function() {$("#my_toolbar").stop(false, true).hide();$('#my_toolbar').hide();});
	});
</script>
<!-- header end ================================================== -->