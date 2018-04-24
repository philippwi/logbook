
var input = document.getElementById('j_idt8:OriginSearchTextField');
var options = {componentRestrictions: {types: 'cities'}};
new google.maps.places.Autocomplete(input, options);

var input = document.getElementById('j_idt8:DestinationSearchTextField');
var options = {componentRestrictions: {types: 'cities'}};
new google.maps.places.Autocomplete(input, options);