var originInput = document.getElementById('calculationForm:originInput');

var destinationInput = document.getElementById('calculationForm:destinationInput');

var options = {componentRestrictions: {types: 'cities'}};

google.maps.places.Autocomplete(originInput, options);

google.maps.places.Autocomplete(destinationInput, options);