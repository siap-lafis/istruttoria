(function() {
    'use strict';

    angular
        .module('istruttoriaApp')
        .factory('PenalitaPagamento', PenalitaPagamento);

    PenalitaPagamento.$inject = ['$resource'];

    function PenalitaPagamento($resource) {
        var resourceUrl = 'api/penalitas/pagamento/:id';
        
        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
