(function() {
    'use strict';
    angular
        .module('istruttoriaApp')
        .factory('SuperficieInverdimento', SuperficieInverdimento);

    SuperficieInverdimento.$inject = ['$resource'];

    function SuperficieInverdimento ($resource) {
        var resourceUrl =  'api/superficie-inverdimentos/:id';

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
