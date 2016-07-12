(function() {
    'use strict';

    angular
        .module('istruttoriaApp')
        .controller('PagamentiDomandaController', PagamentiDomandaController);

    PagamentiDomandaController.$inject = ['$scope', '$state', '$stateParams', 'PagamentiDomanda'];

    function PagamentiDomandaController ($scope, $state, $stateParams, PagamentiDomanda) {
        var vm = this;
        
        vm.pagamentos = [];
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
        	PagamentiDomanda.query({id : $stateParams.id}, function(result) {
        		vm.pagamentos = result;
            });
        }

    }
})();
