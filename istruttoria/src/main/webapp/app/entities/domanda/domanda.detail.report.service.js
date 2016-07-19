(function() {
    'use strict';

    angular
        .module('istruttoriaApp')
        .factory('DomandaDetailReport', DomandaDetailReport);

    DomandaDetailReport.$inject = ['$resource'];

    function DomandaDetailReport($resource) {
        var resourceUrl =  'api/domanda/detail/report';

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