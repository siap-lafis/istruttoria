(function() {
    'use strict';

    angular
        .module('istruttoriaApp')
        .factory('SoggettoReport', SoggettoReport);

    SoggettoReport.$inject = ['$resource'];

    function SoggettoReport($resource) {
        var resourceUrl =  'api/report';

        return $resource(resourceUrl, {}, {
        	generate : {
                method: 'GET',
                transformResponse: function (data) {
                    return data;
                }
        	}
        });    
    }
})();
