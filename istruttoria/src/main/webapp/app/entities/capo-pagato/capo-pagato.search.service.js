(function() {
    'use strict';

    angular
        .module('istruttoriaApp')
        .factory('CapoPagatoSearch', CapoPagatoSearch);

    CapoPagatoSearch.$inject = ['$resource'];

    function CapoPagatoSearch($resource) {
        var resourceUrl =  'api/_search/capo-pagatoes/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
