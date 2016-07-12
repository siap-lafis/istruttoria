(function() {
    'use strict';

    angular
        .module('istruttoriaApp')
        .controller('DomandaController', DomandaController);

    DomandaController.$inject = ['$scope', '$state', 'Domanda', 'DomandaSearch'];

    function DomandaController ($scope, $state, Domanda, DomandaSearch) {
        var vm = this;
        
        vm.domandas = [];
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Domanda.query(function(result) {
                vm.domandas = result;
            });
        }

        function search () {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            DomandaSearch.query({query: vm.searchQuery}, function(result) {
                vm.domandas = result;
            });
        }    }
})();
