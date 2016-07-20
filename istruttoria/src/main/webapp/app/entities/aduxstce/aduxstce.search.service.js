(function() {
    'use strict';

    angular
        .module('istruttoriaApp')
        .factory('AduxstceSearch', AduxstceSearch);

    AduxstceSearch.$inject = ['$resource'];

    function AduxstceSearch($resource) {
        var resourceUrl =  'api/_search/aduxstces/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
