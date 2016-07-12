(function() {
    'use strict';

    angular
        .module('istruttoriaApp')
        .factory('SuperficiDomanda', SuperficiDomanda);

    SuperficiDomanda.$inject = ['$resource'];

    function SuperficiDomanda($resource) {
        var resourceUrl = 'api/superficies/domanda/:id';
        
        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
