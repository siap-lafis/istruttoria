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
                method: 'POST',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
        	}
        });    
    }
})();