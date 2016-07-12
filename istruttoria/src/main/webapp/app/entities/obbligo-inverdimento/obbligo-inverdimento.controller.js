(function() {
    'use strict';

    angular
        .module('istruttoriaApp')
        .controller('ObbligoInverdimentoController', ObbligoInverdimentoController);

    ObbligoInverdimentoController.$inject = ['$scope', '$state', 'ObbligoInverdimento', 'ObbligoInverdimentoSearch'];

    function ObbligoInverdimentoController ($scope, $state, ObbligoInverdimento, ObbligoInverdimentoSearch) {
        var vm = this;
        
        vm.obbligoInverdimentos = [];
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            ObbligoInverdimento.query(function(result) {
                vm.obbligoInverdimentos = result;
            });
        }

        function search () {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            ObbligoInverdimentoSearch.query({query: vm.searchQuery}, function(result) {
                vm.obbligoInverdimentos = result;
            });
        }    }
})();
