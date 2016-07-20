(function() {
    'use strict';
    angular
        .module('istruttoriaApp')
        .factory('Aduxstce', Aduxstce);

    Aduxstce.$inject = ['$resource'];

    function Aduxstce ($resource) {
        var resourceUrl =  'api/aduxstces/:id';

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
