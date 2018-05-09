$(function(){
	var map;
    var zoom = 14;
    map = new T.Map("map_container");
    //设置显示地图的中心点和级别
    map.centerAndZoom(new T.LngLat(118.6635,31.9438), zoom);
    //允许鼠标滚轮缩放地图
    map.enableScrollWheelZoom();
	
	var typeNameCode = new Array();
	
	$.ajax({
		url : "/map/action/coordinate",
		type : "post",
		data : {},
		error : function(){
			
		},
		success : function(data){
			var data = JSON.parse(data);
			console.info(data);
			if(data.code == "0000"){
				var result = data.data;
				for(var name in result){
					typeNameCode.push(name);
					var typeList = result[name];
					for(var i=0;i<typeList.length;i++){
						new tipmarket(typeList[i],map);
					}
					
				}
			}
			
		}
	});

//    var imageURL = "http://t0.tianditu.cn/img_w/wmts?" +
//        "SERVICE=WMTS&REQUEST=GetTile&VERSION=1.0.0&LAYER=img&STYLE=default&TILEMATRIXSET=w&FORMAT=tiles" +
//        "&TILEMATRIX={z}&TILEROW={y}&TILECOL={x}";
//    //创建自定义图层对象
//    var lay = new T.TileLayer(imageURL, {minZoom: 1, maxZoom: 18});
//    //将图层增加到地图上
//    map.addLayer(lay);
});
(function(window){
	var tipWindow = function(data){
		this.tipWin = new T.InfoWindow();
		this.data = data;
		this.xml = "";
		this.build = function(){
			return this.tipWin;
		};
		this.init();
	};
	tipWindow.prototype = {
		init : function(){
			this.xml += "<div class='tipcontainer'>";
			this.xml += "<div class='topspan'>";
			this.xml += "<img src='"+this.data.pic+"'>";
			this.xml += "<p>"+this.data.buildingname+"</p>";
			this.xml += "<p>"+this.data.telephone+"</p>";
			this.xml += "<div class='clear'></div>"
			this.xml += "</div>";
			this.xml += "<div class='bottomspan'>";
			this.xml += "<p>"+this.data.address+"</p>";
			this.xml += "</div>";
			this.xml += "</div>";
			this.tipWin.setContent(this.xml);
		}
	};
	window.tipWindow = tipWindow;
	
	var tipmarket = function(data,map){
		this.map = map;
		this.market = null;
		this.data = data;
		this.init();
	};
	tipmarket.prototype = {
		init : function(){
			var obj = this;
			if(this.data.list.length == 1){
				var site = new T.LngLat(this.data.list[0].longitude,this.data.list[0].latitude);
				this.market = new T.Marker(site);
				if(this.market != null){
					this.map.addOverLay(this.market);
					//绑定事件
					this.market.addEventListener("click", function () {
						obj.market.openInfoWindow(new tipWindow(obj.data).build());
		            });
				}
			}else if(this.data.list.length > 1){
				var points = [];
				for(var i=0;i<this.data.list.length;i++){
					points.push(new T.LngLat(this.data.list[i].longitude,this.data.list[i].latitude));
				}
				var polygon = new T.Polygon(points,{
		            color: "blue", weight: 3, opacity: 0.5, fillColor: "#FFFFFF", fillOpacity: 0.5
		        });
				polygon.addEventListener("click", function () {
					polygon.openInfoWindow(new tipWindow(obj.data).build());
	            });
				this.map.addOverLay(polygon);
			}else{
				
			}
		}
	};
	window.tipmarket = tipmarket;
})(window);