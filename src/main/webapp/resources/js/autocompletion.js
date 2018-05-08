var originInput = document.getElementById('calculationForm:originInput');

var destinationInput = document.getElementById('calculationForm:destinationInput');

var options = {componentRestrictions: {types: 'cities'}};

new google.maps.places.Autocomplete(originInput, options);

new google.maps.places.Autocomplete(destinationInput, options);