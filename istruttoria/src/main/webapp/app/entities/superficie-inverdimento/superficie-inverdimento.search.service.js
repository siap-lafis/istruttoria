(function() {
    'use strict';

    angular
        .module('istruttoriaApp')
        .factory('SuperficieInverdimentoSearch', SuperficieInverdimentoSearch);

    SuperficieInverdimentoSearch.$inject = ['$resource'];

    function SuperficieInverdimentoSearch($resource) {
        var resourceUrl =  'api/_search/superficie-inverdimentos/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
