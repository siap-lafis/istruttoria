(function() {
    'use strict';

    angular
        .module('istruttoriaApp')
        .controller('PenalitaController', PenalitaController);

    PenalitaController.$inject = ['$scope', '$state', 'Penalita', 'PenalitaSearch'];

    function PenalitaController ($scope, $state, Penalita, PenalitaSearch) {
        var vm = this;
        
        vm.penalitas = [];
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Penalita.query(function(result) {
                vm.penalitas = result;
            });
        }

        function search () {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            PenalitaSearch.query({query: vm.searchQuery}, function(result) {
                vm.penalitas = result;
            });
        }    }
})();
