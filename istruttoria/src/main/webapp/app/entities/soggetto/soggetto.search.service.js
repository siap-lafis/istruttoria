(function() {
    'use strict';

    angular
        .module('istruttoriaApp')
        .factory('SoggettoSearch', SoggettoSearch);

    SoggettoSearch.$inject = ['$resource'];

    function SoggettoSearch($resource) {
        var resourceUrl =  'api/_search/soggettos/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
