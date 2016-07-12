(function() {
    'use strict';
    angular
        .module('istruttoriaApp')
        .factory('Domanda', Domanda);

    Domanda.$inject = ['$resource'];

    function Domanda ($resource) {
        var resourceUrl =  'api/domandas/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
