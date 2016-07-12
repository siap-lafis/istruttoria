(function() {
    'use strict';

    angular
        .module('istruttoriaApp')
        .factory('DomandaSearch', DomandaSearch);

    DomandaSearch.$inject = ['$resource'];

    function DomandaSearch($resource) {
        var resourceUrl =  'api/_search/domandas/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
