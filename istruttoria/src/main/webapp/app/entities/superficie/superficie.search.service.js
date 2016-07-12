(function() {
    'use strict';

    angular
        .module('istruttoriaApp')
        .factory('SuperficieSearch', SuperficieSearch);

    SuperficieSearch.$inject = ['$resource'];

    function SuperficieSearch($resource) {
        var resourceUrl =  'api/_search/superficies/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
