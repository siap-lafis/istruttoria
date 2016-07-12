(function() {
    'use strict';
    angular
        .module('istruttoriaApp')
        .factory('Penalita', Penalita);

    Penalita.$inject = ['$resource'];

    function Penalita ($resource) {
        var resourceUrl =  'api/penalitas/:id';

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
