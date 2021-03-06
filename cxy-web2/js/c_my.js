/**
 * file:个人资料
 * author:chenxy
 * date:2015-06-05
*/
//页面初始化
$(function(){
	var g = {};
	g.phone = "";
	g.imgCodeId = "";
	g.sendCode = false;
	g.sendTime = 60;
	g.isBind = true;
	g.token = Utils.getQueryString("token");
	g.page = Utils.getQueryString("p") - 0;
	g.httpTip = new Utils.httpTip({});

	//验证登录状态
	var loginStatus = Utils.getUserInfo();
	if(!loginStatus){
		//未登录
		location.replace("login.html");
	}
	else{
		getUserInfo();
		sendMyInfoCountsHttp();
	}

	$("#updatebtn").bind("click",updateUserInfo);
	$("#loginoutbtn").bind("click",loginOut);
	$("#phonetext").bind("blur",getImgCode);
	$("#updatepwdcodeimg").bind("click",getImgCode);
	$("#sendbtn").bind("click",getPhoneCode);
	$("#bindbtn").bind("click",bindPhone);

	//安全退出
	function loginOut(){
		Utils.offLineStore.remove("userinfo",false);
		location.href = "login.html";
	}

	//获取图形验证码
	function getImgCode(){
		var img = $("#updatepwdcodeimg");
		var tel = $("#phonetext").val();
		var reg = /^1[3,5,7,8]\d{9}$/g;
		if(img.length > 0 && reg.test(tel)){
			g.codeId = tel;
			console.log(tel);
			$("#updatepwdcodeimg").attr("src",Base.imgCodeUrl + "?id=" + g.codeId);
			clearTimeout(g.tout);
			g.tout = setTimeout(function(){
				getImgCode();
			},60000);
		}
	}

	function getPhoneCode(){
		var p = $("#phonetext").val() || "";
		var imgCode = $("#phoneimgcode").val() || "";
		if(p !== ""){
			var reg = /^1[3,5,7,8]\d{9}$/g;
			if(reg.test(p)){
				if(imgCode !== ""){
					g.phone = p;
					if(!g.sendCode){
						sendGetCodeHttp(imgCode);
					}
				}
				else{
					console.log("输入图形验证码");
					$("#phoneimgcode").focus();
				}
			}
			else{
				alert("手机输入不合法");
				$("#phonetext").focus();
			}
		}
		else{
			console.log("没填手机号");
			$("#phonetext").focus();
		}
	}

	//请求验证码
	function sendGetCodeHttp(imgCode){
		g.httpTip.show();
		var url = Base.getCodeUrl;
		var condi = {};
		condi.mobile = g.phone;
		condi.captcha = imgCode;
		$.ajax({
			url:url,
			data:condi,
			type:"POST",
			dataType:"json",
			context:this,
			global:false,
			success: function(data){
				console.log(data);
				g.httpTip.hide();
				var status = data.status || "";
				if(status == "OK"){
					g.sendCode = true;
					$("#sendbtn").html("60秒后重新发送");
					setTimeout(function(){
						resetGetValidCode();
					},1000);
				}
				else{
					alert("验证码获取失败");
				}
			},
			error:function(data){
				g.httpTip.hide();
			}
		});
	}

	//重新获取验证码
	function resetGetValidCode(){
		g.sendTime = g.sendTime - 1;
		if(g.sendTime > 0){
			$("#sendbtn").html(g.sendTime + "秒后重新发送");
			setTimeout(function(){
				resetGetValidCode();
			},1000);
		}
		else{
			$("#sendbtn").html("重新发送");
			g.sendTime = 60;
			g.sendCode = false;

			//重新获取图形验证码,1分钟有效
			getImgCode();
			$("#phoneimgcode").val("");
			$("#phoneimgcode").focus();
		}
	}

	//绑定手机号
	function bindPhone(){
		//token:用户凭据
		//mobile：手机号码
		//validater:短信验证码
		var p = $("#phonetext").val() || "";
		var bindcode = $("#bindcode").val() || "";
		if(g.isBind){
			if(p !== ""){
				var reg = /^1[3,5,7,8]\d{9}$/g;
				if(reg.test(p)){
					if(bindcode !== ""){
						sendBindPhoneHttp(p,bindcode);
					}
					else{
						console.log("输入验证码");
						$("#bindcode").focus();
					}
				}
				else{
					alert("手机输入不合法");
					$("#phonetext").focus();
				}
			}
			else{
				console.log("没填手机号");
				$("#phonetext").focus();
			}
		}
		else{
			alert("解绑没有接口");
		}
	}

	//获取个人资料
	function getUserInfo(){
		var token = g.token;
		sendGetUserInfoHttp(token);
	}
	//更新个人资料
	function updateUserInfo(){
		var condi = {};
		condi.token = g.token;
		condi.username = g.username;
		//昵称
		condi.cnname = $("#nikename").val();
		//真实姓名
		condi.realName = $("#validname").val();
		//英文名
		condi.ename = $("#ename").val();
		//个人简介
		condi.intro = $("#message").val();
		//电子邮箱
		condi.email = $("#emailtext").val();
		//手机号
		//condi.phone = $("#phonetext").val();
		//QQ号
		//condi.qq = $("#qqtext").val();
		//微信
		//condi.weixin = $("#weixintext").val();
		//生日
		//condi.birthDay = $("#birthday").val();
		//行业
		condi.trade = $("#profession").val();
		//现居住地
		condi.address = $("#address").val();



		//性别1男2女
		condi.sexId = "404040e64dd26ab5014dd26ac61f0013";
		var sexRadio = $("#inlineRadio2")[0].checked;
		if(sexRadio){
			//condi.sex = 2;
			condi.sexId = "404040e64dd26ab5014dd26ac64e0014";
		}
		//血型
		condi.bloodTypeId = $("#bloodgroup").val();
		//星座
		condi.constellationId = $("#constellation").val();
		console.log(condi);
		sendUpdateUserInfoHttp(condi);
	}
	//修改个人资料
	function setUserInfoHtml(data){
		var obj = data.user || {};
		var nikeName = obj.cnname || "";
		var validName = obj.realName || "";
		var eName = obj.ename || "";
		var sex = obj.sex || "404040e64dd26ab5014dd26ac61f0013";
		var message = obj.intro || "";
		var email = obj.email || "";
		var phone = obj.mobile || "";
		var qq = obj.qqnum || "";
		var weixin = obj.weixinnum || "";
		var birthday = obj.birthDay || "";
		var profession = obj.trade || "";
		var address = obj.address || "";
		var constellation = obj.constellation || {};
		var bloodgroup = obj.bloodType || {};

		g.username = obj.username;
		Base.userName = obj.username;

		//获取验证码
		//getImgCode();

		//昵称
		$("#nikename").val(nikeName);
		//真实姓名
		$("#validname").val(validName);
		//英文名
		$("#ename").val(eName);
		//性别1男2女
		if(sex !== "404040e64dd26ab5014dd26ac61f0013"){
			if($("#inlineRadio2").length > 0){
				$("#inlineRadio2")[0].checked = true;
			}
		}
		//个人简介
		$("#message").val(message);
		//电子邮箱
		$("#emailtext").val(email);
		if(phone != ""){
			//手机号
			$("#phonetext").val(phone);
			$("#bindbtn").html("解绑");
			g.isBind = false;
		}
		//QQ号
		$("#qqtext").val(qq);
		//微信
		$("#weixintext").val(weixin);
		//生日
		$("#birthday").val(birthday);
		//行业
		$("#profession").val(profession);
		//现居住地
		$("#address").val(address);
		//星座
		$("#constellation").val(constellation.id);
		//血型
		$("#bloodgroup").val(bloodgroup.id);


		var li = [];
		//li.push('<li>ID：' + obj.username + '</li>');
		li.push('<li>' + obj.username + '</li>');
		li.push('<li>5星级用户</li>');
		$("#user_ul").html(li.join(''));

		var li = [];
		var loginIp = obj.loginip || "";
		var loginTime = obj.loginTime || "";
		var regIp = obj.regIp || "";
		var regTime = obj.regTime || "";
		var integra = obj.integra || "";
		li.push('<li>登录 IP<span class="pull-right">' + loginIp + '</span></li>');
		li.push('<li>注册时间<span class="pull-right">' + loginTime + '</span></li>');
		li.push('<li>登录时间<span class="pull-right">' + regTime + '</span></li>');
		li.push('<li>用户积分<span class="pull-right">' + integra + '分</span></li>');
		$("#logintime").html(li.join(''));

		//setUserFunHtml();


	}

	function setUserFunHtml(obj){
		//style="background:#b9090e;color:#fff;"
		var h = 'style="background:#b9090e;color:#fff;"';
		var comments = obj.comments || 0;
		var coupons = obj.coupons || 0;
		var messages = obj.messages || 0;
		var appoints = obj.appoints || 0;
		var collects = obj.collects || 0;

		var html = [];
		html.push('<ul class="blog_category">');
		html.push('<li><a href="c_my.html?token=' + g.token + '&p=1" ' + (g.page == 1 ? h : "") + ' >个人信息 </a></li>');
		html.push('<li><a href="c_safe.html?token=' + g.token + '&p=2" ' + (g.page == 2 ? h : "") + ' >安全设置 </a></li>');
		html.push('<li><a href="c_home.html?token=' + g.token + '&p=3" ' + (g.page == 3 ? h : "") + '  >房屋信息 </a></li>');
		html.push('<li><a href="c_ing.html?token=' + g.token + '&p=4" ' + (g.page == 4 ? h : "") + '  >家装进度 </a></li>');
		html.push('<li><a href="c_message.html?token=' + g.token + '&p=5" ' + (g.page == 5 ? h : "") + '  >我的留言 <span class="badge">' + messages + '</span></a></li>');
		html.push('<li><a href="c_sub.html?token=' + g.token + '&p=6" ' + (g.page == 6 ? h : "") + '  >我的预约 <span class="badge">' + appoints + '</span></a></li>');
		html.push('<li><a href="c_order.html?token=' + g.token + '&p=7" ' + (g.page == 7 ? h : "") + '  >我的订单 <span class="badge"></span></a></li>');
		html.push('<li><a href="c_comment.html?token=' + g.token + '&p=8" ' + (g.page == 8 ? h : "") + '  >我的评论 <span class="badge">' + comments + '</span></a></li>');
		html.push('<li><a href="c_fav.html?token=' + g.token + '&p=9" ' + (g.page == 9 ? h : "") + '  >我的收藏 <span class="badge">' + collects + '</span></a></li>');
		html.push('<li><a href="c_coupon.html?token=' + g.token + '&p=10" ' + (g.page == 10 ? h : "") + '  >我的优惠券 <span class="badge">' + coupons + '</span></a></li>');
		html.push('</ul>');

		$("#userfunul").html(html.join(''));
	}


	//注册
	function regBtnUp(evt){
		var userName = $("#inputEmail3").val() || "";
		var usePwd = $("#inputPassword3").val() || "";
		var phone = $("#inputPhone3").val() || "";
		var imgCode = $("#inputImgCode3").val() || "";
		var validCode = $("#inputCode3").val() || "";

		var regEMail = /^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/;
		var regPhone = /^1[3,5,7,8]\d{9}$/;
		var regFont = /^([\u4E00-\u9FA5|\w\-])+$/;
		if(regEMail.test(userName) || regPhone.test(userName) || regFont.test(userName)){
			if(userName !== "" && usePwd !== "" && phone !== "" && imgCode !== "" && validCode !== ""){
				sendRegHttp(userName,usePwd,validCode);
				//http://101.200.229.135:8080/api/regist?username=ytm&password=123456&mobile=18612444099&validater=3967
			}
			else{
				alert("账户信息未填");
			}
		}
		else{
			alert("用户名输入错误,请输入邮箱或者手机号");
			$("#inputEmail3").focus();
		}

	}

	//获取个人资料请求
	function sendGetUserInfoHttp(token){
		g.httpTip.show();
		var url = Base.profileUrl;
		var condi = {};
		condi.token = token;
		$.ajax({
			url:url,
			data:condi,
			type:"GET",
			dataType:"json",
			context:this,
			global:false,
			success: function(data){
				console.log(data);
				g.httpTip.hide();
				var status = data.status || "";
				if(status == "OK"){
					setUserInfoHtml(data.result);
					Utils.offLineStore.set("login_userprofile",JSON.stringify(data.result.user),false);
				}
				else{
					var msg = data.error || "";
					alert("获取个人信息错误:" + msg);
					location.href = "login.html";
				}
			},
			error:function(data){
				g.httpTip.hide();
			}
		});
	}

	//更新个人资料请求
	function sendUpdateUserInfoHttp(obj){
		g.httpTip.show();
		var url = Base.profileUrl;
		$.ajax({
			url:url,
			data:obj,
			type:"POST",
			dataType:"json",
			context:this,
			global:false,
			success: function(data){
				console.log(data);
				g.httpTip.hide();
				var status = data.status || "";
				if(status == "OK"){
					Utils.offLineStore.set("login_userprofile",JSON.stringify(data.result),false);
					alert("修改个人资料成功");
				}
				else{
					alert("修改个人资料失败");
				}
			},
			error:function(data){
				g.httpTip.hide();
			}
		});
	}

	//绑定手机号
	function sendBindPhoneHttp(phone,code){
		g.httpTip.show();
		var url = Base.bindMobile;
		//token:用户凭据
		//mobile：手机号码
		//validater:短信验证码
		var condi = {};
		condi.token = g.token;
		condi.mobile = phone;
		condi.validater = code;
		$.ajax({
			url:url,
			data:condi,
			type:"POST",
			dataType:"json",
			context:this,
			global:false,
			success: function(data){
				console.log(data);
				g.httpTip.hide();
				var status = data.status || "";
				if(status == "OK"){
					alert("绑定手机成功");
				}
				else{
					var msg = data.error;
					alert("手机绑定错误:" + msg);
				}
			},
			error:function(data){
				g.httpTip.hide();
			}
		});
	}


	function sendMyInfoCountsHttp(){
		g.httpTip.show();
		var url = Base.unreads;
		//token:用户凭据
		var condi = {};
		condi.token = g.token;
		$.ajax({
			url:url,
			data:condi,
			type:"GET",
			dataType:"json",
			context:this,
			global:false,
			success: function(data){
				console.log(data);
				g.httpTip.hide();
				var status = data.status || "";
				if(status == "OK"){
					setUserFunHtml(data.result);
				}
				else{
					var msg = data.error;
					Utils.alert("获取未读消息错误:" + msg);
				}
			},
			error:function(data){
				g.httpTip.hide();
			}
		});
	}
});