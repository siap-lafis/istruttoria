(function() {
    'use strict';
    angular
        .module('istruttoriaApp')
        .factory('Pagamento', Pagamento);

    Pagamento.$inject = ['$resource'];

    function Pagamento ($resource) {
        var resourceUrl =  'api/pagamentos/:id';

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
