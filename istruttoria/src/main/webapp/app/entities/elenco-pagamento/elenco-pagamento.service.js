(function() {
    'use strict';
    angular
        .module('istruttoriaApp')
        .factory('ElencoPagamento', ElencoPagamento);

    ElencoPagamento.$inject = ['$resource'];

    function ElencoPagamento ($resource) {
        var resourceUrl =  'api/elenco-pagamentos/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
