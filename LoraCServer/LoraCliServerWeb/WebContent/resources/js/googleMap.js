function initMap()
{
	var myLatLng1 = {
		lat : 45.351578,
		lng : -75.939724
	};
	var myLatLng2 = {
		lat : 45.353039,
		lng : -75.939859
	};
	var myLatLng3 = {
		lat : 45.353160,
		lng : -75.940975
	};
	var myLatLng4 = {
		lat : 45.352180,
		lng : -75.940750
	};
	var myLatLng5 = {
		lat : 45.352014,
		lng : -75.938823
	};

	// Create a map object and specify the DOM element for display.
	var map = new google.maps.Map(document.getElementById('map'), {
		center : myLatLng1,
		zoom : 17
	});

	// Create a marker and set its position.
	var marker1 = new google.maps.Marker({
		map : map,
		position : myLatLng1,
		title : 'Hello World!'
	});
	var marker2 = new google.maps.Marker({
		map : map,
		position : myLatLng2,
		title : 'Jianfeng!'
	});
	var marker3 = new google.maps.Marker({
		map : map,
		position : myLatLng3,
		title : 'Robert!'
	});
	var marker4 = new google.maps.Marker({
		map : map,
		position : myLatLng4,
		title : 'Liana!'
	});
	var marker5 = new google.maps.Marker({
		map : map,
		position : myLatLng5,
		title : 'Robetrt Zhang'
	});

}
