(function() {
    'use strict';
    angular
        .module('istruttoriaApp')
        .factory('Superficie', Superficie);

    Superficie.$inject = ['$resource'];

    function Superficie ($resource) {
        var resourceUrl =  'api/superficies/:id';

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
