(function() {
    'use strict';
    angular
        .module('istruttoriaApp')
        .factory('ObbligoInverdimento', ObbligoInverdimento);

    ObbligoInverdimento.$inject = ['$resource'];

    function ObbligoInverdimento ($resource) {
        var resourceUrl =  'api/obbligo-inverdimentos/:id';

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
