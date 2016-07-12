(function() {
    'use strict';

    angular
        .module('istruttoriaApp')
        .factory('ElencoPagamentoSearch', ElencoPagamentoSearch);

    ElencoPagamentoSearch.$inject = ['$resource'];

    function ElencoPagamentoSearch($resource) {
        var resourceUrl =  'api/_search/elenco-pagamentos/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
