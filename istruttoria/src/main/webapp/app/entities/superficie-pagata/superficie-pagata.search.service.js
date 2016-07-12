(function() {
    'use strict';

    angular
        .module('istruttoriaApp')
        .factory('SuperficiePagataSearch', SuperficiePagataSearch);

    SuperficiePagataSearch.$inject = ['$resource'];

    function SuperficiePagataSearch($resource) {
        var resourceUrl =  'api/_search/superficie-pagatas/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
