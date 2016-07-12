(function() {
    'use strict';
    angular
        .module('istruttoriaApp')
        .factory('Soggetto', Soggetto);

    Soggetto.$inject = ['$resource'];

    function Soggetto ($resource) {
        var resourceUrl =  'api/soggettos/:id';

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
