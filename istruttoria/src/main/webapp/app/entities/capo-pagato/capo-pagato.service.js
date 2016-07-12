(function() {
    'use strict';
    angular
        .module('istruttoriaApp')
        .factory('CapoPagato', CapoPagato);

    CapoPagato.$inject = ['$resource'];

    function CapoPagato ($resource) {
        var resourceUrl =  'api/capo-pagatoes/:id';

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
