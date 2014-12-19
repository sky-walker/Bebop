var userspage = 2;
var postspage = 2;
var feedspage = 2;
var usersloading = false;
var postsloading = false;
var feedsloading = false;

function loadData() {
	if (isUserAtBottom()) {
		getUsersData();
		getPostsData();
		getFeedsData();
	}
}	

function isUserAtBottom() {return ((($(document).height() - $(window).height()) - $(window).scrollTop()) <= 50) ? true : false;}

function getUsersData() {
	if(!usersloading) {
		usersloading = true;
		$(window).unbind('scroll');
		$('#usersloading').show();
		$.get('/index/users?page='+userspage,{},function(response){
			$('#usersloading').hide();
			$('#usersitems').append(response);
			if(response.length>0) {
				$(window).scroll(loadData);
				userspage++;
			}
			usersloading = false;
		});
	}
}

function getPostsData() {
	if(!postsloading) {
		postsloading = true;
		$(window).unbind('scroll');
		$('#postsloading').show();
		$.get('/index/posts?page='+postspage,{},function(response){
			$('#postsloading').hide();
			$('#postsitems').append(response);
			if(response.length>0) {
				$(window).scroll(loadData);
				postspage++;
			}
			postsloading = false;
		});
	}
}
function getFeedsData() {
	if(!feedsloading) {
		feedsloading = true;
		$(window).unbind('scroll');
		$('#feedsloading').show();
		$.get('/index/feeds?page='+feedspage,{},function(response){
			$('#feedsloading').hide();
			$('#feedsitems').append(response);
			if(response.length>0) {
				$(window).scroll(loadData);
				feedspage++;
			}
			feedsloading = false;
		});
	}
}