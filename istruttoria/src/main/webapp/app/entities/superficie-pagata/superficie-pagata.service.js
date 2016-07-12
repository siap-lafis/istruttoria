(function() {
    'use strict';
    angular
        .module('istruttoriaApp')
        .factory('SuperficiePagata', SuperficiePagata);

    SuperficiePagata.$inject = ['$resource'];

    function SuperficiePagata ($resource) {
        var resourceUrl =  'api/superficie-pagatas/:id';

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
