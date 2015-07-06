/**
*说明：本文件用来定义url接口，如接口有变动只需更改该文件中对应项即可
**/
/*
URL格式：主模块_子模块_操作
*/
var HOST='http://101.200.229.135:8080/api/admin/';
var CAPTCHA = 'http://101.200.229.135:8080/api/captcha?id='
var LOGIN = 'http://101.200.229.135:8080/api/login'
//var MEMBER_MEMBER_LIST = 'assets/data/member.json';
var MEMBER_MEMBER_LIST = 'user';
var MEMBER_GROUP_LIST = 'userGroup';
var MEMBER_ROLE = 'role';
var MEMBER_GROUP_UPDATEALL = 'userGroup/addAll';
//var MEMBER_GROUP_UPDATEALL = 'userGroup';
//var ARTICLE_ARTICLE_LIST='artical';
var APPOINT_APPOINT = 'appoint';
var MESSAGE = 'message';//留言管理
var CATEGORY_QUERY ='http://101.200.229.135:8080/api/category/';
var AREA_QUERY = 'http://101.200.229.135:8080/api/area/level/';
var CASE_CASE = 'case';
var MENU_MENU = 'menu';
var MENU_ARTICAL = 'artical';
var BRAND_BRAND = 'brand';
var BRAND_TYPE = 'brandType';

var LIVE_LIVE = 'live';
var LIVE_DETAIL = 'liveDetail';
var LIVE_DETAILS = 'details';
var USER_WORKER = 'worker';
var USER_COMMENTITEM= 'commentItem';
var COUPON_COUPONSDEF = 'couponsDef';
var COUPON_COUPON = 'coupons';
var COUPON_EVENT = 'couponsDef/events';

var ORDER_PACKAGE = 'package';//套餐

//主界面菜单链接
var MODULE_HOST_URL = "http://101.200.229.135:8080/admin/" ;
var INDEX = MODULE_HOST_URL +"fhzj_admin.html";
var MODULE_USR_URL =MODULE_HOST_URL + "memberlist.htm" ;
var MODULE_GROUP_URL = MODULE_HOST_URL + "membergroup.htm" ;
var MODULE_ROLE_URL = MODULE_HOST_URL + "rolelist.htm" ;
var MODULE_MSG_URL = MODULE_HOST_URL + "messagelist.htm";
var MODULE_APPOINT_URL = MODULE_HOST_URL + "appointlist.htm";
var MODULE_CASE_URL = MODULE_HOST_URL + "caselist.htm";
var MODULE_LIVE_URL = MODULE_HOST_URL + "livelist.htm";
var MODULE_COUPON_URL = MODULE_HOST_URL + "couponlist.htm";
var MODULE_COUPON_DEF = MODULE_HOST_URL + "couponsDef.htm";
var MODULE_PACKAGE_URL = MODULE_HOST_URL + "packagelist.htm";








