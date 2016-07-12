(function() {
    'use strict';

    angular
        .module('istruttoriaApp')
        .controller('PagamentoController', PagamentoController);

    PagamentoController.$inject = ['$scope', '$state', 'Pagamento', 'PagamentoSearch'];

    function PagamentoController ($scope, $state, Pagamento, PagamentoSearch) {
        var vm = this;
        
        vm.pagamentos = [];
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Pagamento.query(function(result) {
                vm.pagamentos = result;
            });
        }

        function search () {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            PagamentoSearch.query({query: vm.searchQuery}, function(result) {
                vm.pagamentos = result;
            });
        }    }
})();
