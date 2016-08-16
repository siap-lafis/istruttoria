(function() {
    'use strict';

    angular
        .module('istruttoriaApp')
        .factory('CapiPagatiPagamento', CapiPagatiPagamento);

    CapiPagatiPagamento.$inject = ['$resource'];

    function CapiPagatiPagamento($resource) {
        var resourceUrl = 'api/capo-pagatoes/pagamento/:id';
        
        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();