var map;
function initMap()
{
	// Create a map object and specify the DOM element for display.
	map = new google.maps.Map(document.getElementById('map'), {
		center : {
			lat : 45.352014,
			lng : -75.938823
		},
		zoom : 17
	});

	// var markers =
	// [{'position':{'lat':45.351578,'lng':-75.939724},'title':'HelloWorld!'},{'position':{'lat':45.353039,'lng':-75.939859},'title':'Jianfeng!'},{'position':{'lat':45.353160,'lng':-75.940975},'title':'Liana!'},{'position':{'lat':45.352180,'lng':-75.940750},'title':'Robert!'},{'position':{'lat':45.352014,'lng':-75.938823},'title':'Taolei!'}];
	/*
	 * var markers = [{ "position" : { "lat" : 45.351578, "lng" : -75.939724 }, "title" : 'aaa!' },{
	 * "position" : { "lat" : 45.353039, "lng" : -75.939859 }, "title" : 'bbb!' },{ "position" : {
	 * "lat" : 45.353160, "lng" : -75.940975 }, "title" : 'ccc!' },{ "position" : { "lat" :
	 * 45.352180, "lng" : -75.940750 }, "title" : 'ddd!' },{ "position" : { "lat" : 45.352014, "lng" :
	 * -75.938823 }, "title" : 'eee!' } ];
	 */

	// addMarkers(markers);
	// var strMarkers = JSON.stringify(markers);
	// addMarkersFromString(strMarkers);
}

//Create a marker and set its position.
function addMarkersWithInfoWindow(mapConfig)
{
	// Set camera position
//	map.setCenter(mapConfig.camConfig.center);
//	map.setZoom(mapConfig.camConfig.zoom);

	var markers = mapConfig.markers;
	// var strMarlers = JSON.stringify(markers);
	// alert("bbb "+strMarlers);
	for (var i = 0; i < markers.length; i++)
	{
		var marker = new google.maps.Marker({
			map : map,
			position : markers[i].position,
			icon : markers[i].iconImg,
			title : markers[i].title
		});

		var infowindow = new google.maps.InfoWindow({
			content : markers[i].contents
		});
		infowindow.open(map, marker);
	}
}

//Create a marker and set its position.
function addMarkersWithoutInfoWindow(mapConfig)
{
	// Set camera position
//	map.setCenter(mapConfig.camConfig.center);
//	map.setZoom(mapConfig.camConfig.zoom);

	var markers = mapConfig.markers;
	// var strMarlers = JSON.stringify(markers);
	// alert("bbb "+strMarlers);
	for (var i = 0; i < markers.length; i++)
	{
		var marker = new google.maps.Marker({
			map : map,
			position : markers[i].position,
			icon : markers[i].iconImg,
			title : markers[i].title
		});
	}
}

// Create a marker and set its position.
function addMarkersFromString(markersString)
{
	var markers = JSON.parse(markersString);
	for (var i = 0; i < markers.length; i++)
	{
		new google.maps.Marker({
			map : map,
			position : markers[i].position,
			title : markers[i].title
		});
	}
}
