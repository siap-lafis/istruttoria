(function() {
    'use strict';
    angular
        .module('istruttoriaApp')
        .factory('SuperficiPagate', SuperficiPagate);

    SuperficiPagate.$inject = ['$resource'];

    function SuperficiPagate ($resource) {
        var resourceUrl =  'api/superficie-pagatas/pagamento/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},          
        });
    }
})();