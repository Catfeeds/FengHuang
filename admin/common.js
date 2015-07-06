//���ڹ��켯�϶�Ӧ��viewmodel
/*function PagingViewModel(data){
	//���checked���ԣ�Ĭ��Ϊfalse
	var results = data.result;
	for(var i in results){
		results[i].selected = false;
	}
	
	data.result = results;
	self = this;
	self.size = ko.observable(data.size);
	self.totalCount = ko.observable(data.totalCount);
	self.totalPages = ko.observable(data.totalPages);
	self.page = ko.observable(data.page);
	self.result = ko.observableArray(data.result);
}*/
//���ڹ��쵥�������Ӧ��viewmodel

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
	if(id === null || id === undefined || id ==='')
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
function isEmpty(data){
	if(data === null || data===undefined||data===''){
			return true;
	}
	return false;
}
//�жϻ�ȡ�������Ƿ���ȷ
function isErrorData(data){
	if(isEmpty(data)){
		alert("�������ݴ���")
	}else if(data.status === 'ERROR'){
		alert(data.error+'����('+data.code+')��������Ϣ��'+data.errorDescription);
	}else if(data.status === 'OK'){
		return false;
	}
	return true;
}
function genPaginator(total,pSize,curPage,handlePageChange,visibleP){
	
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
//��ȡ���ݼ���
function getData(moduleUrl,param,callback){
	
	var url = genUrl(moduleUrl);
	requestByGetJSON(url,param,callback);
}
//����id��ȡ����
function getDataById(moduleUrl,id,callback){
	var url = genUrl(moduleUrl,id);
	requestByGetJSON(url,null,callback);
}
function getUrlParam(paramStr,name) {
            var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //����һ������Ŀ�������������ʽ����
            var r = paramStr.substr(1).match(reg);  //ƥ��Ŀ�����
            if (r != null) return unescape(r[2]); return null; //���ز���ֵ
        }
function friendlyTip(data){
	
	alert(data.status+','+data.result);
}
