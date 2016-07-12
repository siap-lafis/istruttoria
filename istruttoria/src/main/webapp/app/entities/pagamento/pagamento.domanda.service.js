(function() {
    'use strict';

    angular
        .module('istruttoriaApp')
        .factory('PagamentiDomanda', PagamentiDomanda);

    PagamentiDomanda.$inject = ['$resource'];

    function PagamentiDomanda($resource) {
        var resourceUrl = 'api/pagamentos/domanda/:id';
        
        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
