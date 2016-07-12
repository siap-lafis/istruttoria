(function() {
    'use strict';

    angular
        .module('istruttoriaApp')
        .factory('PenalitaSearch', PenalitaSearch);

    PenalitaSearch.$inject = ['$resource'];

    function PenalitaSearch($resource) {
        var resourceUrl =  'api/_search/penalitas/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
