
$(function(){
	var g = {};
	g.phone = "";
	g.imgCodeId = "";
	g.sendCode = false;
	g.sendCode2 = false;
	g.sendTime = 60;
	g.username = Base.userName;
	g.token = Utils.offLineStore.get("token",false);
	g.orderId = Utils.getQueryString("orderId") || "";
	g.orderResult = Utils.getQueryString("result") || "";
	g.payprice = 0;
	g.totalPage = 1;
	g.currentPage = 1;
	g.paseSize = 20;
	g.httpTip = new Utils.httpTip({});
	g.listdata = [];
	g.userprofile = Utils.offLineStore.get("login_userprofile",false) || "";
	//验证登录状态
	g.loginStatus = Utils.getUserInfo();
	g.reserveStatus = false;
	if(!g.loginStatus){
		location.replace("index.html");
	}
	if(g.orderResult == "false"){
		//支付失败
		var html = [];
		html.push('<div style="margin:50px 0 0 50px;padding:10px;text-align:center;vertical-align:middle;border:4px solid #ff0000;-moz-border-radius:50%;-webkit-border-radius:50%;border-radius:50%; height:100px;width:100px">');
		html.push('<i class="fa fa-close" style="color:#ff0000;font-size:70px"></i>')
		html.push('</div>')
		$("#statusdiv").html(html.join(''));
		$("#errorbtn").show();
	}
	$("#statusdiv").show();

	$("#backbtn").bind("click",back);

	getOrderDetail();

	function back(){
		location.replace("center/c_order.html?token=" + g.token + "&p=7");
	}

	function getOrderDetail(){
		//~ token:用户凭据
		//~ id：订单id，
		var condi = {};
		condi.token = g.token;
		condi.id = g.orderId;
		if(g.loginStatus && g.orderId !== ""){
			sendGetOrderDetailsHttp(condi);
		}
	}
	function sendGetOrderDetailsHttp(condi){
		var url = Base.order + "/" + condi.id;
		g.httpTip.show();
		$.ajax({
			url:url,
			data:condi,
			type:"GET",
			dataType:"json",
			context:this,
			global:false,
			success: function(data){
				console.log("sendGetOrderDetailsHttp",data);
				var status = data.status || "";
				if(status == "OK"){
					changeOrderDetailsHtml(data.result);
				}
				else{
					var msg = data.error || "";
					Utils.alert("获取订单详情错误" + msg);
				}
				g.httpTip.hide();
			},
			error:function(data){
				g.httpTip.hide();
			}
		});
	}
	function changeOrderDetailsHtml(data){
		//~ id:id
		//~ price:售出的实际价格
		//~ status:处理状态
		//~ count:数量
		//~ name:商品名称
		//~ type:订单的商品类型
		//~ mainPic:主图
		//~ shopid:店铺id
		//~ shop:店铺名称
		//~ good:具体的商品信息，目前这个就是购买的套餐的信息
		var obj = data || {};
		var price = obj.price || "";
		g.payprice = price;
		var orderId = obj.code;
		var payTime = obj.payTime || "";
		var html = [];
		if(g.orderResult == "true"){
			html.push('<li style="font-weight:800;font-size:20px">您已支付成功</li>');
		}
		else{
			html.push('<li style="font-weight:800;font-size:20px;color:red;">支付失败</li>');
		}
		html.push('<li style="font-size:16px">交易时间：'  + payTime + '</li>');
		html.push('<li class="u_li">支付金额：' + price + '元</li>');
		html.push('<li class="u_li">流水号：<b style="color:#83bd39">' + orderId + '</b></li>');
		html.push('<li class="u_li">');
		html.push('<br/>本页面可作为付款凭证。');
		html.push('</li>');
		var msg = "尊敬的" + g.username + "用户，您好，您已成功支付定金" + price + "元，同时我们为您预约了厦门体验馆到店体验服务，详情请致电400-968-9088具体咨询。";
		html.push('<li class="u_li">提示：' + msg + '</li>');
		$("#orderdetail").html(html.join(''));
		$("#orderdetail").show();
	}

});









