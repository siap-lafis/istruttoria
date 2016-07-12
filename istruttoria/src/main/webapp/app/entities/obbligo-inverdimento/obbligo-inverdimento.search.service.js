(function() {
    'use strict';

    angular
        .module('istruttoriaApp')
        .factory('ObbligoInverdimentoSearch', ObbligoInverdimentoSearch);

    ObbligoInverdimentoSearch.$inject = ['$resource'];

    function ObbligoInverdimentoSearch($resource) {
        var resourceUrl =  'api/_search/obbligo-inverdimentos/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
