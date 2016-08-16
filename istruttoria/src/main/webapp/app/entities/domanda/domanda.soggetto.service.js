(function() {
    'use strict';

    angular
        .module('istruttoriaApp')
        .factory('DomandeSoggetto', DomandeSoggetto);

    DomandeSoggetto.$inject = ['$resource'];

    function DomandeSoggetto($resource) {
        var resourceUrl = 'api/domandas/soggetto/:id';
        
        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
