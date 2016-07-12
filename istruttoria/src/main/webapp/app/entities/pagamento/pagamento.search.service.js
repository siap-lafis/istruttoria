(function() {
    'use strict';

    angular
        .module('istruttoriaApp')
        .factory('PagamentoSearch', PagamentoSearch);

    PagamentoSearch.$inject = ['$resource'];

    function PagamentoSearch($resource) {
        var resourceUrl =  'api/_search/pagamentos/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
