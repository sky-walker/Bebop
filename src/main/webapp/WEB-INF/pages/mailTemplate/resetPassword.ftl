<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<style>
	html{word-wrap:break-word;}
	body{font-size:16px;font-family:arial,verdana,sans-serif;line-height:1.666;padding:8px 10px;margin:0;}
	pre {
		white-space: pre-wrap; 
		white-space: -moz-pre-wrap; 
		white-space: -pre-wrap;
		white-space: -o-pre-wrap;
		word-wrap: break-word;
	}
</style>
<head>
				
				<title>Bebop帐号中心 密码重置 验证邮件</title>
				<style type="text/css">
				<!--
				body{background:white}
				a{color:#06f;text-decoration:none}
				a:hover{color:#108AC6;text-decoraiton:underline}
				*{margin:0;padding:0}
				.red{color:#f00;font-family:Arial}
				.main {margin:0 auto;width:696px;padding-top:18px}
				.mn1{height:36px;background:#108AC6;font-size:20px;padding:15px 0 0 25px;line-height:22px;border:1px solid #ddd;color:#FFF}
				.mn2{padding-top:20px;border:1px solid #ddd;border-top:none;font-size:14px;background:#fff}
				.mn2 p{text-indent:2em;padding-bottom:12px;width:620px;font-size:14px;font-family:"Courier New", Courier, monospace;line-height:20px;padding-left:36px}
				h3{height:28px;font-size:14px;padding-left:36px}
				.foot{padding-top:13px;height:30px;background:#108AC6;color:#FFF;text-align:center;font-family:Arial;}
				p.sign{text-align:right;height:30px;width:605px}
				-->
				</style>
				</head>
				<body>
				<div class="main">
					<div class="mn1"><strong>Bebop帐号中心</strong></div>
					<div class="mn2" id="QQmailNormalAtt">
						<p>Bebop帐号密码重置！</p>
						<p><strong>请点击以下链接启用新的密码：</strong><br>　
						<a href="${activateLink}?email=${email}&activateCode=${activateCode}" target="_blank">${activateLink}?email=${email}&activateCode=${activateCode}</a><br>　　(如链接无法点击，可以将此链接复制到浏览器地址栏打开页面) </p>
						<p>在密码启用之后, 您将可以使用此新密码登入：</P>
						<p>密码：<strong class="red">${resetPasswordKey}</strong></P>
						<p>您可以在您设置功能中更改登录密码.</P>
						<p><strong>温馨提示：</strong></p>
						<p>以上链接<strong class="red">仅72小时内有效</strong>，过期后需重新发送邮件进行密码重置，请尽快点击哦！</p>
						<p>本邮件为系统自动生成，请勿回复。</p>
						<p class="sign">Bebop</p>
						<div class="foot">Copyright © 2013 Bebop Corp. All Rights Reserved　客服电话：110</div>
					</div>
				</div>
				</body>
				</html>
