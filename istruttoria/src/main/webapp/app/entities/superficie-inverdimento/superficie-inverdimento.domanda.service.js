(function() {
    'use strict';

    angular
        .module('istruttoriaApp')
        .factory('SuperficiInverdimentoDomanda', SuperficiInverdimentoDomanda);

    SuperficiInverdimentoDomanda.$inject = ['$resource'];

    function SuperficiInverdimentoDomanda($resource) {
        var resourceUrl = 'api/superficie-inverdimentos/domanda/:id';
        
        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();