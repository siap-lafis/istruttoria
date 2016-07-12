(function() {
    'use strict';

    angular
        .module('istruttoriaApp')
        .controller('ElencoPagamentoController', ElencoPagamentoController);

    ElencoPagamentoController.$inject = ['$scope', '$state', 'ElencoPagamento', 'ElencoPagamentoSearch'];

    function ElencoPagamentoController ($scope, $state, ElencoPagamento, ElencoPagamentoSearch) {
        var vm = this;
        
        vm.elencoPagamentos = [];
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            ElencoPagamento.query(function(result) {
            	vm.elencoPagamentos = result;
            });
        }

        function search () {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            ElencoPagamentoSearch.query({query: vm.searchQuery}, function(result) {
                vm.elencoPagamentos = result;
            });
        }    }
})();
