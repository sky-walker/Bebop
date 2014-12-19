package com.qiniu.qbox;

import com.qiniu.qbox.auth.DigestAuthClient;
import com.qiniu.qbox.rs.PublishRet;
import com.qiniu.qbox.rs.PutAuthRet;
import com.qiniu.qbox.rs.PutFileRet;
import com.qiniu.qbox.rs.RSClient;
import com.qiniu.qbox.rs.RSService;
import com.qiniu.qbox.rs.StatRet;

public class Test {

	public static void main(String[] args) {
		String bucketName  = "bebop";
		DigestAuthClient conn = new DigestAuthClient();
		RSService rs = new RSService(conn, bucketName);
		try {
//			PutAuthRet putAuthRet = rs.putAuth();
//			System.out.println(putAuthRet.getUrl());
//			System.out.println(putAuthRet.getExpiresIn());
//			String uploadUrl = putAuthRet.getUrl();
//			
//			PutFileRet putFileRet = RSClient.putFile(uploadUrl, bucketName, "a.png", "", "e:/a.png", "", null);
//			StatRet statRet = rs.stat("a.png");
//			System.out.println(statRet);
			
			PublishRet publishRet = rs.publish(Config.DEMO_DOMAIN + "/" + "bebop");
			System.out.println(publishRet.statusCode);
			System.out.println(publishRet.ok());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
