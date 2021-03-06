
var MATERIAL_BRAND = 'brand';
var MATERIAL_PRODUCT = 'product';
var MATERIAL_MATERIAL = 'material';
var MATERIAL_TYPE = 'material/types';
var SYSTEM_NAVIGATION = 'navigation';
var TEMPLATE_CAROUSEL = 'carousel';
var TEMPLATE_TEMPLATE = 'template';
var ORDER_DRAWBACK = 'drawback';
var PACKAGE_SPACE = 'decorateSpace';
var DECORATE_TECH = 'decorateTechnology';
var MUSEUM_MUSEUM = 'experienceMuseum';
var MUSEUM_APPOINT= 'experienceAppoint';
var MUSEUM_SIGNUP= 'signup';
var SYSTEM_ADMIN = 'systemUser';
var AREA_CITY = 'area/level/CITY';
//var AREA_CITY = 'area';

function PagingJson2ViewModel(data){

	return ko.mapping.fromJS(data);
}

function genUrl1(baseUrl,sn,pageIndex,pageNum){
	var url =HOST+ baseUrl+'?token='+token+'&page='+pageIndex+'&size=';
	if (pageNum!= 'undefined' && pageNum!= null && pageNum != '')
		
	return url+pageNum;
	else return url+pSize;
}
function genUrl(baseUrl,id){
	if(id=='' || typeof id == 'undefined')
	{	return url =HOST+ baseUrl;}
	else 
	{	return url =HOST+ baseUrl+'/'+id;}
}
ko.observableArray.fn.filterByProperty = function(propName, matchValue) {
    return ko.pureComputed(function() {
        var allItems = this(), matchingItems = [];
        for (var i = 0; i < allItems.length; i++) {
            var current = allItems[i];
            if (ko.unwrap(current[propName]) === matchValue)
                matchingItems.push(current);
        }
        return matchingItems;
    }, this);
}
function filterSelected(items){
	var selectedIds = [];
	for(var i in items){
		if(items[i].selected){
			
			selectedIds.push(items[i].id)
		}
	}
	return selectedIds;
}

//判断获取的数据是否正确
function isErrorData(data){
	if(data.status === 'ERROR'){
		alert(data.error+'，描述信息：'+data.errorDescription);
		if(data.code === 11){
			window.parent.location.href = "login.html";
		}
		
	}else if(data.status === 'OK'){
		return false;
	}
	return true;
}
function genPaginator(total,pSize,curPage,handlePageChange,visibleP){
	if(total === 0) return;//防止报错
	if(visibleP === null) 
		visibleP = 10;
	
	 $('.pageList').jqPaginator({
        totalCounts: total,
        pageSize : pSize,
        visiblePages: visibleP,
        currentPage: curPage,
        activeClass:'active',
        disableClass:'disabled',
        first: '<a href="javascript:;">&lt;&lt;</a>',
        prev: '<a href="javascript:;">&lt;</a>',        
        page: '<span ><a href="javascript:;" class="num">{{page}}</a></span>',
        next: '<a href="javascript:;">&gt;</a>',
        last: '<a href="javascript:;">&gt;&gt;</a>',
        onPageChange: handlePageChange
    });
}
//获取数据集合
function getData(moduleUrl,param,callback){
	//alert(moduleUrl);
	var url = genUrl(moduleUrl);
	requestByGetJSON(url,param,callback);
}
//根据id获取数据
function getDataById(moduleUrl,id,callback){
	var url = genUrl(moduleUrl,id);
	requestByGetJSON(url,null,callback);
}
function getUrlParam(paramStr,name) {
            var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
            var r = paramStr.substr(1).match(reg);  //匹配目标参数
            if (r != null) return unescape(r[2]); return null; //返回参数值
        }
function friendlyTip(data){
	
	alert(data.status+','+data.result);
}
