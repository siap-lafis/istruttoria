(function() {
    'use strict';

    angular
        .module('istruttoriaApp')
        .factory('ObblighiInverdimentoSuperficiInverdimento', ObblighiInverdimentoSuperficiInverdimento);

    ObblighiInverdimentoSuperficiInverdimento.$inject = ['$resource'];

    function ObblighiInverdimentoSuperficiInverdimento($resource) {
        var resourceUrl = 'api/obbligo-inverdimentos/superficie-inverdimento/:id';
        
        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
